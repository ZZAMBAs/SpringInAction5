package tacos.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import tacos.Order;
import tacos.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable); // PlacedAt 속성을 기준으로 User 내림차순 정렬

    // JPA 페이징: https://wonit.tistory.com/483, https://ojt90902.tistory.com/716

    // List<Order> findByDeliveryZip(String deliveryZip); // JPA Repository 커스터마이징.
    // https://www.baeldung.com/spring-data-derived-queries, https://jithub.tistory.com/342 의 맨 아래 챕터.
}
