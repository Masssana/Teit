package ru.max.lang.ast;

import org.antlr.v4.runtime.*;
import ru.max.antlr.TeitLexer;
import ru.max.antlr.TeitParser;
import ru.max.lang.bytecode.variable.Instruction;
import ru.max.lang.ast.listener.TeitTreeWalkErrorListener;
import ru.max.lang.ast.listener.TeitTreeWalkListener;

import java.io.IOException;
import java.util.Queue;

public class SyntaxTreeTraverser {
    public Queue<Instruction> getInstructions(String fileAbsolutePath) throws IOException {
        CharStream charStream = CharStreams.fromFileName(fileAbsolutePath);
        TeitLexer lexer = new TeitLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TeitParser parser = new TeitParser(tokens);
        TeitTreeWalkListener listener = new TeitTreeWalkListener();

        BaseErrorListener baseErrorListener = new TeitTreeWalkErrorListener();

        parser.addErrorListener(baseErrorListener);
        parser.addParseListener(listener);
        parser.compilationUnit();

        return listener.getInstructionQueue();
    }
}
