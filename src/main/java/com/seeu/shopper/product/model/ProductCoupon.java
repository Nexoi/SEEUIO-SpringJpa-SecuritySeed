package com.seeu.shopper.product.model;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 考虑到并不是每次都需要把 product 的优惠券信息列出来，所以单独做表，如有需要可以单独查询该表
 */
@Entity
@Table(name = "product_coupon", indexes = {
        @Index(name = "PRODUCTCOUPON_INDEX1", columnList = "pid"),
        @Index(name = "PRODUCTCOUPON_INDEX2", columnList = "cid")
})
@DynamicUpdate
public class ProductCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long pid;

    @Column(length = 20)
    @Length(max = 20, min = 4, message = "coupon id length was between 4 - 20")
    private String cid;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return pid
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @return cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * @param cid
     */
    public void setCid(String cid) {
        this.cid = cid;
    }
}