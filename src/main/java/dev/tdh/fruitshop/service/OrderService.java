package dev.tdh.fruitshop.service;

import dev.tdh.fruitshop.model.dto.OrderDTO;
import dev.tdh.fruitshop.model.dto.OrderItemDTO;
import dev.tdh.fruitshop.model.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getOrders();
    List<OrderDTO> getPendingOrders();
    List<OrderDTO> getSuccessOrders();
    List<OrderDTO> getCancelOrders();
    ResponseEntity<ResponseDTO> changeStatus(Long orderId, String status);
    List<OrderItemDTO> getOrder(Long orderId);
    List<OrderDTO> getOrdersByUserId(Long userId);
}
