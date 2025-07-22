package dev.tdh.fruitshop.repository;

import dev.tdh.fruitshop.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByStatusEqualsIgnoreCase(String status);
    List<OrderEntity> findAllByUser_Id(Long userId);
}
