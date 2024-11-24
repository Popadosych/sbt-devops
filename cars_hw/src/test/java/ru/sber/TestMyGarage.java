package ru.sber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class TestMyGarage {

    private Garage garage;
    private Owner owner1;
    private Owner owner2;
    private Car car1;
    private Car car2;
    private Car car3;
    private Car car4;

    @BeforeEach
    public void setUp() {
        garage = new MyGarage();

        owner1 = new Owner("Mikhail", "Sukhov", 30, 1);
        owner2 = new Owner("Kirill", "Afentev", 40, 2);

        car1 = new Car(1, "Tesla", "Model S", 250, 670, 1);
        car2 = new Car(2, "BMW", "M5", 305, 625, 1);
        car3 = new Car(3, "Audi", "RS7", 280, 591, 2);
        car4 = new Car(4, "Tesla", "Model 3", 230, 480, 2);

        garage.addNewCar(car1, owner1);
        garage.addNewCar(car2, owner1);
        garage.addNewCar(car3, owner2);
        garage.addNewCar(car4, owner2);
    }

    @Test
    public void testAllCarsUniqueOwners() {
        Collection<Owner> uniqueOwners = garage.allCarsUniqueOwners();
        assertEquals(2, uniqueOwners.size());
        assertTrue(uniqueOwners.contains(owner1));
        assertTrue(uniqueOwners.contains(owner2));
    }

    @Test
    public void testTopThreeCarsByMaxVelocity() {
        Collection<Car> topCars = garage.topThreeCarsByMaxVelocity();
        List<Car> expectedTopCars = Arrays.asList(car2, car3, car1);
        assertEquals(expectedTopCars, new ArrayList<>(topCars));
    }

    @Test
    public void testAllCarsOfBrand() {
        Collection<Car> teslaCars = garage.allCarsOfBrand("Tesla");
        assertEquals(2, teslaCars.size());
        assertTrue(teslaCars.contains(car1));
        assertTrue(teslaCars.contains(car4));
    }

    @Test
    public void testCarsWithPowerMoreThan() {
        Collection<Car> powerfulCars = garage.carsWithPowerMoreThan(600);
        assertEquals(2, powerfulCars.size());
        assertTrue(powerfulCars.contains(car1));
        assertTrue(powerfulCars.contains(car2));
    }

    @Test
    public void testAllCarsOfOwner() {
        Collection<Car> mikhailCars = garage.allCarsOfOwner(owner1);
        assertEquals(2, mikhailCars.size());
        assertTrue(mikhailCars.contains(car1));
        assertTrue(mikhailCars.contains(car2));
    }

    @Test
    public void testMeanOwnersAgeOfCarBrand() {
        int teslaOwnersMeanAge = garage.meanOwnersAgeOfCarBrand("Tesla");
        assertEquals(35, teslaOwnersMeanAge);
    }

    @Test
    public void testMeanCarNumberForEachOwner() {
        int meanCarNumber = garage.meanCarNumberForEachOwner();
        assertEquals(2, meanCarNumber);
    }

    @Test
    public void testRemoveCar() {
        Car removedCar = garage.removeCar(car2.getCarId());
        assertEquals(car2, removedCar);

        Collection<Car> mikhailCars = garage.allCarsOfOwner(owner1);
        assertEquals(1, mikhailCars.size());
        assertFalse(mikhailCars.contains(car2));
    }

    @Test
    public void testAddNewCar() {
        Car newCar = new Car(5, "Mercedes", "AMG GT", 320, 700, 1);
        garage.addNewCar(newCar, owner1);

        Collection<Car> mikhailCars = garage.allCarsOfOwner(owner1);
        assertEquals(3, mikhailCars.size());
        assertTrue(mikhailCars.contains(newCar));
    }
}