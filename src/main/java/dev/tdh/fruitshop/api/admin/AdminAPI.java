package dev.tdh.fruitshop.api.admin;

import dev.tdh.fruitshop.model.dto.FruitDTO;
import dev.tdh.fruitshop.model.dto.UserDTO;
import dev.tdh.fruitshop.model.response.ResponseDTO;
import dev.tdh.fruitshop.service.FruitService;
import dev.tdh.fruitshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
public class AdminAPI {

    private final FruitService fruitService;
    private final UserService userService;

    // login, khong co register
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserDTO userDTO) {
        return userService.adminLogin(userDTO);
    }


    // quyen quan ly trai cay---
    @GetMapping("/fruits/all") // lay het danh sach, len web quan ly
    public List<FruitDTO> searchFruitsList() {
        List<FruitDTO> searchList = fruitService.getFruitsList();
        return searchList;
    }

    @PostMapping("/fruits") // them hoac chinh sua fruit
    public ResponseDTO addOrUpdateFruit(@RequestBody FruitDTO fruitDTO) {
        return fruitService.saveFruit(fruitDTO);
    }

    @DeleteMapping("/fruits/delete/{ids}") //xoa fruit
    public ResponseDTO deleteFruits(@PathVariable String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return fruitService.deleteFruits(idList);
    }

    // quyen quan ly users---
    @GetMapping("/users/all")
    public List<UserDTO> searchUsersList() {
        List<UserDTO> searchList = userService.getUsersList();
        return searchList;
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseDTO> addOrUpdateUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @DeleteMapping("/users/delete/{ids}")
    public ResponseEntity<ResponseDTO> deleteUsers(@PathVariable String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return userService.deleteUsers(idList);
    }

}