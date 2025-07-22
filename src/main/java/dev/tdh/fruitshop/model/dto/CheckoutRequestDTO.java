package dev.tdh.fruitshop.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckoutRequestDTO {
    private List<Long> fruitIds;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String paymentMethod;
    private String note;
}
