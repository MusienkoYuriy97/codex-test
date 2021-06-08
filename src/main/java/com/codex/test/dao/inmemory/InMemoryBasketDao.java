package com.codex.test.dao.inmemory;

import com.codex.test.dao.BasketDao;
import com.codex.test.model.Product;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class InMemoryBasketDao implements BasketDao {
    private Map<String, List<Product>> basket = new HashMap<>();

    @Override
    public Map<String, List<Product>> get() {
        return basket;
    }

    @Override
    public List<Product> getByUsername(String username) {
        if (basket.containsKey(username)){
            return basket.get(username);
        }
        return new ArrayList<>();
    }

    @Override
    public Set<String> getUserNamesByProduct(String productName) {
        Set<String> usernames = new HashSet<>();

        for (Map.Entry<String, List<Product>> stringListEntry : basket.entrySet()) {
            for (Product product : stringListEntry.getValue()) {
                if (productName.equals(product.getName())){
                    usernames.add(stringListEntry.getKey());
                }
            }
        }
        return usernames;
    }

    @Override
    public void save(String username, Product product) {
        if (basket.containsKey(username)){
            basket.get(username).add(product);
        }else {
            List<Product> products = new ArrayList<>();
            products.add(product);
            basket.put(username, products);
        }
    }

    @Override
    public void update(String username, Product product) {

    }


    @Override
    public boolean remove(String username, String productName) {
       Product product = find(username, productName);
       if (product != null){
           basket.get(username).remove(product);
           return true;
       }
       return false;
    }

    @Override
    public boolean removeAll(String username) {
        if (basket.containsKey(username)){
            basket.remove(username);
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(String username, String productName) {
        if (basket.containsKey(username)){
            for (Product product : basket.get(username)) {
                if (product.getName().equals(productName)){
                    return true;
                }
            }
        }
        return false;
    }

    private Product find(String username, String productName){
        if (basket.containsKey(username)){
            for (Product product : basket.get(username)) {
                if (product.getName().equals(productName)){
                    return product;
                }
            }
        }
        return null;
    }
}
