package dev.tdh.fruitshop.api.admin;

import dev.tdh.fruitshop.model.response.FruitInfoResponse;
import dev.tdh.fruitshop.model.response.FruitSearchResponse;
import dev.tdh.fruitshop.service.FruitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Transactional // de khi lenh bi loi se back lai
@RequestMapping("api/fruits")
@RequiredArgsConstructor
public class FruitAPI {
    private final FruitService fruitService;

    @GetMapping // lay het danh sach thong tin co ban len web ban hang, phai co trang thai instock
    public List<FruitSearchResponse> searchFruits(@RequestParam Map<String, Object> params) {
        List<FruitSearchResponse> searchList = fruitService.getFruits(params);
        return searchList;
    }

    // lay thong tin chi tiet cua 1 loai trai, tru thong tin quan trong cay duoc lua chon, len web ban hang
    @GetMapping("/{id}")
    public FruitInfoResponse getFruit(@PathVariable Long id) {
        FruitInfoResponse fruitInfoResponse = fruitService.getFruitInfo(id);
        return fruitInfoResponse;
    }

}