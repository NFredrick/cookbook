package com.fredrick.cookbook;

public class RecipeNotFoundException extends RuntimeException{

    public RecipeNotFoundException(Long id) {
        super("could not find recipe " + id);
    }
}
