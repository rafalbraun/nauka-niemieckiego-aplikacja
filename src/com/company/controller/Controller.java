package com.company.controller;

import com.company.view.View;
import com.company.events.AppEvent;
import com.company.events.CreateLessonEvent;
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

                data.createLesson(((CreateLessonEvent)event).getName());
                view.refresh(data);
            }
        });
    }

}
