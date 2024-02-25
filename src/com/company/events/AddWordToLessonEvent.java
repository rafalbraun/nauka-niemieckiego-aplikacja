package com.company.events;

import com.company.model.Lesson;

public class AddWordToLessonEvent extends AppEvent {

    String word1, word2;

    public AddWordToLessonEvent(String word1, String word2) {
        this.word1 = word1;
        this.word2 = word2;
    }

    public String getWordFirst() {
        return word1;
    }

    public String getWordSecond() {
        return word2;
    }

}
