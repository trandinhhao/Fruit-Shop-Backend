package dev.tdh.fruitshop.service;

import dev.tdh.fruitshop.model.dto.CartItemDTO;
import dev.tdh.fruitshop.model.dto.CheckoutRequestDTO;
import dev.tdh.fruitshop.model.response.CartItemResponse;
import dev.tdh.fruitshop.model.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    ResponseEntity<ResponseDTO> addToCart(Long userId, CartItemDTO cartItemDTO);
    ResponseEntity<ResponseDTO> removeFromCart(Long userId, Long fruitId);
    List<CartItemResponse> showCart(Long userId);
    ResponseEntity<ResponseDTO> checkoutCart(Long userId, CheckoutRequestDTO checkoutRequestDTO);
}
