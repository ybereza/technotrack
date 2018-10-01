package com.example.ybereza.helloandroid;

public class ThreadCall {
    void exec() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello, from background thread");
            }
        });
    }
}
