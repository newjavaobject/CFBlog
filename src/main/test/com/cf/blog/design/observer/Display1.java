package com.cf.blog.design.observer;

/**
 * 观察者实现1
 */
public class Display1 implements Observer, DisplayElement {
    @Override
    public void display() {
        System.out.println("观察者实现1");
    }

    @Override
    public void update() {
        display();
    }
}
