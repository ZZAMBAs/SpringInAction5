package tacos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

// 타코 재료 도메인
@Data // JPA 상에서 권장하지 않는 애노테이션이나, 교재를 그대로 따라간다.
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) // 인자 없는 기본 생성자를 생성. JPA에는 이런 기본 생성자가 필수. force 속성은 final 필드들을 전부 0/null/false 로 초기화
// https://velog.io/@yyy96/JPA-%EA%B8%B0%EB%B3%B8%EC%83%9D%EC%84%B1%EC%9E%90, https://1-7171771.tistory.com/123
@Entity
public class Ingredient { // hibernate가 유연하게 public, protected 기본 생성자가 없어도 돌아가도록 해준다. 예외적 상황에 대비해 해당 기본 생성자를 생성해주는 것이 좋지만 이 책에는 없어서 그대로 간다.
    @Id // PK 속성 정의
    private final String id;
    private final String name;
    private final Type type;

    public enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
