package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
    private OrderRepository orderRepo;
    private OrderProps props;


    @Autowired
    public OrderController(OrderRepository orderRepo, OrderProps props) {
        this.orderRepo = orderRepo;
        this.props = props;
    }

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user,
                            @ModelAttribute Order order){
        if (order.getDeliveryName() == null)
            order.setDeliveryName(user.getFullname());
        if (order.getDeliveryStreet() == null)
            order.setDeliveryStreet(user.getStreet());
        if (order.getDeliveryCity() == null)
            order.setDeliveryCity(user.getCity());
        if (order.getDeliveryState() == null)
            order.setDeliveryState(user.getState());
        if (order.getDeliveryZip() == null)
            order.setDeliveryZip(user.getZip());

        return "orderForm";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model){
        Pageable pageable = PageRequest.of(0, props.getPageSize());
        model.addAttribute("orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));

        return "orderList";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, // SessionStatus: https://meteorkor.tistory.com/14
                               @AuthenticationPrincipal User user){ // @AuthenticationPrincipal: https://cantcoding.tistory.com/87
        if (errors.hasErrors())
            return "orderForm";

        order.setUser(user);
        orderRepo.save(order); // 폼에서 제출된 주문 저장
        sessionStatus.setComplete(); // 주문을 저장하고 나면 세션에 저장할 필요가 없다. 이 코드로 세션을 재설정한다.

        return "redirect:/";
    }
}
