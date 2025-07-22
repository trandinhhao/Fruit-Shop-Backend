package dev.tdh.fruitshop.repository;

import dev.tdh.fruitshop.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByUser_Id(Long userId);
}
