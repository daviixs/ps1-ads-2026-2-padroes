package br.edu.fatecfranca.api.services.contracts;

import java.util.List;
import java.util.Optional;

import br.edu.fatecfranca.api.entities.User;

public interface UserCrudService {

    User create(User user);

    List<User> findAll();

    Optional<User> findById(Long id);

    User update(User user);

    boolean existsById(Long id);

    void deleteById(Long id);
}
