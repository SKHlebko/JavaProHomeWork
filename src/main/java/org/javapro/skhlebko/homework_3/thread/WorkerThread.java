package org.javapro.skhlebko.homework_3.thread;

import org.javapro.skhlebko.homework_3.pool.CustomThreadPool;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerThread extends Thread {
    private final CustomThreadPool pool;
    private static final Logger LOGGER = Logger.getLogger(WorkerThread.class.getName());

    public WorkerThread(String name, CustomThreadPool pool) {
        super(name);
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            while (!pool.isShutdown() || !pool.taskQueue.isEmpty()) {
                Runnable task = pool.takeTask();
                if (task != null) {
                    LOGGER.info(getName() + " начинает выполнение задачи.");
                    task.run();
                    LOGGER.info(getName() + " завершил выполнение задачи.");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.SEVERE, getName() + " был прерван.", e);
        }
        LOGGER.info(getName() + " завершает работу.");
    }
}