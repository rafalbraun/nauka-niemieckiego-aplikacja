package com.company.controller;

import com.company.events.*;
import com.company.model.Lesson;
import com.company.view.View;
import com.company.model.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Controller {

    private final BlockingQueue<AppEvent> blockingQueue;
    private final Map<Class<? extends AppEvent>, AppAction> eventActionMap;
    private final View view;
    private final Data data;

    public Controller(View view, Data data, BlockingQueue<AppEvent> blockingQueue) {
        this.blockingQueue = blockingQueue;
        this.view = view;
        this.data = data;
        this.eventActionMap = new HashMap<Class<? extends AppEvent>, AppAction>();
        fillEventActionMap();
    }

    public void work() {
        while (true){
            try {
                AppEvent event = blockingQueue.take();
                AppAction action = eventActionMap.get(event.getClass());
                action.go(event);
            } catch(Exception e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private void fillEventActionMap() {

        eventActionMap.put(CreateLessonEvent.class, new AppAction() {
            public void go(AppEvent event) {
                String lessonName = ((CreateLessonEvent)event).getName();
                if ("".equals(lessonName)) return;
                Lesson lesson = data.createLesson(lessonName);
                data.setChosenLesson(lesson);
                view.refresh(data);
            }
        });

        eventActionMap.put(CreateWordLessonEvent.class, new AppAction() {
            public void go(AppEvent event) {
                String word1 = ((CreateWordLessonEvent)event).getWordFirst();
                String word2 = ((CreateWordLessonEvent)event).getWordSecond();
                if ("".equals(word1) || "".equals(word2)) return;
                Lesson lesson = data.getChosenLesson();
                lesson.addWord(word1, word2);
                view.refresh(data);
            }
        });

        eventActionMap.put(ChooseLessonEvent.class, new AppAction() {
            public void go(AppEvent event) {
                String lessonName = ((ChooseLessonEvent)event).getLessonName();
                Lesson lesson = data.getLessons().get(lessonName);
                data.setChosenLesson(lesson);
                view.refreshWords(data);
            }
        });

        eventActionMap.put(ShowTestDialogEvent.class, new AppAction() {
            public void go(AppEvent event) {
                view.showTestDialog(data);
            }
        });

        eventActionMap.put(RemoveWordLessonEvent.class, new AppAction() {
            public void go(AppEvent event) {
                String word1 = ((RemoveWordLessonEvent)event).getWordFirst();
                String word2 = ((RemoveWordLessonEvent)event).getWordSecond();
                Lesson lesson = data.getChosenLesson();
                lesson.removeWord(word1, word2);
                view.refresh(data);
            }
        });

        eventActionMap.put(MoveUpWordLessonEvent.class, new AppAction() {
            public void go(AppEvent event) {
                int selectedRow = ((MoveUpWordLessonEvent)event).getSelectedRow();
                if (selectedRow == 0) return;
                if (data.getChosenLesson().getWordList().size() < 2) return;
                Collections.swap(data.getChosenLesson().getWordList(), selectedRow, selectedRow-1);
                view.refresh(data);
            }
        });

        eventActionMap.put(MoveDnWordLessonEvent.class, new AppAction() {
            public void go(AppEvent event) {
                int selectedRow = ((MoveDnWordLessonEvent)event).getSelectedRow();
                if (selectedRow == data.getChosenLesson().getWordList().size()-1) return;
                if (data.getChosenLesson().getWordList().size() < 2) return;
                Collections.swap(data.getChosenLesson().getWordList(), selectedRow, selectedRow+1);
                view.refresh(data);
            }
        });

    }

}
