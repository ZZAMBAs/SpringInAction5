package tacos.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Component
@Data
@ConfigurationProperties(prefix="taco.orders")
public class OrderProps { // 구성 속성 홀더 클래스. https://jithub.tistory.com/349
    @Min(value = 5, message = "5와 25 사이 값이어야 합니다.")
    @Max(value = 25, message = "5와 25 사이 값이어야 합니다.")
    private int pageSize = 20;
}
