package br.edu.fatecfranca.api.factories;

import org.springframework.stereotype.Component;

import br.edu.fatecfranca.api.entities.User;

@Component
public class UserFactory extends EntityFactory<User> {

    @Override
    protected User newEntity() {
        return new User();
    }

    public User create(User source) {
        User user = newEntity();
        user.setFullname(source.getFullname());
        user.setUsername(source.getUsername());
        user.setEmail(source.getEmail());
        user.setPassword(source.getPassword());
        user.setIsAdmin(source.getIsAdmin());
        return user;
    }
}
