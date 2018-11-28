package com.fredrick.cookbook;


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
public class RecipeController {

    private final RecipeRepository repository;
    private final RecipeResourceAssembler assembler;

    RecipeController(RecipeRepository repository,
                     RecipeResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/recipes")
    Resources<Resource<Recipe>> all() {

        List<Resource<Recipe>> recipes = repository.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(recipes,
                linkTo(methodOn(RecipeController.class).all()).withSelfRel());
    }

    @PostMapping("/recipes")
    ResponseEntity<?> newRecipe(@RequestBody Recipe newRecipe) throws URISyntaxException {

        Resource<Recipe> resource = assembler.toResource(repository.save(newRecipe));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping("recipes/{id}")
    Resource<Recipe> one(@PathVariable Long id) {

        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        return assembler.toResource(recipe);
    }
}
