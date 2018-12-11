package com.fredrick.cookbook.ingredient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fredrick.cookbook.recipe.Recipe;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data // getters, setters, toString, hash
@Entity // prepare object for JPA based storage
@Table(name = "ingredients")
public class Ingredient {

    private @Id @GeneratedValue Long id;
    private String name;
    private double amount;
    private String units;

    public Ingredient(String name, double amount, String units) {
        this.name = name;
        this.amount = amount;
        this.units = units;
    }

    public Ingredient() {

    }
}
