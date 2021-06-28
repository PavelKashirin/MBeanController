package com.study.application.controler;

public class Controller implements ControllerMBean {
    private boolean isPrintAspect  = false;

    public boolean isPrintAspect() {
        return isPrintAspect;
    }

    @Override
    public void setPrintAspect(boolean printAspect) {
        isPrintAspect = printAspect;
    }
}