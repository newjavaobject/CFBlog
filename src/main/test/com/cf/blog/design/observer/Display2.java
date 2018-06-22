package com.cf.blog.design.observer;

/**
 * 观察者实现2
 */
public class Display2 implements Observer, DisplayElement {
    @Override
    public void display() {
        System.out.println("观察者实现2");
    }

    @Override
    public void update() {
        display();
    }
}
