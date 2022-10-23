package tacos;

import lombok.Data;

import java.util.List;

// 타코 도메인
@Data
public class Taco {
    private String name;
    private List<String> ingredients;
}
