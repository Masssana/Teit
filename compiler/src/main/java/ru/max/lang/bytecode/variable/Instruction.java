package ru.max.lang.bytecode.variable;

import org.objectweb.asm.MethodVisitor;

public interface Instruction {
    void apply(MethodVisitor mv);
}
