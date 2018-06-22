package com.cf.blog.design.a;

public abstract class Duck {
    public void swim(){
        System.out.println("游泳");
    }

    public abstract void display();//行为不同，设计为抽象方法
}
