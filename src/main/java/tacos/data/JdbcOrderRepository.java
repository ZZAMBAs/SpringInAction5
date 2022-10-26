package tacos.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacos.Order;
import tacos.Taco;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository{
    // SimpleJdbcInsert는 데이터를 쉽게 테이블에 추가하기 위핸 JdbcTemplate의 래퍼 클래스이다.
    // 테이블 이름과 같은 것을 메서드로 입력해준 뒤, Map 타입(속성-테이블의 열-, 값)을 받아 처리한다.
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    // ObjectMapper는 JSON -> Object 또는 그 반대를 처리하려 많이 사용하지만 여기서는 객체 -> Map 으로 변환하기 위해 사용한다.
    // https://spearzero.tistory.com/18
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.orderInserter = new SimpleJdbcInsert(jdbcTemplate) // 래핑
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");

        this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order_Tacos");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();

        for (Taco taco : tacos){
            saveTacoToOrder(taco, orderId);
        }

        return order;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }

    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked") // https://solbel.tistory.com/209
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt()); // objectMapper는 Date 타입을 long 타입으로 강제 변경하므로, 이 코드로 바로잡아준다.

        long orderId = orderInserter.executeAndReturnKey(values).longValue(); // executeAndReturnKey()는 데이터 삽입 수행 후, 자동 생성된 키값을 반환한다.
        return orderId;
    }
}
