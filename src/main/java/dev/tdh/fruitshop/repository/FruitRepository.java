package dev.tdh.fruitshop.repository;

import dev.tdh.fruitshop.entity.FruitEntity;
import dev.tdh.fruitshop.repository.custom.FruitRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FruitRepository extends JpaRepository<FruitEntity, Long>, FruitRepositoryCustom {
    void deleteByIdIn(List<Long> fruitIds);
}
