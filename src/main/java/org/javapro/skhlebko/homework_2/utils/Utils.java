package org.javapro.skhlebko.homework_2.utils;

public class Utils {

    public static void printFormatted(char character, int count) {
        String repeatedString = String.valueOf(character).repeat(count);
        System.out.printf("%s%n", repeatedString);
    }
}