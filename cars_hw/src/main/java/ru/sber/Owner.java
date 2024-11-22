package ru.sber;

import java.util.Objects;

public class Owner {
    private final String name;
    private final String lastName;
    private final int age;
    private final int ownerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return age == owner.age && Objects.equals(name, owner.name) && Objects.equals(lastName, owner.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, age);
    }

    public Owner(String name, String lastName, int age, int ownerId) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.ownerId = ownerId;
    }

    int getAge() {
        return age;
    }

    int getOwnerId() {
        return ownerId;
    }
}