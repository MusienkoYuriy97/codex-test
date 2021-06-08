package com.codex.test.dao.inmemory;

import com.codex.test.dao.ProductDao;
import com.codex.test.model.Product;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryProductDao implements ProductDao {
    private List<Product> products = new ArrayList<>();


    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public Optional<Product> getByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)){
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getByTags(String tag) {
        List<Product> list = new ArrayList<>();
        for (Product product : products) {
           if (product.getTags().contains(tag)){
               list.add(product);
           }
        }
        return list;
    }

    @Override
    public List<Product> getByDescription(String description) {
        List<Product> list = new ArrayList<>();
        for (Product product : products) {
            if (product.getDescription().contains(description)){
                list.add(product);
            }
        }
        return list;
    }

    @Override
    public boolean update(Product product) {
        for (Product product1 : products) {
            if (product1.getName().equals(product.getName())){
                product1.setDescription(product.getDescription());
                product1.setTags(product.getTags());
                return true;
            }
        }
        return false;
    }

    @Override
    public void remove(String productName) {
        products.removeIf(product -> product.getName().equals(productName));
    }

    @Override
    public boolean contains(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
