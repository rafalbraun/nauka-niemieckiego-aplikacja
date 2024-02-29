package com.company.events;

public class MoveUpWordLessonEvent extends AppEvent {

    int selectedRow;

    public MoveUpWordLessonEvent(int selectedRow) {
        this.selectedRow = selectedRow;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

}
