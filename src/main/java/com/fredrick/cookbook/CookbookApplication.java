package com.fredrick.cookbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.ArrayList;

@SpringBootApplication
public class CookbookApplication implements CommandLineRunner {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

	public static void main(String[] args) {
		SpringApplication.run(CookbookApplication.class, args);
	}

	@Override
    @Transactional
    public void run(String... strings) throws Exception {
        Ingredient pb = new Ingredient("peanut butter");
        Ingredient jelly = new Ingredient("jelly");
        Ingredient bread = new Ingredient("bread");

        recipeRepository.saveAll(new ArrayList<Recipe>(){{
            add(new Recipe("pb&j sandwich", new ArrayList<Ingredient>(){{
                add(pb);
                add(jelly);
                add(bread);
            }}));
            add(new Recipe("pb sandwich", new ArrayList<Ingredient>(){{
                add(pb);
                add(bread);
            }}));
            add(new Recipe("toast", new ArrayList<Ingredient>(){{
                add(jelly);
                add(bread);
            }}));
        }});
    }


}
