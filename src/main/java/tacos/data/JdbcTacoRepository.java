package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;
import tacos.Taco;

import java.sql.*;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()){
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreateAt(new Date()); // 생성시간을 현재 시간으로써 저장
        PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
                // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/PreparedStatementCreatorFactory.html
                // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/PreparedStatementCreator.html
                // PreparedStatementCreator는 JdbcTemplate의 콜백으로 사용되며, Factory는 여러개의 Creator 생성을 돕는다.
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP
        ).newPreparedStatementCreator(
                Arrays.asList(taco.getName(), new Timestamp(taco.getCreateAt().getTime())));
        // 날짜 타입에 대해: https://m.blog.naver.com/nieah914/221810697040

        KeyHolder keyHolder = new GeneratedKeyHolder(); // KeyHolder를 쓰는 이유: https://joanne.tistory.com/123
        jdbcTemplate.update(psc, keyHolder);
        /*jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into Taco (name, createdAt) values (?, ?)");
            ps.setString(1, taco.getName());
            ps.setTimestamp(2, new Timestamp(taco.getCreateAt().getTime()));
            return ps;
        }, keyHolder);*/ // 이 코드로 써도 괜찮다.
        return keyHolder.getKey().longValue(); // 타코 id를 반환.
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        jdbcTemplate.update("insert into Taco_Ingredients(taco, ingredient) values (?, ?)",
                tacoId, ingredient.getId());
    }
}
