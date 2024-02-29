package com.company.events;

public class RemoveLessonEvent extends AppEvent {

    String name;

    public RemoveLessonEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
