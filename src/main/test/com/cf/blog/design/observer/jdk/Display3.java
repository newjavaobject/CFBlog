package com.cf.blog.design.observer.jdk;

import com.cf.blog.design.observer.DisplayElement;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者实现3
 */
public class Display3 implements Observer, DisplayElement {
    @Override
    public void display() {
        System.out.println("观察者实现3");
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
