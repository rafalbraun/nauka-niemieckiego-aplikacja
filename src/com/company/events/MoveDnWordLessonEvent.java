package com.company.events;

public class MoveDnWordLessonEvent extends AppEvent {

    int selectedRow;

    public MoveDnWordLessonEvent(int selectedRow) {
        this.selectedRow = selectedRow;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

}
