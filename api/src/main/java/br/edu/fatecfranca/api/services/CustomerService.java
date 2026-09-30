package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;

import br.edu.fatecfranca.api.entities.Customer;
import br.edu.fatecfranca.api.events.CrudOperation;
import br.edu.fatecfranca.api.events.CrudOperationEvent;
import br.edu.fatecfranca.api.repositories.CustomerRepository;
import br.edu.fatecfranca.api.services.contracts.CustomerCrudService;

@Service
public class CustomerService implements CustomerCrudService {

  private final CustomerRepository repository;
  private final ApplicationEventPublisher eventPublisher;

  public CustomerService(CustomerRepository repository, ApplicationEventPublisher eventPublisher) {
    this.repository = repository;
    this.eventPublisher = eventPublisher;
  }

  @Override
  public Customer create(Customer customer) {
    Customer savedCustomer = repository.save(customer);
    publish(CrudOperation.CREATED, savedCustomer.getId());
    return savedCustomer;
  }

  @Override
  public List<Customer> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Customer> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Customer update(Customer customer) {
    Customer savedCustomer = repository.save(customer);
    publish(CrudOperation.UPDATED, savedCustomer.getId());
    return savedCustomer;
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
    eventPublisher.publishEvent(new CrudOperationEvent("customer", operation, resourceId));
  }
}
