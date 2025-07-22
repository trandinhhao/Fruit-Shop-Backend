package dev.tdh.fruitshop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phonenumber", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "status")
    private Integer status;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "address")
    private String address;

    // 1 nguoi 1 gio hang
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CartEntity cart;

    // 1 nguoi - n don dat hang
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OrderEntity> orders;
}
