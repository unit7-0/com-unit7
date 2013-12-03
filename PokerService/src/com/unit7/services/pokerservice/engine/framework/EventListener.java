package com.unit7.services.pokerservice.engine.framework;

public interface EventListener<T> {
    void update(T data);
    EventListener add(Subject subject);
}
