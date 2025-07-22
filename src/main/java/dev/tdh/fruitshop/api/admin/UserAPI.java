package dev.tdh.fruitshop.api.admin;

import dev.tdh.fruitshop.model.dto.UserDTO;
import dev.tdh.fruitshop.model.response.ResponseDTO;
import dev.tdh.fruitshop.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Transactional
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserDTO userDTO) {
        return userService.loginUser(userDTO);
    }

}