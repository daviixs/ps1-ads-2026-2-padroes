package br.edu.fatecfranca.api.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.edu.fatecfranca.api.entities.Car;
import br.edu.fatecfranca.api.entities.Customer;
import br.edu.fatecfranca.api.entities.User;

class EntityFactoryTests {

    @Test
    void carFactoryCreatesNewCarWithoutClientProvidedId() {
        Car source = new Car();
        source.setId(99L);
        source.setBrand("Toyota");
        source.setModel("Corolla");
        source.setColor("Prata");
        source.setYearManufacture(2022);
        source.setImported(false);
        source.setPlates("ABC1D23");

        Car created = new CarFactory().create(source);

        assertNotSame(source, created);
        assertNull(created.getId());
        assertEquals("Toyota", created.getBrand());
        assertEquals("ABC1D23", created.getPlates());
    }

    @Test
    void customerFactoryCreatesNewCustomerWithoutClientProvidedId() {
        Customer source = customer();
        source.setId(99L);

        Customer created = new CustomerFactory().create(source);

        assertNotSame(source, created);
        assertNull(created.getId());
        assertEquals("Maria", created.getName());
        assertEquals("maria@example.com", created.getEmail());
    }

    @Test
    void userFactoryCreatesNewUserWithoutClientProvidedId() {
        User source = user();
        source.setId(99L);

        User created = new UserFactory().create(source);

        assertNotSame(source, created);
        assertNull(created.getId());
        assertEquals("davi", created.getUsername());
        assertEquals(false, created.getIsAdmin());
    }

    private Customer customer() {
        Customer customer = new Customer();
        customer.setName("Maria");
        customer.setIdentDocument("12345678900");
        customer.setStreetName("Rua Um");
        customer.setHouseNumber("10");
        customer.setDistrict("Centro");
        customer.setMunicipality("Franca");
        customer.setState("SP");
        customer.setPhone("16999999999");
        customer.setEmail("maria@example.com");
        return customer;
    }

    private User user() {
        User user = new User();
        user.setFullname("Davi Xavier");
        user.setUsername("davi");
        user.setEmail("davi@example.com");
        user.setPassword("senha-segura");
        user.setIsAdmin(false);
        return user;
    }
}
