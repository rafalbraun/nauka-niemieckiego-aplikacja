package com.company;

import com.company.controller.Controller;
import com.company.events.AppEvent;
import com.company.model.Data;
import com.company.view.View;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Application {

    private static final BlockingQueue<AppEvent> blockingQueue  = new LinkedBlockingQueue<>();
    private static Data data;
    private static View view;

    public static void main(String[] args) {
        data = new Data();
        data.createLesson("zwierzęta");
        data.createLesson("opis pokoju");
        view = new View(blockingQueue, data);
        final Controller controller = new Controller(view, data, blockingQueue);
        controller.work();
    }

}
