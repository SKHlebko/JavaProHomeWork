package org.javapro.skhlebko.homework_3;

import org.javapro.skhlebko.homework_3.pool.CustomThreadPool;

import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        CustomThreadPool pool = new CustomThreadPool(4); // Создаем пул потоков с 4 рабочими потоками

        for (int i = 0; i < 10; i++) {
            int taskNo = i;
            pool.execute(() -> {
                LOGGER.info("Выполнение задачи " + taskNo);
                try {
                    Thread.sleep(1000); // Имитация выполнения задачи
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                LOGGER.info("Задача " + taskNo + " завершена.");
            });
        }

        pool.shutdown();
        pool.awaitTermination();
    }
}