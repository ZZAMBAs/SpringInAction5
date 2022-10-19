package tacos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HomeController.class) // HomeController의 웹 페이지 테스트임을 명시.
// @WebMvcTest는 웹 컨트롤러 테스트 시에 사용한다. @SpringBootTest를 올리기엔 너무 무겁기 때문. 웹 컨트롤러 등과 관련된 빈만 올린다.
// 스프링 부트에서 제공하는 애노테이션이며 스프링 MVC 형태로 테스트가 실행되도록 한다. 그리고 스프링 MVC 테스트를 위한 스프링 지원을 설정해준다.
// Javadoc: https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/web/servlet/WebMvcTest.html
class HomeControllerTest {
    @Autowired // @WebMvcTest에 의해 MockMvc가 빈에 등록된다.
    // @SpringBootTest는 MockMvc를 따로 빈으로 등록시키지 않기 때문에, @AutoConfigureMockMvc를 추가로 작성해야 한다.
    MockMvc mockMvc;
    // MVC 목 객체. 이것으로 스프링 MVC 환경을 최소한으로 빠르게 구축한다.
    // https://velog.io/@jkijki12/Spring-MockMvc

    @Test
    public void testHomePage() throws Exception {
        // 아래는 우리가 테스트로 예상하는 추정치이다.
        mockMvc.perform(get("/")) // get 요청을 수행
                .andExpect(status().isOk()) // status가 ok(200)인지 확인
                .andExpect(view().name("home")) // 뷰 이름이 home 인지 확인
                .andExpect(content().string(containsString("환영합니다!"))); // 컨텐츠에 "환영합니다!" 가 포함되었는지 확인.
    }
}