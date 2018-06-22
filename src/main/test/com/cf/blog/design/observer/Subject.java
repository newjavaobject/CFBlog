package com.cf.blog.design.observer;

/**
 * 主题接口，即观察者敢兴趣的对象接口
 */
public interface Subject {
    void registerObserver(Observer observer);

    void deleteObserver(Observer observer);

    /*通知观察者*/
    void notifyObservers();
}
