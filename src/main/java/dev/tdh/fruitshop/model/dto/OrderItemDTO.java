package dev.tdh.fruitshop.model.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private long fruitId;
    private String fruitName;
    private long quantity;
    private double price;
    private double discount;
}