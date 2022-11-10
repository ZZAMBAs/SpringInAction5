package tacos.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Taco;
import tacos.data.TacoRepository;

import java.util.Optional;

@RestController // @Controller + @ResponseBody. @RestController: https://mangkyu.tistory.com/49
@RequestMapping(path = "/design", produces = "application/json") // 요청의 Accept 헤더에 produces 속성 값이 포함된 요청만 처리함.
@CrossOrigin(origins = "*") // 서로 다른 도메인 간의 요청 허용. CORS: https://evan-moon.github.io/2020/05/21/about-cors/
public class DesignTacoController {
    private TacoRepository tacoRepo;

    // HATEOAS: https://joomn11.tistory.com/26, https://brunch.co.kr/@purpledev/29(스프링부트 2.2 이후 변경된 클래스 이름 포함)
    @Autowired
    EntityLinks entityLinks;

    public DesignTacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos(){
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        // 3번째 파라미터에 Sort 함수를 넣으면 데이터를 정렬해주고 그 정렬 값들에서 페이징한다.
        return tacoRepo.findAll(page).getContent();
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
}
