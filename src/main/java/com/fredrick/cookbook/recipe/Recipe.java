package com.fredrick.cookbook.recipe;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fredrick.cookbook.ingredient.Ingredient;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "recipes")
public class Recipe {

    private @Id @GeneratedValue Long id;
    private String name;
    @ManyToMany(
            cascade = CascadeType.ALL
    )
    private List<Ingredient> ingredients;

    public Recipe(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public Recipe() {

    }
}
