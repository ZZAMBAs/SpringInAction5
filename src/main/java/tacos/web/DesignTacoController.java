package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

import javax.validation.Valid;

import static tacos.Ingredient.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j // 로그 찍기 용.
@Controller
@RequestMapping("/design") // 이 클래스는 /design 에 대응한다.
@SessionAttributes("order") // https://mindols.tistory.com/106
public class DesignTacoController {
    private IngredientRepository ingredientRepo;
    private TacoRepository tacoRepo;

    // @Autowired // 생성자가 1개일 시 생략 가능.
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
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

    // 메서드 레벨의 @ModelAttribute는 해당 모델을 생성해준다.
    // name 속성이 있으면 해당 name이 모델 키값으로, return 값이 값으로 들어간다. name 속성이 없으면 임의로 키값을 생성한다.
    // @SessionAttributes로 지정된 모델은 세션에서 주입한다. 위 @SessionAttribute 참조
    @ModelAttribute(name = "order") // https://ncucu.me/52
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order){
        // https://heydoit.tistory.com/7,

        if (errors.hasErrors())
            return "design";

        Taco saved = tacoRepo.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current"; // PRG 패턴: https://programmer93.tistory.com/76
    }
}
