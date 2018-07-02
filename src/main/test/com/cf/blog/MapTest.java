package com.cf.blog;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    Map<String, Object> map = new HashMap<String, Object>();
    @Test
    public void mapTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable(){
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        map.put("j"+j, j);
                    }
                }
            });
            t.start();
            t.join();
        }
    }
}
