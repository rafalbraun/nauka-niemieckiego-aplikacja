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

            JTextField textField1 = new JTextField();
            JTextField textField2 = new JTextField();

            final JComponent[] inputs = new JComponent[] {
                    textField1, textField2
            };

            int result = JOptionPane.showOptionDialog(
                    null,
                    inputs,
                    "Dodaj Słówko",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);

            // TODO check result

            blockingQueue.add(new CreateWordLessonEvent(textField1.getText(), textField2.getText()));

        });
        add(button);

        button = new JButton("Test");
        button.addActionListener((event) -> {

            blockingQueue.add(new ShowTestDialogEvent());

        });
        add(button);

    }

}
