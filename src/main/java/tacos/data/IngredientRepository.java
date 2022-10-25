package tacos.data;

import tacos.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll(); // Iterable과 Iterator: https://girawhale.tistory.com/17
    Ingredient findById(String id);
    Ingredient save(Ingredient ingredient);
}
