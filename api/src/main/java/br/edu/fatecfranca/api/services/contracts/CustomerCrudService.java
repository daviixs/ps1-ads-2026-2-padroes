package br.edu.fatecfranca.api.services.contracts;

import java.util.List;
import java.util.Optional;

import br.edu.fatecfranca.api.entities.Customer;

public interface CustomerCrudService {

    Customer create(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(Long id);

    Customer update(Customer customer);

    boolean existsById(Long id);

    void deleteById(Long id);
}
