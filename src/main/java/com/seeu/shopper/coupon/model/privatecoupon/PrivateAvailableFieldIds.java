package com.seeu.shopper.coupon.model.privatecoupon;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 10/12/2017
 * Time: 3:51 AM
 * Describe:
 */
@Entity
@Table(name = "coupon_pr_available_field_ids", indexes = {
        @Index(name = "COUPON_PR_FIELD_INDEX1", columnList = "field_id"),
        @Index(name = "COUPON_PR_FIELD_INDEX1", columnList = "coupon_id")
})
@DynamicUpdate
public class PrivateAvailableFieldIds {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "field_id")
    private Long fieldId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }
}
