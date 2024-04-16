package org.javapro.skhlebko.homework_1;

import org.javapro.skhlebko.homework_1.service.TestRunnerService;
import org.javapro.skhlebko.homework_1.test.ExampleTests;

public class Main {
    public static void main(String[] args) {
        TestRunnerService.runTests(ExampleTests.class);
    }
}