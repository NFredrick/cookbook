package com.fredrick.cookbook.ingredient;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(Long id) {
        super("Could not find ingredients " + id);
    }
}
