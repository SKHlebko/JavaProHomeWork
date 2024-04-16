package org.javapro.skhlebko.homework_1.service;


import org.javapro.skhlebko.homework_1.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunnerService {
    public static void runTests(Class<?> c) {
        try {
            Object testInstance = c.getDeclaredConstructor().newInstance();
            Method beforeSuite = null;
            Method afterSuite = null;
            List<Method> testMethods = new ArrayList<>();
            List<Method> beforeTestMethods = new ArrayList<>();
            List<Method> afterTestMethods = new ArrayList<>();

            for (Method method : c.getDeclaredMethods()) {
                if (method.isAnnotationPresent(BeforeSuite.class)) {
                    if (beforeSuite != null || !Modifier.isStatic(method.getModifiers()))
                        throw new RuntimeException("Only one static @BeforeSuite method is allowed.");
                    beforeSuite = method;
                } else if (method.isAnnotationPresent(AfterSuite.class)) {
                    if (afterSuite != null || !Modifier.isStatic(method.getModifiers()))
                        throw new RuntimeException("Only one static @AfterSuite method is allowed.");
                    afterSuite = method;
                } else if (method.isAnnotationPresent(Test.class)) {
                    testMethods.add(method);
                } else if (method.isAnnotationPresent(BeforeTest.class)) {
                    beforeTestMethods.add(method);
                } else if (method.isAnnotationPresent(AfterTest.class)) {
                    afterTestMethods.add(method);
                }
            }

            testMethods.sort(Comparator.comparingInt(m -> m.getAnnotation(Test.class).priority()));

            invokeMethod(beforeSuite, c);
            for (Method testMethod : testMethods) {
                beforeTestMethods.forEach(m -> invokeMethod(m, testInstance));
                invokeTestMethod(testMethod, testInstance);
                afterTestMethods.forEach(m -> invokeMethod(m, testInstance));
            }
            invokeMethod(afterSuite, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void invokeMethod(Method method, Object instance) {
        if (method != null) {
            try {
                method.setAccessible(true); // Для доступа к private методам
                method.invoke(instance);
            } catch (Exception e) {
                throw new RuntimeException("Failed to invoke method: " + method.getName(), e);
            }
        }
    }

    private static void invokeTestMethod(Method testMethod, Object testInstance) throws Exception {
        if (testMethod.isAnnotationPresent(CsvSource.class)) {
            String[] args = testMethod.getAnnotation(CsvSource.class).value().split(",\\s*");
            Class<?>[] parameterTypes = testMethod.getParameterTypes();
            Object[] convertedArgs = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                convertedArgs[i] = convertArg(args[i], parameterTypes[i]);
            }
            testMethod.invoke(testInstance, convertedArgs);
        } else {
            testMethod.invoke(testInstance);
        }
    }

    private static Object convertArg(String arg, Class<?> targetType) {
        if (Integer.TYPE == targetType) {
            return Integer.parseInt(arg);
        } else if (Long.TYPE == targetType) {
            return Long.parseLong(arg);
        } else if (Double.TYPE == targetType) {
            return Double.parseDouble(arg);
        } else if (Float.TYPE == targetType) {
            return Float.parseFloat(arg);
        } else if (Boolean.TYPE == targetType) {
            return Boolean.parseBoolean(arg);
        } else if (Character.TYPE == targetType) {
            return arg.charAt(0);
        }
        return arg;
    }
}