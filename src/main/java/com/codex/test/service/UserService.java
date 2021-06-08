package com.codex.test.service;

import com.codex.test.dao.TokenDao;
import com.codex.test.dao.UserDao;
import com.codex.test.model.User;
import com.codex.test.dto.UserDTO;
import com.codex.test.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    TokenDao tokenDao;

    public boolean save(UserDTO userDTO){
        if (userDao.contains(userDTO.getUsername())){
            return false;
        }

        User user = new User();
        user.setRole(UserRole.USER);
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        userDao.save(user);
        return true;
    }

    public User getByUsername(String username){
        return userDao.getByUsername(username).orElse(null);
    }

    public boolean checkAuthorization(String username, String password){
        if (tokenDao.containsByUsername(username)){
            return false;
        }
        Optional<User> optionalUser = userDao.getByUsernameAndPassword(username,password);
        return optionalUser.isPresent();
    }

}
