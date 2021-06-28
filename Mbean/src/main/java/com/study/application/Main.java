package com.study.application;

import com.study.application.bean.App;
import com.study.application.bean.Application;
import com.study.application.bean.LogProcess;
import com.study.application.controler.Controller;
import com.study.application.controler.ProcessScanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

class MyInvocationHandler implements InvocationHandler {
    boolean needSave;
    App originalApp;
    App inputApp;

    public MyInvocationHandler(App app) {
        originalApp = app;
        needSave = false;

        inputApp = app;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int count = 0;
        for (Annotation a : method.getDeclaredAnnotations()) {
            boolean result = (a.annotationType() == LogProcess.class);
            if (result) {
                count = 1;
            }
        }

        Object invoke;
        if (count > 0) {
            System.out.println("Log start : " + System.currentTimeMillis());
            invoke = method.invoke(originalApp, args);
            System.out.println("Log over : " + System.currentTimeMillis());
        } else {
            invoke = method.invoke(originalApp, args);
        }
        return invoke;
    }
}

public class Main {
    private static boolean wasChange = false;
    private static App originalApp;


    public static void main(String[] args) throws Exception {

        App application = new Application();
        Controller controller = new Controller();
        ProcessScanner scanner = new ProcessScanner(controller);

        while (true) {
            System.out.println(application.getClass());
            application.printWithAnnotation();
            application.printWithOutAnnotation();
            System.out.println(controller.isPrintAspect());
            TimeUnit.SECONDS.sleep(1);

            if (controller.isPrintAspect() && !wasChange) {
                originalApp = application;
                application = (App) Proxy.newProxyInstance(application.getClass().getClassLoader()
                        , application.getClass().getInterfaces()
                        , new MyInvocationHandler(application));
                wasChange = true;
            }

            if (!controller.isPrintAspect() && wasChange) {
                application = originalApp;
                wasChange = false;
            }

        }
    }
}
