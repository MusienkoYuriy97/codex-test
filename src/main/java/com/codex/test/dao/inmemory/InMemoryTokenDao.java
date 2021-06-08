package com.codex.test.dao.inmemory;


import com.codex.test.dao.TokenDao;
import com.codex.test.model.Token;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryTokenDao implements TokenDao {
    private List<Token> tokens = new ArrayList<>();



    @Override
    public void save(Token token) {
        tokens.add(token);
    }

    @Override
    public List<Token> getAll() {
        return tokens;
    }

    @Override
    public boolean delete(Token token) {
        return tokens.remove(token);
    }


    @Override
    public Optional<Token> get(String tokenId) {
        for (Token token : tokens) {
            if (token.getTokenId().equals(tokenId)){
                return Optional.of(token);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean containsByUsername(String username) {
        for (Token token : tokens) {
            if (token.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsByTokenId(String tokenId) {
        for (Token token : tokens) {
            if (token.getTokenId().equals(tokenId)){
                return true;
            }
        }
        return false;
    }


}
