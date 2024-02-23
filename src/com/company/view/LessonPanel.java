package com.company.view;

import javax.swing.*;
import java.awt.*;

public class LessonPanel extends JPanel {
//    JLabel askedWord = new JLabel();
//    JTextField textField = new JTextField();
//    JButton checkButton = new JButton("Check");

    public LessonPanel(LessonsSidebar lessonsSidebar, JPanel panel) {

        setLayout(new BorderLayout());

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        mainSplitPane.setTopComponent(lessonsSidebar);
        mainSplitPane.setBottomComponent(panel);

        //mainSplitPane.setLayout(new BorderLayout());

        add(mainSplitPane);

//        add(askedWord);
//        add(textField);
//        add(checkButton);
//        askedWord.setText("marchew");
//
//        checkButton.addActionListener(e -> {
//            System.out.println("aaa");
//        });
    }



}
