package com.codex.test.controller;

import com.codex.test.model.Token;
import com.codex.test.model.User;
import com.codex.test.dto.UserDTO;
import com.codex.test.service.TokenService;
import com.codex.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/reg")
    public ResponseEntity<User> save(@RequestBody UserDTO userDTO){
        if (userService.save(userDTO)) {
            return new ResponseEntity<>(userService.getByUsername(userDTO.getUsername()), HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/auth")
    public ResponseEntity<Token> auth(String username, String password){
        if (userService.checkAuthorization(username,password)){
            Token token = tokenService.tokenGenerate(username);
            return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("X-Token") String tokenId){
        if (tokenService.delete(tokenId)) {
            return new ResponseEntity<>("Success logout", HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
