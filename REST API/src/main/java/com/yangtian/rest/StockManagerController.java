package com.yangtian.rest;

/**
 * @author Yang Tian
 * @date 21/02/2021 21:20
 */

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
public class StockManagerController {

    private final StockRepository repository;

    StockManagerController(StockRepository repository) {
        this.repository = repository;
    }

    //  list all
    @GetMapping("/listall")
    List<Stock> all() {
        return repository.findAll();
    }

    // single item
    @GetMapping("/item")
    public Stock singleItem(@RequestParam String item) {

        if (repository.existsByItem(item) != true) {

            throw new ItemNotFoundException(item);
        }
        return repository.findByItem(item);
    }

    // addItem
    @PostMapping ("/additem")
    public Stock addItem(@RequestParam(name = "item", defaultValue = "item") String item) {

        System.out.print(item);

        if (repository.existsByItem(item)) {
            throw new ItemExistedException(item);
        }

        Stock newItem = new Stock();
        newItem.setItem(item);
        newItem.setStockLevel(0);
        return repository.save(newItem);
    }

    // add stock
    @PutMapping("/addstock")
    public Stock addStock(@RequestParam(name = "item", defaultValue = "item") String item , @RequestParam(name = "stockLevel") int numItem) {

        if (repository.existsByItem(item) != true){
            throw new ItemNotFoundException(item);
        }

        Stock stock = repository.findByItem(item);
        int oldStockLevel = stock.getStockLevel();
        int newStockLevel = oldStockLevel+numItem;
        stock.setStockLevel(newStockLevel);
        return repository.save(stock);
    }

    // remove stock
    @PutMapping("/removestock")
    public Stock removeStock(@RequestParam(name = "item", defaultValue = "item") String item , @RequestParam(name = "stockLevel") int numItem) {

        if (repository.existsByItem(item) != true){
            throw new ItemNotFoundException(item);
        }

        Stock stock = repository.findByItem(item);
        int currentStock = stock.getStockLevel();
        if (numItem > currentStock) {
            throw new RuntimeException();
        }

        int oldStockLevel = stock.getStockLevel();
        int newStockLevel = oldStockLevel-numItem;
        stock.setStockLevel(newStockLevel);
        return repository.save(stock);
    }

    // delete item
    @DeleteMapping("/deleteitem")
    public void deleteItem(@RequestParam String item) {

        if (repository.existsByItem(item) != true){
            throw new ItemNotFoundException(item);
        }
        repository.deleteByItem(item);
    }
}
