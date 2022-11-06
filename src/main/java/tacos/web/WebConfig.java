package tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 뷰 컨트롤러의 역할을 수행하는 구성 클래스.
@Configuration
public class WebConfig implements WebMvcConfigurer { // https://mangkyu.tistory.com/176
    @Override
    public void addViewControllers(ViewControllerRegistry registry) { // https://1-7171771.tistory.com/81
        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/ViewControllerRegistry.html
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login");
    }
}
