package com.codex.test.service;

import com.codex.test.dao.BasketDao;
import com.codex.test.dao.ProductDao;
import com.codex.test.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BasketService {
    @Autowired
    private BasketDao basketDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender javaMailSender;

    public boolean add(String username, String productName){
        Product product = productDao.getByName(productName).get();

        if (basketDao.contains(username,product.getName())){
            return false;
        }else {
            basketDao.save(username,product);
            return true;
        }
    }

    public boolean remove(String username, String productName) {
        return basketDao.remove(username,productName);
    }

    public int length(String username){
        return basketDao.get().get(username).size();
    }

    public List<Product> get(String username){
        return basketDao.getByUsername(username);
    }

    public int buyProduct(String username){
        List<Product> products = basketDao.getByUsername(username);
        if (products.size() != 0){
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("teachmusienko@gmail.com");
            mailMessage.setSubject("Buy Product");
            mailMessage.setText(products.toString());
            mailMessage.setTo("97musienko@gmail.com");
            javaMailSender.send(mailMessage);
            basketDao.removeAll(username);
            return products.size();
        }else {
            return 0;
        }
    }

}
