package com.company.events;

public class CreateLessonEvent extends AppEvent {

    String name;

    public CreateLessonEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
