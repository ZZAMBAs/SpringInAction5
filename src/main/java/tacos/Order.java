package tacos;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class Order {
    private Long id;
    private Date placedAt;

    @NotBlank(message = "필수 항목입니다.") // @NotNull, @NotEmpty, @NotBlank의 차이: https://sanghye.tistory.com/36
    private String deliveryName;

    @NotBlank(message = "필수 항목입니다.")
    private String deliveryStreet;

    @NotBlank(message = "필수 항목입니다.")
    private String deliveryCity;

    @NotBlank(message = "필수 항목입니다.")
    private String deliveryState;

    @NotBlank(message = "필수 항목입니다.")
    private String deliveryZip;

    @CreditCardNumber(message = "유효하지 않은 카드번호입니다.")
    // Luhn 알고리즘 검사(https://ko.wikipedia.org/wiki/%EB%A3%AC_%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98)에 합격한 번호만 입력 가능하도록 한다.
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "MM/YY 형식이어야 합니다.") // 정규표현식: https://hamait.tistory.com/342
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "유효하지 않은 CVV") // integer는 최대 정수 자리 수, fraction은 최대 소수 자리 수를 의미
    private String ccCVV;
}
