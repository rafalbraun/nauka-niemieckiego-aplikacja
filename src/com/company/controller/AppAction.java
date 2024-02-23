package com.company.controller;

import com.company.events.AppEvent;

public interface AppAction {
    abstract public void go(AppEvent e);
}