package br.edu.fatecfranca.api.services.contracts;

import java.util.List;
import java.util.Optional;

import br.edu.fatecfranca.api.entities.Car;

public interface CarCrudService {

    Car create(Car car);

    List<Car> findAll();

    Optional<Car> findById(Long id);

    Car update(Car car);

    boolean existsById(Long id);

    void deleteById(Long id);
}
