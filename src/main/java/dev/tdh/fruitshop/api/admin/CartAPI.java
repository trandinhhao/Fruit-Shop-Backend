package dev.tdh.fruitshop.api.admin;

import dev.tdh.fruitshop.model.dto.CartItemDTO;
import dev.tdh.fruitshop.model.dto.CheckoutRequestDTO;
import dev.tdh.fruitshop.model.response.CartItemResponse;
import dev.tdh.fruitshop.model.response.ResponseDTO;
import dev.tdh.fruitshop.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CartAPI {
    private final CartService cartService;
    // them sp vao gio
    @PostMapping("/{userId}/add")
    public ResponseEntity<ResponseDTO> addFruitToCart(@PathVariable Long userId, @RequestBody CartItemDTO cartItemDTO) {
        return cartService.addToCart(userId, cartItemDTO);
    }
    // show gio hang
    @GetMapping("/{userId}")
    public List<CartItemResponse> getCart(@PathVariable Long userId) {
        return cartService.showCart(userId);
    }
    //xoa san pham
    @DeleteMapping("/{userId}/remove/{fruitId}")
    public ResponseEntity<ResponseDTO> removeFruitFromCart(@PathVariable Long userId, @PathVariable Long fruitId) {
        return cartService.removeFromCart(userId, fruitId);
    }
    // thanh toan gio hang, nhung san pham duoc lua chon
    @PostMapping("/{userId}/checkout")
    public ResponseEntity<ResponseDTO> checkoutCart(@PathVariable Long userId, @RequestBody CheckoutRequestDTO checkoutRequestDTO) {
        return cartService.checkoutCart(userId, checkoutRequestDTO);
    }
}