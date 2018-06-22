package com.cf.blog.design.b;

public class MallardDuck extends Duck {
    public MallardDuck(){
        fly = new Flyable() {
            @Override
            public void fly() {

            }
        };
        quack = new Quack() {
            @Override
            public void quack() {
            }
        };
    }

    @Override
    public void display() {
        System.out.println("绿色的鸭子。");
    }
}
