package com.company;

import com.company.model.Data;

import javax.swing.*;
import java.awt.*;

public class Application {

    private static Data data;

    public static void main(String[] args) {
        data = new Data("db.dat");
        //data.readData();

        data.createLesson("zwierzęta");
        data.createLesson("opis pokoju");

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JMenuBar menuBar = new MainMenuBar(data);
                JPanel lessonPanel = new LessonPanel(data);

                JFrame frame = new JFrame("Application");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setJMenuBar(menuBar);
                frame.add(lessonPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setLocationRelativeTo(null);
            }
        });
    }

}
