package com.fredrick.cookbook;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IngredientController {

    private final IngredientRepository repository;

    public IngredientController(IngredientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/ingredients")
    List<Ingredient> all() {
        return repository.findAll();
    }

    @PostMapping("/ingredients")
    Ingredient newIngredient(@RequestBody Ingredient newIngredient) {
        return repository.save(newIngredient);
    }

    @GetMapping("/ingredients/{id}")
    Ingredient one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException(id));
    }

    @PutMapping("/ingredients/{id}")
    Ingredient replaceIngredient(@RequestBody Ingredient newIngredient, @PathVariable Long id) {

        return repository.findById(id)
                .map(ingredient -> {
                    ingredient.setName(newIngredient.getName());
                    ingredient.setFoodGroup(newIngredient.getFoodGroup());
                    return repository.save(ingredient);
                })
                .orElseGet(() -> {
                    newIngredient.setId(id);
                    return repository.save(newIngredient);
                });
    }

    @DeleteMapping("/ingredients/{id}")
    void deleteIngredient(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
