package br.edu.fatecfranca.api.services.decorators;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.entities.Customer;
import br.edu.fatecfranca.api.exceptions.InvalidCrudDataException;
import br.edu.fatecfranca.api.services.contracts.CustomerCrudService;

@Service
@Primary
public class ValidatingCustomerService implements CustomerCrudService {

    private final CustomerCrudService delegate;

    public ValidatingCustomerService(@Qualifier("customerService") CustomerCrudService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Customer create(Customer customer) {
        validate(customer);
        return delegate.create(customer);
    }

    @Override
    public List<Customer> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public Customer update(Customer customer) {
        validate(customer);
        return delegate.update(customer);
    }

    @Override
    public boolean existsById(Long id) {
        return delegate.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        delegate.deleteById(id);
    }

    private void validate(Customer customer) {
        if (customer == null || isBlank(customer.getName()) || isBlank(customer.getIdentDocument())
                || isBlank(customer.getStreetName()) || isBlank(customer.getHouseNumber())
                || isBlank(customer.getDistrict()) || isBlank(customer.getMunicipality())
                || isBlank(customer.getState()) || isBlank(customer.getPhone()) || isBlank(customer.getEmail())) {
            throw new InvalidCrudDataException("Dados obrigatorios do cliente devem ser informados.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
