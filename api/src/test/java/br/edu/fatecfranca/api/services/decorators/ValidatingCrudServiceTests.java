package br.edu.fatecfranca.api.services.decorators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import br.edu.fatecfranca.api.entities.Car;
import br.edu.fatecfranca.api.entities.Customer;
import br.edu.fatecfranca.api.exceptions.InvalidCrudDataException;
import br.edu.fatecfranca.api.services.contracts.CarCrudService;
import br.edu.fatecfranca.api.services.contracts.CustomerCrudService;

class ValidatingCrudServiceTests {

    @Test
    void delegatesValidCarCreation() {
        CarCrudService delegate = mock(CarCrudService.class);
        Car car = car();
        when(delegate.create(any(Car.class))).thenReturn(car);

        Car saved = new ValidatingCarService(delegate).create(car);

        assertEquals(car, saved);
        verify(delegate).create(car);
    }

    @Test
    void rejectsCarWithBlankPlate() {
        Car invalid = car();
        invalid.setPlates(" ");

        assertThrows(InvalidCrudDataException.class,
                () -> new ValidatingCarService(mock(CarCrudService.class)).create(invalid));
    }

    @Test
    void delegatesValidCustomerCreation() {
        CustomerCrudService delegate = mock(CustomerCrudService.class);
        Customer customer = customer();
        when(delegate.create(any(Customer.class))).thenReturn(customer);

        Customer saved = new ValidatingCustomerService(delegate).create(customer);

        assertEquals(customer, saved);
        verify(delegate).create(customer);
    }

    @Test
    void rejectsCustomerWithoutEmail() {
        Customer invalid = customer();
        invalid.setEmail(null);

        assertThrows(InvalidCrudDataException.class,
                () -> new ValidatingCustomerService(mock(CustomerCrudService.class)).create(invalid));
    }

    private Car car() {
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        car.setColor("Prata");
        car.setYearManufacture(2022);
        car.setImported(false);
        car.setPlates("ABC1D23");
        return car;
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
}
