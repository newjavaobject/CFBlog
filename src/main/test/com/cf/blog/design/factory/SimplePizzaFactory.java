package com.cf.blog.design.factory;

/**
 * 创建Pizza的工厂类
 */
public class SimplePizzaFactory {
    public static Pizza getPizza(String type) {
        if (type.equals("a"))
            return new PizzaA();
        if (type.equals("b"))
            return new PizzaB();
        return null;
    }

    public static void main(String[] args) {
        getPizza("b");
    }
}
