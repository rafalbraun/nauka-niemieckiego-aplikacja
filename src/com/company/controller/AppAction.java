package com.company.controller;

import com.company.events.AppEvent;

public interface AppAction {
    void go(AppEvent e);
}