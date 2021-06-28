package com.study.application.bean;

public interface App {
    @LogProcess
    void printWithAnnotation();
    @LogProcess
    void printWithOutAnnotation();
}
