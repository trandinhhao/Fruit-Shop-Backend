package dev.tdh.fruitshop.model.dto;

import lombok.Data;

@Data
public class FruitDTO extends BaseDTO {
    private String name;
    private String origin;
    private String unit;
    private boolean inStock;
    private double price;
    private double discount;
    private String info;
    private String category;
    private String imageUrl;
    private double remain;
}