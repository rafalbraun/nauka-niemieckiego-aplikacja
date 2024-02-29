package com.company.view;

import com.company.events.AppEvent;
import com.company.events.CreateLessonEvent;
import com.company.events.SaveEvent;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

public class MainMenuBar extends JMenuBar {

    public MainMenuBar(BlockingQueue<AppEvent> blockingQueue, Component rootComponent) {

        var iconNew = new ImageIcon("resources/icons/new.png");
        var iconOpen = new ImageIcon("resources/icons/open.png");
        var iconSave = new ImageIcon("resources/icons/save.png");
        var iconExit = new ImageIcon("resources/icons/exit.png");

        var fileMenu = new JMenu("Plik");

        var importMenuItem = new JMenuItem("Importuj", iconOpen);
        var saveMenuItem = new JMenuItem("Zapisz", iconSave);
        var saveAsMenuItem = new JMenuItem("Zapisz jako ...", iconSave);
        var exitMenuItem = new JMenuItem("Zamknij", iconExit);

        exitMenuItem.addActionListener((event) -> System.exit(0));

        fileMenu.add(importMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);
        add(fileMenu);

        importMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String myDocuments = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
                System.out.println(myDocuments);


            }

        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockingQueue.add(new SaveEvent());
            }
        });

        var lessonsMenu = new JMenu("Lekcje");

        var createLessonItem = new JMenuItem("Dodaj Lekcję", iconNew);
        lessonsMenu.add(createLessonItem);

        createLessonItem.addActionListener((event) -> {

            JTextField textField = new JTextField();

            final JComponent[] inputs = new JComponent[] {
                    textField
            };

            int result = JOptionPane.showOptionDialog(
                    null,
                    inputs,
                    "Nazwa Lekcji",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);

            //System.out.println(textField.getText());
            //data.createLesson(textField.getText());
            //view.refresh();

            blockingQueue.add(new CreateLessonEvent(textField.getText()));

        });

        add(lessonsMenu);

        var helpMenu = new JMenu("Pomoc");
        var aboutItem = new JMenuItem("O Programie", iconNew);
        helpMenu.add(aboutItem);
        add(helpMenu);

        aboutItem.addActionListener((event) -> {

            int choice = JOptionPane.showConfirmDialog(rootComponent,
                    """
                            Autor: Rafał Braun\s
                            Wersja: 1.0.0\s
                            """,
                    "O Programie", JOptionPane.DEFAULT_OPTION);

        });

    }

    public void importLesson() {}

    public void exportLesson() {}

    public void createLesson() {}

    public void removeLesson() {}

    public void createWord() {}

    public void removeWord() {}

    public void updateWord() {}


}
