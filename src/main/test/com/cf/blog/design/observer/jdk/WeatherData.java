package com.cf.blog.design.observer.jdk;

import java.util.Observable;

/**
 * 实现主题接口
 */
public class WeatherData extends Observable {
    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }
}
