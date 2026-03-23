package ru.max.lang.ast.listener;

import org.antlr.v4.runtime.tree.TerminalNode;
import ru.max.antlr.TeitBaseListener;
import ru.max.antlr.TeitParser;
import ru.max.lang.bytecode.variable.Instruction;
import ru.max.lang.bytecode.variable.impl.PrintVariable;
import ru.max.lang.bytecode.variable.impl.Variable;
import ru.max.lang.bytecode.variable.impl.VariableDeclaration;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class TeitTreeWalkListener extends TeitBaseListener {
    Queue<Instruction> instructionQueue = new ArrayDeque<>();
    Map<String, Variable> variables = new HashMap<>();

    public Queue<Instruction> getInstructionQueue() {
        return instructionQueue;
    }

    @Override
    public void exitVariable(TeitParser.VariableContext ctx) {
        final TerminalNode varName = ctx.ID();
        final TeitParser.ValueContext varValue = ctx.value();
        final int varType = varValue.getStart().getType();
        final int varIndex = variables.size();
        final String varTextValue = varValue.getText();
        Variable var = new Variable(varType, varIndex, varTextValue);
        variables.put(varName.getText(), var);
        instructionQueue.add(new VariableDeclaration(var));
        logVarDeclarationStatement(varName, varValue);
    }

    @Override
    public void exitPrint(TeitParser.PrintContext ctx) {
        final TerminalNode varName = ctx.ID();
        final boolean printNotDeclared = !variables.containsKey(varName.getText());
        if(printNotDeclared) {
            final String errorFormat = "OSHIBKA: Myau myafriend WHAT U DOING? TEIT is not that smart to understand" +
                    "VARIABLES YOU HAVE NOT DECLARED '%s'!!!!!!";
            System.err.printf((errorFormat) + "%n", varName.getText());
            return;
        }
        final Variable variable = variables.get(varName.getText());
        instructionQueue.add(new PrintVariable(variable));
        logPrintStatementFound(varName, variable);
    }

    private void logVarDeclarationStatement(TerminalNode varName, TeitParser.ValueContext varValue) {
        final int line = varName.getSymbol().getLine();
        final String format = "GOOD: Declared variable '%s' with value '%s'!" +
                "at line '%s ";
        System.out.printf(format, varName.getText(), varValue.getText(), line);
    }

    private void logPrintStatementFound(TerminalNode varName, Variable variable) {
        final int line = varName.getSymbol().getLine();
        final String format = "GOOD: Instructed variable '%s'" +
                "to print value '%s' + at line '%s ";
        System.out.printf(format, variable.getId(), variable.getValue(), line);
    }

}
