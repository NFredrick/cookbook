package com.fredrick.cookbook;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "recipes")
public class Recipe {

    private @Id @GeneratedValue Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST, CascadeType.MERGE
            })
    @JoinTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"))
    @JsonManagedReference
    private List<Ingredient> ingredients;

    public Recipe(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public Recipe() {

    }
}
