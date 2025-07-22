package dev.tdh.fruitshop.model.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long fruitId;
    private int quantity;
}
