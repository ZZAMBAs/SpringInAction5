package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

import java.util.Optional;

// https://sujl95.tistory.com/8
@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private IngredientRepository ingredientRepo;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    // id로 특정 식자재 데이터를 읽고 그것을 Ingredient 객체로 변환하기 위해 사용함.
    @Override
    public Ingredient convert(String id) {
        // Optional은 null이 올 수 있는 값을 감싼 Wrapper 클래스이다. https://mangkyu.tistory.com/70
        Optional<Ingredient> optionalIngredient = ingredientRepo.findById(id);
        return optionalIngredient.isPresent() ? optionalIngredient.get() : null;
    }
}
