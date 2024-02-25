package com.company.controller;

import com.company.events.*;
import com.company.model.Lesson;
import com.company.view.View;
import com.company.model.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Controller {

    BlockingQueue<AppEvent> blockingQueue;
    private final Map<Class<? extends AppEvent>, AppAction> eventActionMap;
    View view;
    Data data;

    public Controller(View view, Data data, BlockingQueue<AppEvent> blockingQueue) {
        this.blockingQueue = blockingQueue;
        this.view = view;
        this.data = data;
        eventActionMap = new HashMap<Class<? extends AppEvent>, AppAction>();
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
        eventActionMap.put(AddWordToLessonEvent.class, new AppAction() {
            public void go(AppEvent event) {
                String word1 = ((AddWordToLessonEvent)event).getWordFirst();
                String word2 = ((AddWordToLessonEvent)event).getWordSecond();
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
    }

}
