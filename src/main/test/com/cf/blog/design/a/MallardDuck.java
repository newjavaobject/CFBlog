package com.cf.blog.design.a;

public class MallardDuck extends Duck implements Flyable,Quack {
    @Override
    public void display() {
        System.out.println("绿色的鸭子。");
    }

    @Override
    public void fly() {

    }

    @Override
    public void quack() {

    }
}
