package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository{
    private JdbcTemplate jdbcTemplate;
    // Javadoc: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html

    @Autowired // 생성자가 하나면 생략 가능.
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("select * from Ingredient", this::mapRowToIngredient);
        // 더블 콜론 연산자(람다식 편의 연산자): http://yoonbumtae.com/?p=2776
        // this::mapRowToIngredient == (rs, rowNum) -> this.mapRowToIngredient(rs, rowNum);
        // query는 여러 값이 나올 경우가 예상될 때 사용하며, queryForObject는 단 하나의 값이 예상될 때 사용한다.
        // query는 각각의 결과를 RowMapper를 이용해 객체에 저장하고 리스트 형태로 반환한다.
    }

    @Override
    public Ingredient findById(String id) {
        return jdbcTemplate.queryForObject("select * from Ingredient where id = ?", this::mapRowToIngredient, id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update("insert into Ingredient values (?, ?, ?)",
                ingredient.getId(), ingredient.getName(), String.valueOf(ingredient.getType()));
        // String.valueOf(ingredient.getType()) == ingredient.getType().toString()
        return null;
    }

    // RowMapper: https://velog.io/@seculoper235/RowMapper%EC%97%90-%EB%8C%80%ED%95%B4
    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }
}
