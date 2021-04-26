package com.yangtian.rest;

/**
 * @author Yang Tian
 * @date 21/02/2021 21:22
 */

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Stock {

    @Id
    private @GeneratedValue Long id;

    private String item;
    private int stockLevel;

   public Stock(String item,int stockLevel) {
        this.item = item;
        this.stockLevel = stockLevel;
    }

    public Stock() {
       this.stockLevel = 0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public Long getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + this.id + ", name='" + this.item + '\'' + ", stockLevel='" + this.stockLevel + '\'' + '}';
    }
}
