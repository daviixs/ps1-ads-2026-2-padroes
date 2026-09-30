package br.edu.fatecfranca.api.factories;

import org.springframework.stereotype.Component;

import br.edu.fatecfranca.api.entities.Car;

@Component
public class CarFactory extends EntityFactory<Car> {

    @Override
    protected Car newEntity() {
        return new Car();
    }

    public Car create(Car source) {
        Car car = newEntity();
        car.setBrand(source.getBrand());
        car.setModel(source.getModel());
        car.setColor(source.getColor());
        car.setYearManufacture(source.getYearManufacture());
        car.setImported(source.getImported());
        car.setPlates(source.getPlates());
        car.setSellingDate(source.getSellingDate());
        car.setSellingPrice(source.getSellingPrice());
        car.setCustomer(source.getCustomer());
        return car;
    }
}
