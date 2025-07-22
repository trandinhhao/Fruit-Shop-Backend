package dev.tdh.fruitshop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "fruit")
public class FruitEntity extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private  String name;

    @Column(name = "origin", nullable = false)
    private String origin;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "remain")
    private double remain = 0.0;

    @Column(name = "instock", nullable = false)
    //@JsonProperty("inStock")
    private boolean inStock;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "discount", nullable = false)
    private double discount;

    @Column(name = "info")
    private String info;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "imageurl", nullable = false)
    private String imageUrl; // Lưu URL ảnh

    @OneToMany(mappedBy = "fruit")
    private List<CartItemEntity> cartItems;

    @OneToMany(mappedBy = "fruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems;

    public void setRemain(double remain) {
        this.remain = remain;
    }
}