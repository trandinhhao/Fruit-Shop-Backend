package dev.tdh.fruitshop.service;

import dev.tdh.fruitshop.model.dto.FruitDTO;
import dev.tdh.fruitshop.model.response.FruitInfoResponse;
import dev.tdh.fruitshop.model.response.FruitSearchResponse;
import dev.tdh.fruitshop.model.response.ResponseDTO;

import java.util.List;
import java.util.Map;

public interface FruitService {
    List<FruitSearchResponse> getFruits(Map<String, Object> conditions);
    FruitInfoResponse getFruitInfo(Long id);
    ResponseDTO deleteFruits(List<Long> ids);
    ResponseDTO saveFruit(FruitDTO fruitDTO);
    List<FruitDTO> getFruitsList();
}
