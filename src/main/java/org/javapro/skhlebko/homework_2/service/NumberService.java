package org.javapro.skhlebko.homework_2.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class NumberService {
    // 2. Найти 3-е наибольшее число
    public Optional<Integer> findThirdLargest(List<Integer> numbers) {
        return numbers.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst();
    }

    // 3. Найти 3-е наибольшее "уникальное" число
    public Optional<Integer> findThirdLargestUnique(List<Integer> numbers) {
        return numbers.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst();
    }
}

