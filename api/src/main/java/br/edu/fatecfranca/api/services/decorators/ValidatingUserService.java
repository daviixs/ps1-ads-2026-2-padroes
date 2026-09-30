package br.edu.fatecfranca.api.services.decorators;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.entities.User;
import br.edu.fatecfranca.api.exceptions.InvalidCrudDataException;
import br.edu.fatecfranca.api.services.contracts.UserCrudService;

@Service
@Primary
public class ValidatingUserService implements UserCrudService {

    private final UserCrudService delegate;

    public ValidatingUserService(@Qualifier("userService") UserCrudService delegate) {
        this.delegate = delegate;
    }

    @Override
    public User create(User user) {
        validate(user);
        return delegate.create(user);
    }

    @Override
    public List<User> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public User update(User user) {
        validate(user);
        return delegate.update(user);
    }

    @Override
    public boolean existsById(Long id) {
        return delegate.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        delegate.deleteById(id);
    }

    private void validate(User user) {
        if (user == null || isBlank(user.getFullname()) || isBlank(user.getUsername())
                || isBlank(user.getEmail()) || isBlank(user.getPassword()) || user.getIsAdmin() == null) {
            throw new InvalidCrudDataException("Dados obrigatorios do usuario devem ser informados.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
