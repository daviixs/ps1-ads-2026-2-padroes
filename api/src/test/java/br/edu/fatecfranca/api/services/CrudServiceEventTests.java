package br.edu.fatecfranca.api.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import br.edu.fatecfranca.api.entities.Car;
import br.edu.fatecfranca.api.entities.Customer;
import br.edu.fatecfranca.api.events.CrudOperation;
import br.edu.fatecfranca.api.events.CrudOperationEvent;
import br.edu.fatecfranca.api.repositories.CarRepository;
import br.edu.fatecfranca.api.repositories.CustomerRepository;

class CrudServiceEventTests {

    @Test
    void carServicePublishesCreatedEvent() {
        CarRepository repository = mock(CarRepository.class);
        ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);
        Car car = new Car();
        car.setId(1L);
        when(repository.save(any(Car.class))).thenReturn(car);

        new CarService(repository, publisher).create(car);

        verify(publisher).publishEvent(new CrudOperationEvent("car", CrudOperation.CREATED, 1L));
    }

    @Test
    void customerServicePublishesDeletedEvent() {
        CustomerRepository repository = mock(CustomerRepository.class);
        ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);

        new CustomerService(repository, publisher).deleteById(1L);

        verify(publisher).publishEvent(new CrudOperationEvent("customer", CrudOperation.DELETED, 1L));
    }
}
