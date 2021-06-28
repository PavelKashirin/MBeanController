package com.study.application.bean;

public class Application implements App {
    private int count;

    @Override
    public void printWithAnnotation() {
        System.out.println("Void with annotation");
        System.out.println("Count: " + count++);
    }

    @Override
    public void printWithOutAnnotation() {
        System.out.println("Void without annotation");
    }
}
