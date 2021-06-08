package com.codex.test.service;

import com.codex.test.dao.BasketDao;
import com.codex.test.dao.ProductDao;
import com.codex.test.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BasketDao basketDao;
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender javaMailSender;

    public boolean save(Product product){
        if (!productDao.contains(product.getName())) {
            productDao.save(product);
            return true;
        }else {
            return false;
        }
    }

    public boolean contains(String productName){
        if (productDao.getByName(productName).isPresent()) {
            return true;
        }
        return false;
    }

    public List<Product> findByTags(String tag){
        return productDao.getByTags(tag);
    }

    public List<Product> findByDescription(String description) {
        return productDao.getByDescription(description);
    }

    public boolean drop(String productName) {
        if (productDao.contains(productName)){
            productDao.remove(productName);
            return true;
        }
        return false;
    }

    public boolean update(Product product) {
        for (Map.Entry<String, List<Product>> stringListEntry : basketDao.get().entrySet()) {
            for (Product product1 : stringListEntry.getValue()) {
                if (product1.getName().equals(product.getName())){
                    return false;
                }
            }
        }
        return productDao.update(product);
    }

    public boolean forceUpdate(Product product) {

        if (productDao.update(product)) {
            for (String usernames : basketDao.getUserNamesByProduct(product.getName())) {
                String email = userService.getByUsername(usernames).getEmail();
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom("teachmusienko@gmail.com");
                mailMessage.setSubject("Buy Product");
                mailMessage.setText("Product in your basket is changed, check it");
                mailMessage.setTo("97musienko@gmail.com");
                javaMailSender.send(mailMessage);
            }
            return true;
        }
        return false;
    }
}
