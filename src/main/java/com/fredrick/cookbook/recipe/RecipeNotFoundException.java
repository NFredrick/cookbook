package com.fredrick.cookbook.recipe;

public class RecipeNotFoundException extends RuntimeException{

    public RecipeNotFoundException(Long id) {
        super("could not find recipe " + id);
    }
}
