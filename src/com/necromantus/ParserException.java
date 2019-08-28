package com.necromantus;

import com.necromantus.parser.Parser;

public class ParserException extends Exception {
    public ParserException(String msg) {
        super(
                msg +
                        " at " +
                        Parser.tokenizer.firstCharIndex +
                        " ..." +
//                        Parser.tokenizer.tokenEatingHistory.get(Parser.tokenizer.tokenEatingHistory.size()-1).substring(
//                                bound(0, Parser.tokenizer.actualStr.length(), Parser.tokenizer.firstCharIndex - 5),
//                                bound(0, Parser.tokenizer.actualStr.length(), Parser.tokenizer.firstCharIndex)
//                        ) +
                        " >>" +
                        Parser.tokenizer.tokenEatingHistory.get(Parser.tokenizer.tokenEatingHistory.size()-1).charAt(0) +
                        "<<" +
                        (Parser.tokenizer.tokenEatingHistory.get(Parser.tokenizer.tokenEatingHistory.size()-1)+"END").substring(
                                1,
                                bound(1, 6, Parser.tokenizer.tokenEatingHistory.get(Parser.tokenizer.tokenEatingHistory.size()-1).length())
                        ) +
//                        Parser.tokenizer.tokenEatingHistory.get(Parser.tokenizer.tokenEatingHistory.size()-1).substring(
//                                bound(0, Parser.tokenizer.actualStr.length(), Parser.tokenizer.firstCharIndex + 1),
//                                bound(0, Parser.tokenizer.actualStr.length(), Parser.tokenizer.firstCharIndex + 6)
//                        ) +
                        "..."
        );
    }

    public static int bound(int min, int max, int value) {
        return value < min ? min : value > max ? max : value;
    }
}
