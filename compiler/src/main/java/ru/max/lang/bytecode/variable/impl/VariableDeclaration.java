package ru.max.lang.bytecode.variable.impl;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import ru.max.antlr.TeitLexer;
import ru.max.lang.bytecode.variable.Instruction;

public class VariableDeclaration implements Instruction, Opcodes {
    Variable variable;

    public VariableDeclaration(Variable variable) {
        this.variable = variable;
    }

    @Override
    public void apply(MethodVisitor mv) {
        final int type = variable.getType();
        if(type == TeitLexer.NUMBER){
            int val = Integer.parseInt(variable.getValue());
            mv.visitIntInsn(Opcodes.BIPUSH, val);
            mv.visitVarInsn(Opcodes.ISTORE, variable.getId());
        }else if(type == TeitLexer.STRING){
            mv.visitLdcInsn(variable.getValue());
            mv.visitVarInsn(ASTORE, variable.getId());
        }
    }
}
