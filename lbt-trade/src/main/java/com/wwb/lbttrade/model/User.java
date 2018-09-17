package com.wwb.lbttrade.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "imu-user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message="主键不存在无法更新")
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "state")
    private Integer state;
}
