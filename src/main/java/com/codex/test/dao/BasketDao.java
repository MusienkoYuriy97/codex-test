package com.codex.test.dao;

import com.codex.test.model.Product;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BasketDao {
    Map<String,List<Product>> get();
    List<Product> getByUsername(String username);
    Set<String> getUserNamesByProduct(String productName);
    void save(String username, Product product);
    void update(String username, Product product);
    boolean remove(String username,String productName);
    boolean removeAll(String username);
    boolean contains(String username, String productName);
}
