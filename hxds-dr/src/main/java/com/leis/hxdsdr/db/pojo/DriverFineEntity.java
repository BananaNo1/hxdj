package com.leis.hxdsdr.db.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class DriverFineEntity implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 司机ID
     */
    private Long driverId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 罚款金额
     */
    private BigDecimal amount;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 1未缴纳，2已缴纳
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}