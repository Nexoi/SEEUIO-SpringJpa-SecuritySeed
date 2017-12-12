package com.seeu.shopper.coupon.model.privatecoupon;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 10/12/2017
 * Time: 3:04 AM
 * Describe:
 * <p>
 * 用户私有的优惠券信息
 */


@Entity
@Table(name = "coupon_private_bind_user", indexes = {
        @Index(name = "PRIVATECOUON_INDEX1", columnList = "uid"),
        @Index(name = "PRIVATECOUON_INDEX2", columnList = "cid")
})
@DynamicUpdate
public class PrivateCouponBindUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long uid;
    private Long cid;
    @Enumerated(EnumType.ORDINAL)
    private PRIVATE_COUPON_USER_STATUS status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public PRIVATE_COUPON_USER_STATUS getStatus() {
        return status;
    }

    public void setStatus(PRIVATE_COUPON_USER_STATUS status) {
        this.status = status;
    }
}
