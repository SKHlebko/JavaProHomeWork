package org.javapro.skhlebko.test;

import org.javapro.skhlebko.annotation.*;

public class ExampleTests {

    @BeforeTest
    public static void preparing() {
        System.out.println(("Preparing for test."));
    }

    @AfterTest
    public static void goodWork() {
        System.out.println("Good work");
    }

    @BeforeSuite
    public static void setup() {
        System.out.println("Setup before all tests.");
    }

    @AfterSuite
    public static void tearDown() {
        System.out.println("Cleanup after all tests.");
    }

    @Test(priority = 1)
    public void test1() {
        System.out.println("Running test1 with priority 1.");
    }

    @Test(priority = 2)
    @CsvSource("10, Java, 20, true")
    public void testWithParameters(int a, String b, int c, boolean d) {
        System.out.println("Running testWithParameters with parameters: " + a + ", " + b + ", " + c + ", " + d);
    }

}