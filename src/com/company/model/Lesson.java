package com.company.model;

import java.util.LinkedList;
import java.util.List;

public class Lesson {

    String lessonName;

    List<Word> lessonWords = new LinkedList<Word>();

    public Lesson(String lessonName) {
        this.lessonName = lessonName;
    }

    @Override
    public String toString() {
        return lessonName;
    }
}
