package tacos.web.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Taco;
import tacos.data.TacoRepository;

import java.util.List;
import java.util.Optional;

@RestController // @Controller + @ResponseBody. @RestController: https://mangkyu.tistory.com/49
@RequestMapping(path = "/design", produces = "application/json") // 요청의 Accept 헤더에 produces 속성 값이 포함된 요청만 처리함.
@CrossOrigin(origins = "*") // 서로 다른 도메인 간의 요청 허용. CORS: https://evan-moon.github.io/2020/05/21/about-cors/
public class DesignTacoController {
    private TacoRepository tacoRepo;

    // HATEOAS: https://joomn11.tistory.com/26, https://brunch.co.kr/@purpledev/29(스프링부트 2.2 이후 변경된 클래스 이름 포함)
    /*@Autowired
    EntityLinks entityLinks;*/

    public DesignTacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping("/recent")
    public CollectionModel<EntityModel<Taco>> recentTacos(){
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        // 3번째 파라미터에 Sort 함수를 넣으면 데이터를 정렬해주고 그 정렬 값들에서 페이징한다.
        List<Taco> tacos = tacoRepo.findAll(page).getContent();
        CollectionModel<EntityModel<Taco>> recentCollection = CollectionModel.wrap(tacos);

        recentCollection.add(
                //Link.of("https://localhost:8080/design/recent", "recents")); // URI 하드코딩은 절대하면 안된다.
                /*WebMvcLinkBuilder.linkTo(DesignTacoController.class) // DesignTacoController에 기본 경로가 /design인 링크를 요청.
                        .slash("recent") // "위에서 받은 주소/(값)"
                        .withRel("recents") // rel(관계 이름) 값 명시.*/
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DesignTacoController.class).recentTacos())
                        .withRel("recents")); // 하드코딩 최소화. methodOn으로 해당 클래스를 리플렉션으로 받고, 메서드를 호출해 경로를 모두 알 수 있음.

        return recentCollection;
    }

    @GetMapping("/{id}") // {} 내 변수를 플레이스홀더 변수라고 한다.
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id){
        // ResponseEntity는 HttpEntity를 상속받는 응답 데이터 정보를 가지고 있는 클래스이다.
        // ResponseEntity: https://a1010100z.tistory.com/106
        // HttpEntity의 Javadoc: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html
        // WebClient: https://gngsn.tistory.com/154
        Optional<Taco> optTaco = tacoRepo.findById(id);
        if (optTaco.isPresent())
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } // 단순히 Taco 타입으로 반환하지 않고 ResponseEntity 타입으로 반환하는 이유는 id로 타코가 검색되지 않았을 경우, null이 반환되는데 이때도 200 코드가 날아가기 때문임.

    @PostMapping(consumes = "application/json") // JSON 타입 요청만 처리한다.
    @ResponseStatus(HttpStatus.CREATED) // 응답 상태 코드 반환 애노테이션.
    public Taco postTaco(@RequestBody Taco taco){ // @RequestBody와 @ModelAttribute: https://tecoble.techcourse.co.kr/post/2021-05-11-requestbody-modelattribute/
        // HttpMessageConverter: https://itvillage.tistory.com/46, https://velog.io/@woo00oo/HTTP-%EB%A9%94%EC%8B%9C%EC%A7%80-%EC%BB%A8%EB%B2%84%ED%84%B0
        return tacoRepo.save(taco);
    }

    // 그 외, 부분 변경은 @PatchMapping, 전체 변경은 @PutMapping, 삭제는 @DeleteMapping 등의 방식이 존재함.
}
