package com.cui.java.study.base.io.utils;

import com.vdurmont.emoji.EmojiParser;

/**
 * mysql字符集大多数使用的是utf8，而mysql的utf8编码的一个字符最多3个字节，
 * 但是一个emoji表情为4个字节，所以utf8不支持存储emoji表情。
 * 但是utf8的超集utf8mb4一个字符最多能有4字节，所以能支持emoji表情的存储，并且兼容utf8
 *
 * https://blog.csdn.net/W_ya24K/article/details/78351073
 */
public class EmojiUtils {
    public static void main(String[] args) {
        String s1 = "👥";
        String alias = EmojiParser.parseToAliases(s1);
        String emoj = EmojiParser.parseToUnicode(alias);

        String s2 = "😢";
        String alias2 = EmojiParser.parseToAliases(s2);
        String emoj2 = EmojiParser.parseToUnicode(s2);

        String s3 = "\uD83D\uDE0A";
        String alias3 = EmojiParser.parseToAliases(s3);
        String emoj3 = EmojiParser.parseToUnicode(s3);

        /*String s4 = "\xf0\x9f\x9a\x95";
        String alias4 = EmojiParser.parseToAliases(s4);
        String emoj4 = EmojiParser.parseToUnicode(s4);*/


        System.out.println(1);
    }

}
