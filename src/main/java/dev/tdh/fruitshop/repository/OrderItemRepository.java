package dev.tdh.fruitshop.repository;

import dev.tdh.fruitshop.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findAllByFruit_Id(Long fruitId);
    List<OrderItemEntity> findAllByOrder_Id(Long orderId);
}