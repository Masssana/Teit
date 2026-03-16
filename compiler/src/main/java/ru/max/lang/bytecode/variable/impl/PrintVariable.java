package ru.max.lang.bytecode.variable.impl;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import ru.max.antlr.TeitLexer;
import ru.max.lang.bytecode.variable.Instruction;

public class PrintVariable implements Instruction, Opcodes {
    Variable variable;

    public PrintVariable(Variable variable) {
        this.variable = variable;
    }

    @Override
    public void apply(MethodVisitor mv) {
        final int type = variable.getType();
        final int id = variable.getId();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        if(type == TeitLexer.NUMBER){
            mv.visitVarInsn(ILOAD, id);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V");
        }else if(type == TeitLexer.STRING){
            mv.visitVarInsn(ALOAD, id);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        }

    }
}
