package com.company;

import com.company.model.Data;
import com.company.model.Lesson;

import javax.swing.*;
import java.util.Collection;

public class LessonsSidebar extends JList {

    public LessonsSidebar(Data data) {

        //DefaultListModel<String> demoList = new DefaultListModel<String>();
        //demoList.addElement("addElements");
        //setModel(demoList);

        DefaultListModel<Lesson> modelList = new DefaultListModel<Lesson>();
        Collection<Lesson> lessons = data.getLessons();
        for(Lesson lesson : lessons) {
            modelList.addElement(lesson);
        }
        setModel(modelList);

    }

}
