package com.fredrick.cookbook.ingredient;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class IngredientController {

    private final IngredientRepository repository;
    private final IngredientResourceAssembler assembler;

    public IngredientController(IngredientRepository repository,
                                IngredientResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/ingredients")
    Resources<Resource<Ingredient>> all() {
        List<Resource<Ingredient>> ingredients = repository.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(ingredients,
                linkTo(methodOn(IngredientController.class).all()).withSelfRel());

    }

    @PostMapping("/ingredients")
    ResponseEntity<?> newIngredient(@RequestBody Ingredient newIngredient) throws URISyntaxException {

        Resource<Ingredient> resource = assembler.toResource(repository.save(newIngredient));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping("/ingredients/{id}")
    Resource<Ingredient> one(@PathVariable Long id) {

        Ingredient ingredient = repository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException(id));

        return assembler.toResource(ingredient);
    }

    @PutMapping("/ingredients/{id}")
    ResponseEntity<?> replaceIngredient(@RequestBody Ingredient newIngredient, @PathVariable Long id) throws URISyntaxException{

        Ingredient updatedIngredient = repository.findById(id)
                .map(ingredient -> {
                    ingredient.setName(newIngredient.getName());
                    ingredient.setAmount(newIngredient.getAmount());
                    ingredient.setUnits(newIngredient.getUnits());
                    return repository.save(ingredient);
                })
                .orElseGet(() -> {
                    newIngredient.setId(id);
                    return repository.save(newIngredient);
                });

        Resource<Ingredient> resource = assembler.toResource(updatedIngredient);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/ingredients/{id}")
    ResponseEntity<?> deleteIngredient(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
