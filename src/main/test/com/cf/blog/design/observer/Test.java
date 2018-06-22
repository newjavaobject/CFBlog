package com.cf.blog.design.observer;

public class Test {
    public static void main(String[] args) {
        WeatherData data = new WeatherData();

        Display1 d1 = new Display1();
        Display2 d2 = new Display2();
        Display3 d3 = new Display3();

        data.registerObserver(d2);
        data.registerObserver(d3);

        data.notifyObservers();
    }
}
