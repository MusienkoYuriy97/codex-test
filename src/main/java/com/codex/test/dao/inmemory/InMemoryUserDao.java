package com.codex.test.dao.inmemory;


import com.codex.test.dao.UserDao;
import com.codex.test.model.User;
import com.codex.test.model.UserRole;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryUserDao implements UserDao {
    private static List<User> users = new ArrayList<>();

    public List<User> get() {
        return users;
    }

    public void save(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> getByUsername(String username)  {
        for (User user : users) {
            if (user.getUsername().equals(username)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getByUsernameAndPassword(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }


    public boolean contains(String username){
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public boolean contains(String username, String password){
        return users.stream().anyMatch(user -> user.getUsername().equals(username)
                && user.getPassword().equals(password));
    }

    static {
        users.add(new User(UserRole.ADMIN,"Admin","1111","97musienko@gmail.com"));
    }
}
