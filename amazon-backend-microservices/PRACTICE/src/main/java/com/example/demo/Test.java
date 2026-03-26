package com.example.demo;

import java.util.*;

public class Test {
    

    public static void main(String[] args) throws Exception {
    	
    	List<Integer> list = new ArrayList<>();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Size: " + list.size());
    }
}