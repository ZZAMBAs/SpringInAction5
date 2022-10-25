package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.Ingredient;
import tacos.Taco;
import tacos.data.IngredientRepository;

import javax.validation.Valid;

import static tacos.Ingredient.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j // 로그 찍기 용.
@Controller
@RequestMapping("/design") // 이 클래스는 /design 에 대응한다.
public class DesignTacoController {
    private IngredientRepository ingredientRepo;

    // @Autowired // 생성자가 1개일 시 생략 가능.
    public DesignTacoController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @GetMapping
    public String showDesignForm(Model model){ // 모델을 자동으로 받아오고 해당 모델을 뷰에 전송한다.
        List<Ingredient> ingredients = new ArrayList<>(); // List 인터페이스 메서드만 사용하기에 List로 받는다.
        ingredientRepo.findAll().forEach(ingredient -> ingredients.add(ingredient));

        Type[] types = Type.values(); // 열거형 타입의 values()는 열거형 내 모든 값들을 배열로 리턴한다.
        for (Type type : types)
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        model.addAttribute("taco", new Taco());

        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors){ // 유효성 검사를 통과했을 때만 바인딩된다. Errors는 에러 시 넘겨받는 객체.
        // https://heydoit.tistory.com/7,

        if (errors.hasErrors())
            return "design";

        // 이 지점에서 타코 디자인(선택된 식자재 내역)을 저장한다.
        // 3장에서 다룰 예정.
        log.info("다음 디자인 처리중: " + design); // Slf4j 기능으로 로그를 남긴다.

        return "redirect:/orders/current"; // PRG 패턴: https://programmer93.tistory.com/76
    }
}
