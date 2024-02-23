package com.company;

import com.company.model.Data;

import javax.swing.*;

public class MainMenuBar extends JMenuBar {

    public MainMenuBar(Data data) {

        var iconNew = new ImageIcon("resources/new.png");
        var iconOpen = new ImageIcon("resources/open.png");
        var iconSave = new ImageIcon("resources/save.png");
        var iconExit = new ImageIcon("resources/exit.png");

        var fileMenu = new JMenu("File");
        var impMenu = new JMenu("Import");

        var newsMenuItem = new JMenuItem("Import newsfeed list...");
        var bookmarksMenuItem = new JMenuItem("Import bookmarks...");
        var importMailMenuItem = new JMenuItem("Import mail...");

        impMenu.add(newsMenuItem);
        impMenu.add(bookmarksMenuItem);
        impMenu.add(importMailMenuItem);

        var newMenuItem = new JMenuItem("New", iconNew);
        var openMenuItem = new JMenuItem("Open", iconOpen);
        var saveMenuItem = new JMenuItem("Save", iconSave);

        var exitMenuItem = new JMenuItem("Exit", iconExit);
        exitMenuItem.setToolTipText("Exit application");

        exitMenuItem.addActionListener((event) -> System.exit(0));

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(impMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        add(fileMenu);

        var lessonsMenu = new JMenu("Lessons");

        var createLessonItem = new JMenuItem("Create Lesson", iconNew);
        lessonsMenu.add(createLessonItem);

        createLessonItem.addActionListener((event) -> {

            JTextField textField = new JTextField();

            final JComponent[] inputs = new JComponent[] {
                    textField
            };

            int result = JOptionPane.showOptionDialog(
                    null,
                    inputs,
                    "Lesson name",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);

            //System.out.println(textField.getText());

            data.createLesson(textField.getText());
            //view.refresh();

        });

        add(lessonsMenu);

    }

    public void importLesson() {}

    public void exportLesson() {}

    public void createLesson() {}

    public void removeLesson() {}

    public void createWord() {}

    public void removeWord() {}

    public void updateWord() {}


}
