package tacos;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

// 타코 도메인
@Data
@Entity
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동 생성 키
    private Long id;
    private Date createAt;

    @NotNull // null 값 방지
    @Size(min = 5, message = "이름은 5자 이상이어야 합니다!")
    private String name;

    @Size(min = 1, message = "최소 1개 이상의 재료를 선택하셔야 합니다!")
    @ManyToMany(targetEntity = Ingredient.class) // https://dev-coco.tistory.com/106
    private List<Ingredient> ingredients;

    @PrePersist // JPA persist 전 실행되는 메서드.
    void createdAt(){
        this.createAt = new Date();
    }
}
