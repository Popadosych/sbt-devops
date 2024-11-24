package ru.sber;

import java.util.*;


public class MyGarage implements Garage {
    private final Map<Integer, Owner> idToOwner = new HashMap<>();
    private final Map<Integer, Car> idToCar = new HashMap<>();
    private final Map<Integer, Set<Car>> ownerToCars = new HashMap<>();
    private final Map<String, Set<Car>> carsOfBrand = new HashMap<>();
    private final NavigableSet<Car> carsByMaxVelocity = new TreeSet<>(Comparator.comparingInt(Car::getMaxVelocity).reversed().thenComparing(Car::getCarId));
    private final NavigableSet<Car> carsByPower = new TreeSet<>(Comparator.comparingInt(Car::getPower).thenComparing(Car::getCarId));



    @Override
    public Collection<Owner> allCarsUniqueOwners() {
        return Collections.unmodifiableCollection(idToOwner.values());
    }

    @Override
    public Collection<Car> topThreeCarsByMaxVelocity() {
        List<Car> res = new ArrayList<>();
        Iterator<Car> iterator = carsByMaxVelocity.iterator();
        int count = 0;
        while (iterator.hasNext() && count != 3) {
            res.add(iterator.next());
            count++;
        }
        return res;
    }

    @Override
    public Collection<Car> allCarsOfBrand(String brand) {
        return carsOfBrand.getOrDefault(brand, Collections.emptySet());
    }

    @Override
    public Collection<Car> carsWithPowerMoreThan(int power) {
        Car dummyCar = new Car(-1, "", "", 0, power + 1, -1);
        return carsByPower.tailSet(dummyCar);
    }


    @Override
    public Collection<Car> allCarsOfOwner(Owner owner) {
        return ownerToCars.getOrDefault(owner.getOwnerId(), Collections.emptySet());
    }

    @Override
    public int meanOwnersAgeOfCarBrand(String brand) {
        Collection<Car> cars = allCarsOfBrand(brand);
        if (cars.isEmpty()) {
            return 0;
        }
        Set<Integer> ownersId = new HashSet<>();
        int sumAge = 0;
        for (Car car: cars) {
            Integer id = car.getOwnerid();
            if (!ownersId.contains(id)) {
                sumAge += idToOwner.get(id).getAge();
                ownersId.add(id);
            }
        }
        return sumAge / cars.size();
    }

    @Override
    public int meanCarNumberForEachOwner() {
        if (ownerToCars.isEmpty()) {
            return 0;
        }
        int cntCars = 0;
        for (Map.Entry<Integer, Set<Car>> entry : ownerToCars.entrySet()) {
            cntCars += entry.getValue().size();
        }

        return cntCars / ownerToCars.size();
    }

    @Override
    public Car removeCar(int carId) {
        Car removedCar = idToCar.remove(carId);
        if (removedCar == null) return null;
        idToCar.remove(carId);
        carsByMaxVelocity.remove(removedCar);
        if (carsOfBrand.get(removedCar.getBrand()).size() == 1) {
            carsOfBrand.remove(removedCar.getBrand());
        } else {
            carsOfBrand.get(removedCar.getBrand()).remove(removedCar);
        }

        if (ownerToCars.get(removedCar.getOwnerid()).size() == 1) {
            ownerToCars.remove(removedCar.getOwnerid());
        } else {
            ownerToCars.get(removedCar.getOwnerid()).remove(removedCar);
        }
        carsByPower.remove(removedCar);
        return removedCar;
    }

    @Override
    public void addNewCar(Car car, Owner owner) {
        if (car == null || owner == null) return;
        ownerToCars.computeIfAbsent(car.getOwnerid(), k -> (new HashSet<>())).add(car);
        carsOfBrand.computeIfAbsent(car.getBrand(), k -> (new HashSet<>())).add(car);
        idToOwner.put(car.getOwnerid(), owner);
        idToCar.put(car.getCarId(), car);
        carsByMaxVelocity.add(car);
        carsByPower.add(car);
    }
}