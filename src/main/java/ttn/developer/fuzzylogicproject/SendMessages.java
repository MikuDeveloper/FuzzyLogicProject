package ttn.developer.fuzzylogicproject;

import java.util.Iterator;
import java.util.Queue;

public class SendMessages implements Runnable {
    private final Iterator<Thread> iterator;

    public SendMessages(Queue<Thread> tasks) {
        this.iterator = tasks.iterator();
    }

    @Override
    public void run() {
        while (iterator.hasNext()) {
            Thread task = iterator.next();
            task.start();
            try {
                task.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            /*while (true) {
                *//*if (!(task.isAlive())) {
                    break;
                }*//*

            }*/
        }
    }
}
