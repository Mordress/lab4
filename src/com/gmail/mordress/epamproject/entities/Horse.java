package com.gmail.mordress.epamproject.entities;

/**
 * Class-Entity, describes horse.
 * @author Alexey Kardychko
 * @version 1.0
 */
public class Horse extends Entity {

    private String name;

    private Breed breed;

    private Integer weight;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return new StringBuilder("Horse:{")
                .append("HorseId = ").append(this.getId())
                .append(", horseName = ").append(name)
                .append(", Breed = ").append(breed)
                .append(", weight = ").append(weight)
                .append(", age = ").append(age)
                .append("}")
                .toString();
    }
}
