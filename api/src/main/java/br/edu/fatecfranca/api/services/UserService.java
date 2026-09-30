package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.entities.User;
import br.edu.fatecfranca.api.events.CrudOperation;
import br.edu.fatecfranca.api.events.CrudOperationEvent;
import br.edu.fatecfranca.api.repositories.UserRepository;
import br.edu.fatecfranca.api.services.contracts.UserCrudService;

@Service
public class UserService implements UserCrudService {

    private final UserRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public UserService(UserRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public User create(User user) {
        User savedUser = repository.save(user);
        publish(CrudOperation.CREATED, savedUser.getId());
        return savedUser;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public User update(User user) {
        User savedUser = repository.save(user);
        publish(CrudOperation.UPDATED, savedUser.getId());
        return savedUser;
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
        publish(CrudOperation.DELETED, id);
    }

    private void publish(CrudOperation operation, Long resourceId) {
        eventPublisher.publishEvent(new CrudOperationEvent("user", operation, resourceId));
    }
}
