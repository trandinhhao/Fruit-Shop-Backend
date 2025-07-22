package dev.tdh.fruitshop.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseDTO implements Serializable {
    private Long id;
    private Date createDate;
    private Date modifiedDate;
    private String createBy = "Admin";
    private String modifiedBy = "Admin";
}
