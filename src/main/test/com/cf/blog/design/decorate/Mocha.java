package com.cf.blog.design.decorate;

/**
 * 装饰者
 */
public class Mocha extends Beberage {
    Beberage beberage;
    public Mocha(Beberage beberage) {
        this.beberage = beberage;
    }

    public Beberage getBeberage() {
        return beberage;
    }

    public void setBeberage(Beberage beberage) {
        this.beberage = beberage;
    }

    @Override
    public float cost() {
        return super.cost() + beberage.cost();
    }
}
