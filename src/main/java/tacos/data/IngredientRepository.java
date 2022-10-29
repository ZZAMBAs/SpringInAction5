package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> { // Spring Data에 정의된 CRUD Repository용 인터페이스. <T, ID> 형식이며 T는 엔티티 타입, ID는 엔티티 Id 타입.
    // 해당 인터페이스 구현체는 Spring Data Jpa가 자동 구현한다.
}
