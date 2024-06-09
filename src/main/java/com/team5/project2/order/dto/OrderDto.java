package com.team5.project2.order.dto;

import com.team5.project2.order.entity.OrderDetail;
import com.team5.project2.order.entity.OrderStatus;
import java.util.List;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private Long totalPrice;
    private List<OrderDetailDto> orderDetails;

    public Long getTotalPrice() {
        Long result = 0L;
        for (OrderDetailDto orderDetail : orderDetails) {
            result += orderDetail.getPrice() * orderDetail.getCount();
        }
        return result;
    }
}
