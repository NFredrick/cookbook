package com.fredrick.cookbook;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data // getters, setters, toString, hash
@Entity // prepare object for JPA based storage
@Table(name = "ingredients")
public class Ingredient {

    private @Id @GeneratedValue Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "ingredients")
    @JsonBackReference
    private List<Recipe> recipes;

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient() {

    }
}
