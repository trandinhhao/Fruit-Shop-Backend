package dev.tdh.fruitshop.repository.custom;

import dev.tdh.fruitshop.entity.FruitEntity;

import java.util.List;
import java.util.Map;

public interface FruitRepositoryCustom {
    List<FruitEntity> findAllByConditions(Map<String, Object> conditions);
}