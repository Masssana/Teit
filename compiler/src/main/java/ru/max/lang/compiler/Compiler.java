package ru.max.lang.compiler;

import org.apache.commons.lang3.StringUtils;
import ru.max.lang.bytecode.ByteCodeGenerator;
import ru.max.lang.bytecode.variable.Instruction;
import ru.max.lang.ast.SyntaxTreeTraverser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Queue;

public class Compiler {
    public static void main(String[] args) throws Exception {
        new Compiler().compile(args);
    }

    public void compile(String[] args) throws Exception {
        final File teitFile = new File(args[0]);
        String fileName = teitFile.getName();
        String fileAbsolutePath = teitFile.getAbsolutePath();
        String className = StringUtils.remove(fileName, ".teit");
        final Queue<Instruction> instructionQueue = new SyntaxTreeTraverser().getInstructions(fileAbsolutePath);
        var generator = new ByteCodeGenerator();
        final byte[] byteCode = generator.generateBytecode(instructionQueue, className);
        saveBytecodeToClassFile(fileName, byteCode);
    }

    private void saveBytecodeToClassFile(String fileName, byte[] byteCode) throws IOException {
        String className = StringUtils.replace(fileName, ".teit", ".class");
        OutputStream outputStream = new FileOutputStream(className);
        outputStream.write(byteCode);
        outputStream.close();
    }
}
