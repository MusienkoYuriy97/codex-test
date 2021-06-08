package com.codex.test.controller;

import com.codex.test.model.Product;
import com.codex.test.dto.ProductDTO;
import com.codex.test.service.ProductService;
import com.codex.test.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ProductService productService;



    @GetMapping("/tag")
    public ResponseEntity<List<Product>> getByTag(String tag, @RequestHeader("X-Token")String tokenId){
        if (tokenService.isExist(tokenId)) {
            List<Product> findByTags = productService.findByTags(tag);
            return new ResponseEntity<>(findByTags,HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/description")
    public ResponseEntity<List<Product>> getByDescription(@RequestBody ProductDTO productDTO,@RequestHeader("X-Token")String tokenId){
        if (tokenService.isExist(tokenId)) {
            String description = productDTO.getDescription();
            List<Product> findByTags = productService.findByDescription(description);
            return new ResponseEntity<>(findByTags, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
