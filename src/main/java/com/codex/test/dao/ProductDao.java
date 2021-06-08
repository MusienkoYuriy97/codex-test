package com.codex.test.dao;

import com.codex.test.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    void save(Product product);
    Optional<Product> getByName(String name);
    List<Product> getByTags(String tag);
    List<Product> getByDescription(String description);
    boolean update(Product product);
    void remove(String productName);
    boolean contains(String name);
}
