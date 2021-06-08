package com.codex.test.service;

import com.codex.test.dao.TokenDao;
import com.codex.test.dao.UserDao;
import com.codex.test.model.Token;
import com.codex.test.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenService {
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private UserDao userDao;

    public boolean delete(String tokenId){
        if (tokenDao.containsByTokenId(tokenId)) {
            tokenDao.delete(tokenDao.get(tokenId).get());
            return true;
        }
        return false;


    }

    public void save(String tokenId, String username){
        tokenDao.save(new Token(tokenId,username));
    }

    public boolean isAdmin(String tokenId){
        if (tokenDao.containsByTokenId(tokenId)) {
            Optional<Token> token = tokenDao.get(tokenId);
            String username = token.get().getUsername();
            UserRole role = userDao.getByUsername(username).get().getRole();
            return role.equals(UserRole.ADMIN);
        }
        return false;
    }

    public boolean isUser(String tokenId){
        if (tokenDao.containsByTokenId(tokenId)) {
            Optional<Token> token = tokenDao.get(tokenId);
            String username = token.get().getUsername();
            UserRole role = userDao.getByUsername(username).get().getRole();
            return role.equals(UserRole.USER);
        }
        return false;
    }

    public boolean isExist(String tokenId){
        if (tokenDao.containsByTokenId(tokenId)) {
            return true;
        }
        return false;
    }

    public String getUserNameByToken(String tokenId){
        if (tokenDao.containsByTokenId(tokenId)){
            return tokenDao.get(tokenId).get().getUsername();
        }
        return "";
    }

    public Token tokenGenerate(String username){
        String tokenId = UUID.randomUUID().toString();
        Token token = new Token(tokenId,username);
        tokenDao.save(token);
        return token;
    }
}
