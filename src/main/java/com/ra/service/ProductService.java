package com.ra.service;

import com.ra.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product saveOrUpdate(Product product);
    Product findById(Integer id);
    void deleteById(Integer id);
}
