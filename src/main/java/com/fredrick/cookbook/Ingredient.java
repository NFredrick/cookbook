package com.fredrick.cookbook;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Ingredient {

    private @Id @GeneratedValue Long id;
    private String name;
    private String foodGroup;

    public Ingredient(String name, String foodGroup) {
        this.name = name;
        this.foodGroup = foodGroup;
    }

    public Ingredient() {

    }
}
