package com.cf.blog.design.observer.jdk;

import com.cf.blog.design.observer.DisplayElement;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者实现1
 */
public class Display1 implements Observer, DisplayElement {
    @Override
    public void display() {
        System.out.println("观察者实现1");
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
