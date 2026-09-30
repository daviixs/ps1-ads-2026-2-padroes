package br.edu.fatecfranca.api.factories;

import org.springframework.stereotype.Component;

import br.edu.fatecfranca.api.entities.Customer;

@Component
public class CustomerFactory extends EntityFactory<Customer> {

    @Override
    protected Customer newEntity() {
        return new Customer();
    }

    public Customer create(Customer source) {
        Customer customer = newEntity();
        customer.setName(source.getName());
        customer.setIdentDocument(source.getIdentDocument());
        customer.setBirthDate(source.getBirthDate());
        customer.setStreetName(source.getStreetName());
        customer.setHouseNumber(source.getHouseNumber());
        customer.setComplements(source.getComplements());
        customer.setDistrict(source.getDistrict());
        customer.setMunicipality(source.getMunicipality());
        customer.setState(source.getState());
        customer.setPhone(source.getPhone());
        customer.setEmail(source.getEmail());
        return customer;
    }
}
