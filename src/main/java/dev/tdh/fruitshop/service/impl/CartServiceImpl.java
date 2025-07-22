package dev.tdh.fruitshop.service.impl;

import dev.tdh.fruitshop.entity.*;
import dev.tdh.fruitshop.model.dto.CartItemDTO;
import dev.tdh.fruitshop.model.dto.CheckoutRequestDTO;
import dev.tdh.fruitshop.model.response.CartItemResponse;
import dev.tdh.fruitshop.model.response.ResponseDTO;
import dev.tdh.fruitshop.repository.*;
import dev.tdh.fruitshop.service.CartService;
import dev.tdh.fruitshop.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final FruitRepository fruitRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public ResponseEntity<ResponseDTO> addToCart(Long userId, CartItemDTO cartItemDTO) {
        // get user them hang
        UserEntity userEntity = userRepository.findById(userId);
        // check cart exist
        CartEntity cartEntity = cartRepository.findByUser_Id(userId);
        if (cartEntity == null) {
            cartEntity = new CartEntity();
            cartEntity.setUser(userEntity);
            cartEntity = cartRepository.save(cartEntity); // phai save moi co id
        }
        // check xem san pham da co trong gio hang truoc do chua?
        CartItemEntity cartItemEntity = cartItemRepository
                .findByCart_IdAndFruit_Id(cartEntity.getId(), cartItemDTO.getFruitId());
        //
        if (cartItemEntity == null) { // chua co san pham
            cartItemEntity = new CartItemEntity();
            cartItemEntity.setCart(cartEntity);
            cartItemEntity.setFruit(fruitRepository.findById(cartItemDTO.getFruitId()).get());
            cartItemEntity.setQuantity(cartItemDTO.getQuantity());
            cartItemEntity.setPrice(cartItemEntity.getFruit().getPrice());
        } else {
            cartItemEntity.setQuantity(cartItemDTO.getQuantity() + cartItemEntity.getQuantity());
        }
        cartItemRepository.save(cartItemEntity);
        return ResponseEntity.ok(ResponseUtils.makeResponse("", "them thanh cong", ""));
    }

    @Override
    public ResponseEntity<ResponseDTO> removeFromCart(Long userId, Long fruitId) {
        // lay cai cart
        CartEntity cartEntity = cartRepository.findByUser_Id(userId);
        // xoa cai cartitem
        cartItemRepository.deleteByFruit_IdAndCart_Id(fruitId, cartEntity.getId());
        return ResponseEntity.ok(ResponseUtils.makeResponse("", "xoa thanh cong", ""));
    }

    @Override
    public List<CartItemResponse> showCart(Long userId) {
        // lay gio hang
        CartEntity cartEntity = cartRepository.findByUser_Id(userId);
        // lay het trong gio hang
        List<CartItemEntity> entities = cartItemRepository.findAllByCart_Id(cartEntity.getId());
        return entities.stream().map(item -> {
            CartItemResponse cartItemResponse = new CartItemResponse();
            cartItemResponse.setFruitId(item.getFruit().getId());
            cartItemResponse.setName(item.getFruit().getName());
            cartItemResponse.setQuantity(item.getQuantity());
            cartItemResponse.setPrice(item.getPrice());
            cartItemResponse.setDiscount(item.getFruit().getDiscount());
            cartItemResponse.setUnit(item.getFruit().getUnit());
            cartItemResponse.setImageUrl(item.getFruit().getImageUrl());
            return cartItemResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ResponseDTO> checkoutCart(Long userId, CheckoutRequestDTO checkoutRequestDTO) {
        // lay gio hang
        CartEntity cartEntity = cartRepository.findByUser_Id(userId);
        List<CartItemEntity> selectedItems = cartItemRepository.findAllByCart_IdAndFruit_IdIn(cartEntity.getId(), checkoutRequestDTO.getFruitIds());
        // tao order
        OrderEntity newOrder = new OrderEntity();
        newOrder.setUser(cartEntity.getUser());
        newOrder.setStatus("Pending");
        newOrder.setTotalPrice(0.0);

        // thong tin nguoi nhan
        newOrder.setReceiverName(checkoutRequestDTO.getReceiverName());
        newOrder.setReceiverPhone(checkoutRequestDTO.getReceiverPhone());
        newOrder.setReceiverAddress(checkoutRequestDTO.getReceiverAddress());
        newOrder.setPaymentMethod(checkoutRequestDTO.getPaymentMethod());
        newOrder.setNote(checkoutRequestDTO.getNote());

        orderRepository.save(newOrder); // tao order moi
        // them cac items dc select vao
        double totalPrice = 0.0;
        for (CartItemEntity item : selectedItems) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(newOrder);
            orderItem.setFruit(item.getFruit());
            orderItem.setQuantity(item.getQuantity());
            // tinh giam gia
            double discountPrice = item.getPrice() * (1 - item.getFruit().getDiscount() / 100);
            orderItem.setPrice(discountPrice);
            orderItemRepository.save(orderItem); // tao orderitem
            totalPrice += discountPrice * item.getQuantity();
            cartItemRepository.delete(item);
        }
        newOrder.setTotalPrice(totalPrice);

        orderRepository.save(newOrder);
        return ResponseEntity.ok(ResponseUtils.makeResponse("", "tao don hang thanh cong", ""));
    }
}
