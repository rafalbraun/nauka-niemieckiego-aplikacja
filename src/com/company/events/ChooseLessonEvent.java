package com.company.events;

public class ChooseLessonEvent extends AppEvent {

    String lessonName;

    public ChooseLessonEvent(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonName() {
        return lessonName;
    }


}
