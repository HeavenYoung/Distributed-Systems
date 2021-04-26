package com.yangtian.rest;

/**
 * @author Yang Tian
 * @date 21/02/2021 21:37
 */

import org.springframework.data.jpa.repository.JpaRepository;

interface StockRepository extends JpaRepository<Stock, Long> {

    public Stock findByItem(String item);
    public boolean existsByItem(String item);

    public void deleteByItem(String item);
}

