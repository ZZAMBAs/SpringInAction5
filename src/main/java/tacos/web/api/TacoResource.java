package tacos.web.api;

import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import tacos.Taco;

@Getter
public class TacoResource extends RepresentationModel<TacoResource> { // 링크를 추가로 가질 수 있는 Taco 클래스
    // id는 따로 노출시킬 필요가 없어서 필드에 추가하지 않는다.
    // 최초에 Taco 엔티티 클래스 생성 시에 그냥 RepresentationModel를 상속해 하나로 만들 수도 있다. 각각의 장단점이 있다.
    private static final IngredientResourceAssembler ingredientAssembler = new IngredientResourceAssembler();
    private final String name;
    private final String createdAt;
    private final CollectionModel<IngredientResource> ingredients;
    
    public TacoResource(Taco taco){
        this.name = taco.getName();
        this.createdAt = taco.getName();
        this.ingredients = ingredientAssembler.toCollectionModel(taco.getIngredients());
    }
}
