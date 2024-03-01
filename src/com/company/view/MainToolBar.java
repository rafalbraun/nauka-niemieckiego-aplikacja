package com.company.view;

import com.company.events.CreateWordLessonEvent;
import com.company.events.AppEvent;
import com.company.events.ShowTestDialogEvent;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;

public class MainToolBar extends JToolBar {

    JButton button;

    public MainToolBar(BlockingQueue<AppEvent> blockingQueue, Component rootComponent) {

        button = new JButton("Dodaj Słówko");
        button.addActionListener((event) -> {

            blockingQueue.add(new CreateWordLessonEvent());

        });
        add(button);

        button = new JButton("Test");
        button.addActionListener((event) -> {

            blockingQueue.add(new ShowTestDialogEvent());

        });
        add(button);

    }

}
