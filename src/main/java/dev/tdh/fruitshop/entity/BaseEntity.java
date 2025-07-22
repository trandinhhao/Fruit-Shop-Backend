package dev.tdh.fruitshop.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass // Khai bao lop cha khong phai la entity nhung chua cac thuoc tinh chung cho cac entity con
@EntityListeners(AuditingEntityListener.class) // Theo doi thong tin, thoi gian cap nhat, ket hop cac Annotation o duoi
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tu dong tang, la khoa chinh
    private Long id;

    @Column(name = "createdate")
    @CreatedDate
    private Date createDate;

    @Column(name = "modifieddate")
    @LastModifiedDate
    private Date modifiedDate;

    @Column(name = "createby")
    @CreatedBy
    private String createBy = "Admin";

    @Column(name = "modifiedby")
    @LastModifiedBy
    private String modifiedBy = "Admin";
}
