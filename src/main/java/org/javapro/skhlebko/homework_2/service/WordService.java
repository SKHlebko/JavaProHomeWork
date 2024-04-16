package org.javapro.skhlebko.homework_2.service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordService {
    // 6. Самое длинное слово в списке слов
    public String findLongestWord(List<String> words) {
        return words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(() -> new NoSuchElementException("Не удалось найти самое длинное слово"));
    }

    // 7. Хеш-мапа слово - количество его встреч в строке
    public Map<String, Long> countWords(String sentence) {
        return Arrays.stream(sentence.split(" "))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    // 8. Вывод строк из списка в порядке увеличения длины слова
    public void sortWordsByLengthAndPrint(List<String> words) {
        words.stream()
                .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
                .forEach(System.out::println);
    }

    // 9. Самое длинное слово в массиве строк
    public String findLongestWordInArray(String[] arrayOfStrings) {
        return Arrays.stream(arrayOfStrings)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(() -> new NoSuchElementException("Не удалось найти самое длинное слово в массиве строк"));
    }
}