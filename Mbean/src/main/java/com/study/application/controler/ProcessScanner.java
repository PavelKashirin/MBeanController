package com.study.application.controler;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class ProcessScanner {
    Controller controller;

    public ProcessScanner(Controller controller) throws Exception {
        final MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        this.controller = controller;
        platformMBeanServer.registerMBean(controller, new ObjectName("aspecting", "name", "controlPrint"));
    }
}
