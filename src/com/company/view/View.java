package com.company.view;

import com.company.model.Data;
import com.company.model.DataPack;
import com.company.events.AppEvent;
import com.company.model.Lesson;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;

public class View {

    private AppWindow window;
    private BlockingQueue<AppEvent> blockingQueue;

    public View(BlockingQueue<AppEvent> blockingQueue, final Data data) {
        this.blockingQueue = blockingQueue;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window = new AppWindow();
                refresh(data);
            }
        });

    }

    public void refresh(Data data) {
        //DefaultListModel<String> demoList = new DefaultListModel<String>();
        //demoList.addElement("addElements");
        //setModel(demoList);

        System.out.println("refresh " + data.getLessons().size());

        window.refreshLessons(data);

    }

    class AppWindow {

        JList<Lesson> lessonsSidebar;

        AppWindow() {

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }

            lessonsSidebar = new JList<Lesson>();
            JPanel panel = new JPanel();

            JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            mainSplitPane.setTopComponent(lessonsSidebar);
            mainSplitPane.setBottomComponent(panel);
            mainSplitPane.setDividerLocation(200);
            //mainSplitPane.setLayout(new BorderLayout());

            JMenuBar menuBar = new MainMenuBar(blockingQueue);

            JFrame frame = new JFrame("Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setJMenuBar(menuBar);
            //frame.add(lessonsSidebar);
            frame.add(mainSplitPane);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setSize(new Dimension(800, 600));
            frame.setLocationRelativeTo(null);
        }

        public void refreshLessons(Data data) {
            DefaultListModel<Lesson> modelList = new DefaultListModel<Lesson>();
            Collection<Lesson> lessons = data.getLessons();
            for(Lesson lesson : lessons) {
                modelList.addElement(lesson);
            }
            lessonsSidebar.setModel(modelList);
        }
    }



}
