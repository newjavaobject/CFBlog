package com.cf.blog.design.observer;

/**
 * 观察者实现3
 */
public class Display3 implements Observer, DisplayElement {
    @Override
    public void display() {
        System.out.println("观察者实现3");
    }

    @Override
    public void update() {
        display();
    }
}
