package dev.tdh.fruitshop.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private long userId;
    private double totalPrice;
    private String status;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String paymentMethod;
    private String note;
    private LocalDateTime createdAt;
}