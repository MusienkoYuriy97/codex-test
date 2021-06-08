package com.codex.test.controller;

import com.codex.test.model.Product;
import com.codex.test.service.ProductService;
import com.codex.test.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Product product, @RequestHeader("X-Token")String adminTokenId){
        if (tokenService.isAdmin(adminTokenId)) {
            if (productService.save(product)) {
                return new ResponseEntity<>("Product successfully added", HttpStatus.ACCEPTED);
            }else {
                return new ResponseEntity<>("Product with the same name already exist", HttpStatus.ACCEPTED);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/remove")
    public ResponseEntity<String> remove(String productName, @RequestHeader("X-Token")String adminTokenId){
        if (tokenService.isAdmin(adminTokenId)) {
            if (productService.drop(productName)){
                return new ResponseEntity<>("Successfully drop item - " + productName,HttpStatus.ACCEPTED);
            }

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Product product, @RequestHeader("X-Token")String adminTokenId){
        if (tokenService.isAdmin(adminTokenId)) {
            if (productService.update(product)){
                return new ResponseEntity<>(product.getName() + " successfully updated",HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>("Either the product is already in basket or such a product does not exist", HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/forceupdate")
    public ResponseEntity<String> forceUpdate(@RequestBody Product product, @RequestHeader("X-Token")String adminTokenId){
        if (tokenService.isAdmin(adminTokenId)) {
            if (productService.forceUpdate(product)) {
                return new ResponseEntity<>(product.getName() + " successfully updated", HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
