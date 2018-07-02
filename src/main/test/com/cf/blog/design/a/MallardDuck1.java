package com.cf.blog.design.a;

/**
 * 测试重写静态方法
 *
 * 静态方法不能重写
 */
public class MallardDuck1 extends MallardDuck{
//    public static void aaaaa(){
//        System.out.println("bbb");
//    }

    public static void main(String[] args) {
//        MallardDuck.aaaaa();
        MallardDuck1.aaaaa();
    }
}
