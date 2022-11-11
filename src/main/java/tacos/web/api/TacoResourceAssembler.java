package tacos.web.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import tacos.Taco;

public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco, TacoResource> {


    public TacoResourceAssembler() {
        super(DesignTacoController.class, TacoResource.class);
    }

    @Override
    protected TacoResource instantiateModel(Taco taco) { // 전달받은 Taco 객체로 TacoResource 생성.
        return new TacoResource(taco);
    }

    @Override
    public TacoResource toModel(Taco taco) { // 전달받은 Taco 객체로 TacoResource를 생성함과 동시에 id 속성값으로 생성되는 self 링크가 자동 지정됨.
        return createModelWithId(taco.getId(), taco);
    }
}
