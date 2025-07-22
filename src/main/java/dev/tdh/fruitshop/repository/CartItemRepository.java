package dev.tdh.fruitshop.repository;

import dev.tdh.fruitshop.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    CartItemEntity findByCart_IdAndFruit_Id(Long cartId, Long fruitId);
    void deleteByFruit_IdAndCart_Id(Long fruitId, Long cartId);
    List<CartItemEntity> findAllByCart_Id(Long cartId);
    List<CartItemEntity> findAllByCart_IdAndFruit_IdIn(Long cartId, List<Long> fruitIds);
    void deleteByFruit_Id(Long fruitId);
}