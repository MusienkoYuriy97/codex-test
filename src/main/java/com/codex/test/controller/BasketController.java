package com.codex.test.controller;

import com.codex.test.model.ResponseApi;
import com.codex.test.model.User;
import com.codex.test.service.BasketService;
import com.codex.test.service.ProductService;
import com.codex.test.service.TokenService;
import com.codex.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/basket")
public class BasketController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private BasketService basketService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<ResponseApi> addProduct(@RequestHeader("X-Token") String tokenId, String productName){

        if (tokenService.isUser(tokenId) && productService.contains(productName)){
            ResponseApi responseApi = new ResponseApi();
            String username = tokenService.getUserNameByToken(tokenId);
            User user = userService.getByUsername(username);

            responseApi.setUser(user);
            if (basketService.add(tokenService.getUserNameByToken(tokenId),
                    productName)){
                responseApi.setMessage(basketService.length(username) + " products in basket");
            }else {
                responseApi.setMessage("The same product already in your basket");
            }
            responseApi.setProduct(basketService.get(username));
            return new ResponseEntity<>(responseApi, HttpStatus.ACCEPTED);

        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> remove(@RequestHeader("X-Token") String tokenId, String productName){
        if (tokenService.isUser(tokenId)){
            if (basketService.remove(tokenService.getUserNameByToken(tokenId),
                    productName)){
                return new ResponseEntity<>("Successfully remove " + basketService.length(tokenService.getUserNameByToken(tokenId)) + " products in basket", HttpStatus.ACCEPTED);
            }

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buy(@RequestHeader("X-Token") String tokenId){
        if (tokenService.isUser(tokenId)){
            if (basketService.buyProduct(tokenService.getUserNameByToken(tokenId)) != 0){
                return new ResponseEntity<>("Successfully buy",HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
