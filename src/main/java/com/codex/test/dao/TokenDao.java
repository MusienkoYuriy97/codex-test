package com.codex.test.dao;

import com.codex.test.model.Token;
import java.util.List;
import java.util.Optional;

public interface TokenDao {
    void save(Token token);
    List<Token> getAll();
    boolean delete(Token token);
    Optional<Token> get(String tokenId);
    boolean containsByUsername(String username);
    boolean containsByTokenId(String tokenId);
}
