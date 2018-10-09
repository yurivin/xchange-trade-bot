package com.github.yurivin.bot.thread;

public class ThreadUtils {

    public static void interruptIfinterrupted() {
        if(Thread.currentThread().isInterrupted()) {
            Thread.currentThread().interrupt();
        }
    }
}
