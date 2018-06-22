package com.cf.blog.design.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现主题接口
 */
public class WeatherData implements Subject {
    private List<Observer> observers;
    private int a;
    private int b;
    private int c;

    public WeatherData(){
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        synchronized (observers) {
            for (Observer observer : observers) {
                observer.update();
            }
        }
    }
}
