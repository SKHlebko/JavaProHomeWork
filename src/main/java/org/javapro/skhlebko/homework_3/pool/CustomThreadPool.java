package org.javapro.skhlebko.homework_3.pool;

import org.javapro.skhlebko.homework_3.thread.WorkerThread;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomThreadPool {
    private final List<WorkerThread> threads;
    public final LinkedList<Runnable> taskQueue;
    private boolean isShutdown = false;
    private static final Logger LOGGER = Logger.getLogger(CustomThreadPool.class.getName());

    public CustomThreadPool(int capacity) {
        taskQueue = new LinkedList<>();
        threads = new LinkedList<>();

        for (int i = 0; i < capacity; i++) {
            WorkerThread worker = new WorkerThread("WorkerThread-" + (i + 1), this);
            threads.add(worker);
            worker.start();
        }
        LOGGER.info("Пул потоков инициализирован с емкостью: " + capacity);
    }

    public synchronized void execute(Runnable task) {
        if (this.isShutdown) {
            LOGGER.severe("Попытка выполнить задачу после остановки пула потоков.");
            throw new IllegalStateException("Пул потоков остановлен и не может принимать новые задачи.");
        }
        this.taskQueue.addLast(task);
        LOGGER.info("Новая задача добавлена в очередь.");
        this.notify(); // Уведомляем один из ожидающих потоков
    }

    public synchronized void shutdown() {
        this.isShutdown = true;
        LOGGER.info("Инициирована остановка. Новые задачи не будут приниматься.");
        this.notifyAll(); // Уведомляем все ожидающие потоки
    }

    public void awaitTermination() {
        for (Thread thread : threads) {
            try {
                thread.join(); // Ожидаем завершения каждого потока
                LOGGER.info(thread.getName() + " завершил работу.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.log(Level.SEVERE, "Поток прерван во время ожидания завершения", e);
            }
        }
        LOGGER.info("Все потоки завершены. Остановка пула потоков выполнена.");
    }

    public synchronized Runnable takeTask() throws InterruptedException {
        while (taskQueue.isEmpty() && !isShutdown) {
            this.wait();
        }
        if (isShutdown && taskQueue.isEmpty()) {
            return null; // Если пул остановлен и задач нет, возвращаем null
        }
        return taskQueue.removeFirst(); // Берем задачу из очереди
    }

    public boolean isShutdown() {
        return isShutdown;
    }
}