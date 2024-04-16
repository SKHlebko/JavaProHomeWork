package org.javapro.skhlebko.homework_2;

import org.javapro.skhlebko.homework_2.model.Employee;
import org.javapro.skhlebko.homework_2.service.EmployeeService;
import org.javapro.skhlebko.homework_2.service.NumberService;
import org.javapro.skhlebko.homework_2.service.WordService;
import org.javapro.skhlebko.homework_2.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StreamExamples {

    public static void main(String[] args) {
        NumberService numberService = new NumberService();
        EmployeeService employeeService = new EmployeeService();
        WordService wordService = new WordService();

        // 1. Удаление всех дубликатов из списка
        List<String> animals = new ArrayList<>(List.of("Кот", "Собака", "Кот", "Попугай", "Собака"));

        List<String> distinctRecords = animals.stream()
                .distinct()
                .toList();
        System.out.println("Без дубликатов: " + distinctRecords);
        Utils.printFormatted('-', 60);

        // 2. Найти 3-е наибольшее число
        List<Integer> numbers = List.of(5, 2, 10, 9, 4, 3, 10, 1, 13);

        Optional<Integer> thirdLargest = numberService.findThirdLargest(numbers);
        thirdLargest.ifPresentOrElse(
                number -> System.out.println("Третье наибольшее число: " + number),
                () -> System.out.println("Третье наибольшее число не найдено")
        );
        Utils.printFormatted('-', 60);

        // 3. Найти 3-е наибольшее "уникальное" число
        Optional<Integer> thirdLargestUnique = numberService.findThirdLargestUnique(numbers);
        thirdLargestUnique.ifPresentOrElse(
                number -> System.out.println("Третье наибольшее уникальное число: " + number),
                () -> System.out.println("Третье наибольшее уникальное число не найдено")
        );
        Utils.printFormatted('-', 60);

        // 4. Список имен 3-х самых старших инженеров
        List<Employee> employees = List.of(
                new Employee("Василий", 45, "Инженер"),
                new Employee("Александра", 32, "Инженер"),
                new Employee("Игорь", 36, "Менеджер"),
                new Employee("Елена", 40, "Инженер"),
                new Employee("Дмитрий", 50, "Инженер")
        );
        List<String> top3Engineers = employeeService.findTop3SeniorEngineers(employees);
        System.out.println("Топ 3 старших инженеров: " + top3Engineers);
        Utils.printFormatted('-', 60);


        // 5. Средний возраст сотрудников-инженеров
        double averageAge = employeeService.calculateAverageAgeOfEngineers(employees);
        System.out.println("Средний возраст инженеров: " + averageAge);
        Utils.printFormatted('-', 60);

        // 6. Самое длинное слово в списке слов
        List<String> words = List.of("компьютер", "банан", "философия", "дом", "кот");
        String longestWord = wordService.findLongestWord(words);
        System.out.println("Самое длинное слово: " + longestWord);
        Utils.printFormatted('-', 60);

        // 7. Хеш-мапа слово - количество его встреч в строке
        String sentence = "кот собака кот попугай собака кот";
        System.out.println("Количество повторений слов: " + wordService.countWords(sentence));
        Utils.printFormatted('-', 60);

        // 8. Вывод строк из списка в порядке увеличения длины слова
        wordService.sortWordsByLengthAndPrint(words);
        Utils.printFormatted('-', 60);

        // 9. Самое длинное слово в массиве строк
        String[] arrayOfStrings = {"компьютер банан философия дом кот", "гиппопотам муравей акула кит слон"};
        String longestWordInArray = wordService.findLongestWordInArray(arrayOfStrings);
        System.out.println("Самое длинное слово в массиве строк: " + longestWordInArray);
        Utils.printFormatted('-', 60);
    }
}