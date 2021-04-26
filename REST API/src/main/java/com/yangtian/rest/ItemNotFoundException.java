package com.yangtian.rest;

/**
 * @author Yang Tian
 * @date 21/02/2021 23:30
 */
public class ItemNotFoundException extends  RuntimeException{
    ItemNotFoundException(String item) {
        super(item + " dose not exist");
    }
}
