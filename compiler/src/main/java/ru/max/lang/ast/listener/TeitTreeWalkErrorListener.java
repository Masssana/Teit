package ru.max.lang.ast.listener;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class TeitTreeWalkErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine, String msg, RecognitionException e) {
        final String errorFormat = "oopsie a mistake at the line %d, char $d :(. here is details " +
                "bro:\n%s ";
        final String errorMessage = String.format(errorFormat, line, charPositionInLine, msg);
        System.err.println(errorMessage);
    }
}
