package com.team5.project2.order.controller;

import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.service.OrderService;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.service.ProductService;
import com.team5.project2.user.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/admin")
    public String adminHome(Model model) {
        List<OrderDto> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/adminHome";
    }

    @GetMapping("/sheet")
    public String viewOrder(Principal principal, @RequestParam List<Long> productIds, @RequestParam List<Long> counts, Model model) {
        String userEmail = principal.getName();
        Long userId = userService.findUserByEmail(userEmail).getId();

        List<OrderDetailDto> orderDetails = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
//            Product product = productService.getProductById();
            OrderDetailDto orderDetailDto = new OrderDetailDto();
            orderDetailDto.setProductId(productIds.get(i));
            orderDetailDto.setCount(counts.get(i));
//            orderDetailDto.setPrice(prices.get(i));
            orderDetails.add(orderDetailDto);
        }
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("userId", userId);
        return "/order/orderSheet";
    }

    @GetMapping
    public String getUserOrders(Principal principal, Model model) {
        String userEmail = principal.getName();
        Long userId = userService.findUserByEmail(userEmail).getId();

        List<OrderDto> orders = orderService.getOrders(userId);
        model.addAttribute("orders", orders);
        return "/order/orderList";
    }

    @GetMapping("/detail/{orderId}")
    public String showDetail(@PathVariable Long orderId, Model model) {
        OrderDto order = orderService.getOrderById(orderId);
        List<OrderDetailDto> orderDetails = orderService.getOrderDetails(orderId);
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);
        return "/order/orderDetail";
    }
}
