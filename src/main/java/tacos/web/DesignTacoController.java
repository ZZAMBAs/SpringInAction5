package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.Ingredient;
import tacos.Taco;

import static tacos.Ingredient.Type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j // 로그 찍기 용.
@Controller
@RequestMapping("/design") // 이 클래스는 /design 에 대응한다.
public class DesignTacoController {

    @GetMapping
    public String showDesignForm(Model model){ // 모델을 자동으로 받아오고 해당 모델을 뷰에 전송한다.
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        ); // 임시 하드 코딩

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
    public String processDesign(Taco design){ // form 태그로 넘겨받은 것들이 Taco 객체로 바인딩된다. @ModelAttribute가 생략되어 있다.
        // https://heydoit.tistory.com/7,

        // 이 지점에서 타코 디자인(선택된 식자재 내역)을 저장한다.
        // 3장에서 다룰 예정.
        log.info("다음 디자인 처리중: " + design); // Slf4j 기능으로 로그를 남긴다.

        return "redirect:/orders/current"; // PRG 패턴: https://programmer93.tistory.com/76
    }
}
