package com.company;

import com.company.controller.Controller;
import com.company.events.AppEvent;
import com.company.model.Data;
import com.company.view.View;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Application {

    private static final BlockingQueue<AppEvent> blockingQueue  = new LinkedBlockingQueue<>();

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                FontUIResource font = new FontUIResource("Verdana", Font.PLAIN, 24);
//                UIManager.put("Table.font", font);
//                UIManager.put("Table.foreground", Color.RED);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final Data data = new Data();
        final View view = new View(blockingQueue, data);
        final Controller controller = new Controller(view, data, blockingQueue);
        controller.work();
    }

}
