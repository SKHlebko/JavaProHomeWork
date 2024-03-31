package org.javapro.skhlebko;

import org.javapro.skhlebko.service.TestRunnerService;
import org.javapro.skhlebko.test.ExampleTests;

public class Main {
    public static void main(String[] args) {
        TestRunnerService.runTests(ExampleTests.class);
    }
}