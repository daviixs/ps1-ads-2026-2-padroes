package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;

import br.edu.fatecfranca.api.entities.Car;
import br.edu.fatecfranca.api.events.CrudOperation;
import br.edu.fatecfranca.api.events.CrudOperationEvent;
import br.edu.fatecfranca.api.repositories.CarRepository;
import br.edu.fatecfranca.api.services.contracts.CarCrudService;

@Service
public class CarService implements CarCrudService {

    private final CarRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public CarService(CarRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Car create(Car car) {
        Car savedCar = repository.save(car);
        publish(CrudOperation.CREATED, savedCar.getId());
        return savedCar;
    }

    @Override
    public List<Car> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Car> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Car update(Car car) {
        Car savedCar = repository.save(car);
        publish(CrudOperation.UPDATED, savedCar.getId());
        return savedCar;
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
        eventPublisher.publishEvent(new CrudOperationEvent("car", operation, resourceId));
    }
}
