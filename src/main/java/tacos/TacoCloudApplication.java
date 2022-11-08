package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import tacos.data.IngredientRepository;

import static tacos.Ingredient.Type;

// 전체 예제 소스 코드: https://github.com/Jpub/SpringInAction5

// 부트스트랩 클래스
@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    // 스프링 부트 서버 구동 시점에 실행되는 메서드. https://coding-start.tistory.com/118
    // 미리 아래 데이터를 Repository에 저장하도록 함.
    @Bean
    //@Profile("!prod") // 특정 프로파일에서만 빈을 적용하고 싶을 때 사용. https://dbjh.tistory.com/31
    // 위 예시는 prod 외의 프로파일에서 빈을 적용한다는 뜻. 또한 @Profile은 클래스 레벨에서도 사용 가능하다.
    public CommandLineRunner dataLoader(IngredientRepository repo){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
                repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
                repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
                repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
                repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
                repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
                repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
                repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
                repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
                repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
            }
        };
    }
}
