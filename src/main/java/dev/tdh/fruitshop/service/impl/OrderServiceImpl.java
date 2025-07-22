package dev.tdh.fruitshop.service.impl;

import dev.tdh.fruitshop.entity.OrderEntity;
import dev.tdh.fruitshop.entity.OrderItemEntity;
import dev.tdh.fruitshop.model.dto.OrderDTO;
import dev.tdh.fruitshop.model.dto.OrderItemDTO;
import dev.tdh.fruitshop.model.response.ResponseDTO;
import dev.tdh.fruitshop.repository.OrderItemRepository;
import dev.tdh.fruitshop.repository.OrderRepository;
import dev.tdh.fruitshop.service.OrderService;
import dev.tdh.fruitshop.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderDTO> getOrders() { // lay het tat ca order, moi nhat den cu nhat
        List<OrderEntity> orderEntities = orderRepository.findAll();
        Collections.reverse(orderEntities); // Đảo ngược danh sách, để lấy theo thời gian
        return orderEntities.stream()
                .map(ett -> modelMapper.map(ett, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getPendingOrders() { // lay het tat ca order dang cho xu ly, tu cu nhat den moi nhat
        List<OrderEntity> orderEntities = orderRepository.findAllByStatusEqualsIgnoreCase("Pending");
        return orderEntities.stream()
                .map(ett -> modelMapper.map(ett, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getSuccessOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAllByStatusEqualsIgnoreCase("Success");
        Collections.reverse(orderEntities); // Đảo ngược danh sách, để lấy theo thời gian
        return orderEntities.stream()
                .map(ett -> modelMapper.map(ett, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getCancelOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAllByStatusEqualsIgnoreCase("Cancel");
        Collections.reverse(orderEntities); // Đảo ngược danh sách, để lấy theo thời gian
        return orderEntities.stream()
                .map(ett -> modelMapper.map(ett, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ResponseDTO> changeStatus(Long orderId, String status) {
        OrderEntity orderEntity = orderRepository.findById(orderId).get();
        orderEntity.setStatus(status);
        orderRepository.save(orderEntity);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtils.makeResponse(orderId, "Chinh sua thanh cong", ""));
    }

    @Override
    public List<OrderItemDTO> getOrder(Long orderId) {
        List<OrderItemEntity> orderItemEntities = orderItemRepository.findAllByOrder_Id(orderId);
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for (OrderItemEntity orderItemEntity : orderItemEntities) {
            // set tay, khong dung model mapper
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            // set
            orderItemDTO.setFruitId(orderItemEntity.getFruit().getId());
            orderItemDTO.setFruitName(orderItemEntity.getFruit().getName());
            orderItemDTO.setQuantity(orderItemEntity.getQuantity());
            orderItemDTO.setDiscount(orderItemEntity.getFruit().getDiscount());
            orderItemDTO.setPrice(orderItemEntity.getFruit().getPrice());
            orderItemDTOS.add(orderItemDTO);
        }
        return orderItemDTOS;
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<OrderEntity> orderEntities = orderRepository.findAllByUser_Id(userId);
        Collections.reverse(orderEntities);
        return orderEntities.stream()
                .map(ett -> modelMapper.map(ett, OrderDTO.class))
                .collect(Collectors.toList());
    }
}