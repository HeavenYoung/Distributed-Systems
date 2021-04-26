package com.yangtian.grpcclient;

/**
 * @author Yang Tian
 * @date 01/04/2021 21:31
 */
public class GlobalSingleton {

    private volatile static GlobalSingleton instance = null;

    private GlobalSingleton() {

    }
    public int MAX;

    public static GlobalSingleton getInstance() {
        if (instance == null) {
            synchronized (GlobalSingleton.class) {
                if (instance == null) {
                    instance = new GlobalSingleton();
                }
            }

        }
        return instance;
    }
}
