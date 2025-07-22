package dev.tdh.fruitshop.model.response;

import lombok.Data;

@Data
public class FruitInfoResponse { // thong tin trai cay lua chon
    private long id;
    private String name;
    private String origin;
    private String unit;
    private boolean inStock;
    private double price;
    private double discount;
    private String info;
    private String category;
    private String imageUrl;
}
