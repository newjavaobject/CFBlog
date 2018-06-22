package com.cf.blog.design;

public abstract class Duck {
    public void quack(){
        System.out.println("guaguajiao");
    }

    public void swim(){
        System.out.println("游泳");
    }

    public abstract void display();//行为不同，设计为抽象方法

    /**
     * 扩展，所有的鸭子都具有的飞的行为
     *
     * 当涉及维护时，为了 "复用" 目的而使用继承，并不完美
     */
    public void fly(){
        System.out.println("会飞的鸭子..");
    }
}
