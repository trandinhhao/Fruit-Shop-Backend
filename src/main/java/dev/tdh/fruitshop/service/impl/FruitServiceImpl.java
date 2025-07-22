package dev.tdh.fruitshop.service.impl;

import dev.tdh.fruitshop.entity.FruitEntity;
import dev.tdh.fruitshop.model.dto.FruitDTO;
import dev.tdh.fruitshop.model.response.FruitInfoResponse;
import dev.tdh.fruitshop.model.response.FruitSearchResponse;
import dev.tdh.fruitshop.model.response.ResponseDTO;
import dev.tdh.fruitshop.repository.CartItemRepository;
import dev.tdh.fruitshop.repository.FruitRepository;
import dev.tdh.fruitshop.service.FruitService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FruitServiceImpl implements FruitService {
    private final FruitRepository fruitRepository;
    private final ModelMapper modelMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public List<FruitSearchResponse> getFruits(Map<String, Object> conditions) {
        List<FruitEntity> fruitEntities = fruitRepository.findAllByConditions(conditions);
        // stream java 8
        return fruitEntities.stream()
                .map(ett -> modelMapper.map(ett, FruitSearchResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public FruitInfoResponse getFruitInfo(Long id) {
        FruitEntity fruitEntity = fruitRepository.findById(id).orElse(null); // neu khong lay duoc tra ra null
        FruitInfoResponse fruitInfoResponse = modelMapper.map(fruitEntity, FruitInfoResponse.class);
        return fruitInfoResponse;
    }

    @Override
    public ResponseDTO deleteFruits(List<Long> fruitIds) {
        //fruitRepository.deleteByIdIn(fruitIds);
        //
        for (Long fruitId : fruitIds) {
            FruitEntity fruitEntity = fruitRepository.findById(fruitId).orElse(null);

            if (fruitEntity != null) {
                // Xóa các tham chiếu trong cart_item trước
                // Nếu bạn có repository cho cart_item
                cartItemRepository.deleteByFruit_Id(fruitId);

                // Xóa các tham chiếu trong order_items
                fruitEntity.getOrderItems().clear();

                // Lưu thay đổi trước khi xóa để đảm bảo các orphan được xử lý
                fruitRepository.saveAndFlush(fruitEntity);

                // Sau đó xóa fruit
                fruitRepository.delete(fruitEntity);
            }
        }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(fruitIds);
        responseDTO.setMessage("Successfully deleted fruits");
        return responseDTO;
    }

    @Override
    public ResponseDTO saveFruit(FruitDTO fruitDTO) {
        FruitEntity fruitEntity = modelMapper.map(fruitDTO, FruitEntity.class);
        //
        ResponseDTO responseDTO = new ResponseDTO();
        if (fruitEntity.getId() == null) {// THEM MOI
            responseDTO.setMessage("Add");
        } else {
            responseDTO.setMessage("Update");
        }
        fruitEntity = fruitRepository.save(fruitEntity); // tu dong tao hoac them moi, thuoc spring data jpa
        responseDTO.setData(fruitEntity.getId());
        return responseDTO;
    }

    @Override
    public List<FruitDTO> getFruitsList() { // for management
        List<FruitEntity> fruitEntities = fruitRepository.findAll();
        return fruitEntities.stream()
                .map(ett -> modelMapper.map(ett, FruitDTO.class))
                .collect(Collectors.toList());
    }
}