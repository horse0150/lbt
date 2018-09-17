package com.wwb.lbttrade.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "imu_fund")
public class Fund {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message="主键不存在无法更新")
    private Long id;


    @Column(name = "user_id")
    private Long userId;

    @Column(name = "money")
    private BigDecimal money;
}
