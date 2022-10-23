package tacos;

import lombok.Data;

// 타코 재료 도메인
@Data // Object가 구현해야 하는 것들을 구현해줌. 단, 생성자는 final 필드만 구현해준다. 내부 참조.
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;

    public enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
