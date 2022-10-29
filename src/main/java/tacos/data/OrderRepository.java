package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
    // List<Order> findByDeliveryZip(String deliveryZip); // JPA Repository 커스터마이징.
    // https://www.baeldung.com/spring-data-derived-queries, https://jithub.tistory.com/342 의 맨 아래 챕터.
}
