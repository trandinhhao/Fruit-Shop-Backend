package dev.tdh.fruitshop.model.dto;

import lombok.Data;

@Data
public class UserDTO extends BaseDTO {
    private String userName;
    private String password;
    private String phoneNumber;
    private Integer status;
    private String fullName;
    private String email;
    private String address;
}
