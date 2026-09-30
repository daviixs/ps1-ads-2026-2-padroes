package br.edu.fatecfranca.api.services.decorators;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.entities.Car;
import br.edu.fatecfranca.api.exceptions.InvalidCrudDataException;
import br.edu.fatecfranca.api.services.contracts.CarCrudService;

@Service
@Primary
public class ValidatingCarService implements CarCrudService {

    private final CarCrudService delegate;

    public ValidatingCarService(@Qualifier("carService") CarCrudService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Car create(Car car) {
        validate(car);
        return delegate.create(car);
    }

    @Override
    public List<Car> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<Car> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public Car update(Car car) {
        validate(car);
        return delegate.update(car);
    }

    @Override
    public boolean existsById(Long id) {
        return delegate.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        delegate.deleteById(id);
    }

    private void validate(Car car) {
        if (car == null || isBlank(car.getBrand()) || isBlank(car.getModel()) || isBlank(car.getColor())
                || car.getYearManufacture() == null || car.getImported() == null || isBlank(car.getPlates())) {
            throw new InvalidCrudDataException("Dados obrigatorios do veiculo devem ser informados.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
