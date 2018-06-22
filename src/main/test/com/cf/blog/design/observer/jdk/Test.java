package com.cf.blog.design.observer.jdk;

public class Test {
    public static void main(String[] args) {
        WeatherData data = new WeatherData();

        Display1 d1 = new Display1();
        Display2 d2 = new Display2();
        Display3 d3 = new Display3();

        data.addObserver(d1);
        data.addObserver(d2);

        data.setChanged();
        data.notifyObservers();

        d3.update(data, null);
    }
}
