package com.yangtian.rest;

/**
 * @author Yang Tian
 * @date 21/02/2021 22:58
 */
public class ItemExistedException extends RuntimeException{
    ItemExistedException(String item) {
        super(item + " has existed");
    }
}
