package org.javapro.skhlebko.homework_2.service;

import org.javapro.skhlebko.homework_2.model.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class EmployeeService {
    // 4. Список имен 3-х самых старших инженеров
    public List<String> findTop3SeniorEngineers(List<Employee> employees) {
        return employees.stream()
                .filter(e -> "Инженер".equals(e.position()))
                .sorted(Comparator.comparingInt(Employee::age).reversed())
                .limit(3)
                .map(Employee::name)
                .collect(Collectors.toList());
    }
    
    // 5. Средний возраст сотрудников-инженеров
    public double calculateAverageAgeOfEngineers(List<Employee> employees) {
        return employees.stream()
                .filter(e -> "Инженер".equals(e.position()))
                .mapToInt(Employee::age)
                .average()
                .orElseThrow(() -> new NoSuchElementException("Не удалось вычислить средний возраст инженеров"));
    }
}