package ru.sber;

import java.util.Objects;

public class Car {
    private final int carId;
    private final String brand;
    private final String modelName;
    private final int maxVelocity;
    private final int power;
    private final int ownerId;

    public Car(int carId, String brand, String modelName, int maxVelocity, int power, int ownerId) {
        this.carId = carId;
        this.brand = brand;
        this.modelName = modelName;
        this.maxVelocity = maxVelocity;
        this.power = power;
        this.ownerId = ownerId;
    }

    int getMaxVelocity() {
        return maxVelocity;
    }

    int getPower() {
        return power;
    }

    int getOwnerid() {
        return ownerId;
    }

    int getCarId() {
        return carId;
    }

    String getBrand() {
        return brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carId == car.carId && maxVelocity == car.maxVelocity && power == car.power && ownerId == car.ownerId && Objects.equals(brand, car.brand) && Objects.equals(modelName, car.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, brand, modelName, maxVelocity, power, ownerId);
    }
}