package dev.tdh.fruitshop.service;

import dev.tdh.fruitshop.model.dto.UserDTO;
import dev.tdh.fruitshop.model.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<ResponseDTO> registerUser(UserDTO userDTO);
    ResponseEntity<ResponseDTO> loginUser(UserDTO userDTO);
    List<UserDTO> getUsersList();
    ResponseEntity<ResponseDTO> saveUser(UserDTO userDTO);
    ResponseEntity<ResponseDTO> deleteUsers(List<Long> ids);
    ResponseEntity<ResponseDTO> adminLogin(UserDTO userDTO);
}
