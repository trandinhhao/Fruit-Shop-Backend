package dev.tdh.fruitshop.service.impl;

import dev.tdh.fruitshop.entity.UserEntity;
import dev.tdh.fruitshop.model.dto.UserDTO;
import dev.tdh.fruitshop.model.response.ResponseDTO;
import dev.tdh.fruitshop.repository.UserRepository;
import dev.tdh.fruitshop.service.UserService;
import dev.tdh.fruitshop.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<ResponseDTO> registerUser(UserDTO userDTO) {
        //
        if (userRepository.findByUserName(userDTO.getUserName()) != null ||
                userRepository.findByEmail(userDTO.getEmail()) != null ||
                userRepository.findByPhoneNumber(userDTO.getPhoneNumber()) != null) { // trung username hoac email hoac sdt
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ResponseUtils.makeResponse(null, "Trung thong tin",""));
        }
        userDTO.setStatus(1); // bat status len
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ResponseUtils.makeResponse(null, "Dang ky thanh cong",""));
    }

    @Override
    public ResponseEntity<ResponseDTO> loginUser(UserDTO userDTO) {
        UserEntity userEntity = userRepository.findByUserName(userDTO.getUserName());
        if (userEntity == null) { // k tim thay
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(ResponseUtils.makeResponse(userDTO.getUserName(), "Tai khoan khong ton tai", ""));
        }
        if (!userEntity.getPassword().equals(userDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(ResponseUtils.makeResponse(userDTO.getUserName(), "Mat khau khong khop", ""));
        }
        if (userEntity.getStatus() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(ResponseUtils.makeResponse(userDTO.getUserName(), "Tai khoan da bi khoa", ""));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtils.makeResponse(userDTO.getUserName(), "Dang nhap thanh cong", userEntity.getId().toString()));
    }

    @Override
    public List<UserDTO> getUsersList() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map(ett -> modelMapper.map(ett, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ResponseDTO> saveUser(UserDTO userDTO) {
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        ResponseDTO responseDTO = new ResponseDTO();
        if (userEntity.getId() == null) {// them moi
            responseDTO.setMessage("Add");
        } else {
            responseDTO.setMessage("Update");
        }
        userEntity = userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteUsers(List<Long> ids) {
        userRepository.deleteByIdIn(ids);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ResponseUtils.makeResponse(ids, "Xoa thanh cong",""));
    }

    @Override
    public ResponseEntity<ResponseDTO> adminLogin(UserDTO userDTO) {
        if (userDTO.getUserName() != null && userDTO.getPassword() != null) {
            // xu li them sau, bay gio test
            if (userDTO.getUserName().equals("admin") && userDTO.getPassword().equals("admin")) {
                return ResponseEntity.status(HttpStatus.OK).body(ResponseUtils.makeResponse("","OK",""));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseUtils.makeResponse("","Unauthorized",""));
    }
}