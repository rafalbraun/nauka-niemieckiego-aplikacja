package com.company;

import com.company.controller.Controller;
import com.company.events.AppEvent;
import com.company.model.Data;
import com.company.view.View;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Application {

    private static final BlockingQueue<AppEvent> blockingQueue  = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        final Data data = new Data();
        final View view = new View(blockingQueue, data);
        final Controller controller = new Controller(view, data, blockingQueue);
        controller.work();
    }

}
