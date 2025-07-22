package dev.tdh.fruitshop.model.response;

import lombok.Data;

@Data
public class CartItemResponse {
    private Long fruitId;
    private String name;
    private int quantity;
    private double price;
    private double discount;
    private String imageUrl;
    private String unit;
}