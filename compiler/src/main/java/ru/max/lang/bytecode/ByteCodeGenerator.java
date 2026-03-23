package ru.max.lang.bytecode;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import ru.max.lang.bytecode.variable.Instruction;
import ru.max.lang.bytecode.variable.impl.VariableDeclaration;


import java.util.Queue;



public class ByteCodeGenerator implements Opcodes {
    private final int VERSION = 52;
    private final String SIGNATURE = null;
    private final String BASE_CLASS = "java/lang/Object";
    private final String[] INTERFACES = null;

    public byte[] generateBytecode(Queue<Instruction> instructionQueue, String name) throws Exception {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(VERSION, ACC_PUBLIC + ACC_SUPER, name, SIGNATURE, BASE_CLASS, INTERFACES);

        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            final long localVariablesCount = instructionQueue.stream()
                    .filter(instruction -> instruction instanceof VariableDeclaration)
                    .count();

            final int maxStack = 100;

            for (Instruction instruction : instructionQueue) {
                instruction.apply(mv);
            }

            mv.visitInsn(RETURN);

            mv.visitMaxs(maxStack, (int) localVariablesCount);

            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();

    }
}
