package dev.tdh.fruitshop.api.admin;
import dev.tdh.fruitshop.model.dto.OrderDTO;
import dev.tdh.fruitshop.model.dto.OrderItemDTO;
import dev.tdh.fruitshop.model.response.ResponseDTO;
import dev.tdh.fruitshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderAPI {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDTO> searchOrders() { // lay het tat ca order tu moi nhat den cu nhat
        List<OrderDTO> oderList = orderService.getOrders();
        return oderList;
    }

    @GetMapping("/pending")
    public List<OrderDTO> searchPendingOrders() { // lay cac order dang trong hang cho xu ly, thu tu tu cu nhat
        List<OrderDTO> oderList = orderService.getPendingOrders();
        return oderList;
    }

    @GetMapping("/success")
    public List<OrderDTO> searchSuccessOrders() { // lay cac order xu ly xong roi, thu tu tu moi nhat
        List<OrderDTO> oderList = orderService.getSuccessOrders();
        return oderList;
    }

    @GetMapping("/cancel")
    public List<OrderDTO> searchCancelOrders() { // lay cac order xu ly xong roi, thu tu tu moi nhat
        List<OrderDTO> oderList = orderService.getCancelOrders();
        return oderList;
    }

    @PostMapping("/{orderId}/{status}")
    public ResponseEntity<ResponseDTO> changeStatus(@PathVariable long orderId, @PathVariable String status) {
        return orderService.changeStatus(orderId, status);
    }

    @GetMapping("{orderId}")
    public List<OrderItemDTO> getOrder(@PathVariable long orderId) {
        List<OrderItemDTO> aboutOrder = orderService.getOrder(orderId);
        return aboutOrder;
    }

    @GetMapping("/status/{userId}")
    public List<OrderDTO> getOrdersByUserId(@PathVariable long userId) {
        return orderService.getOrdersByUserId(userId);
    }
}