package com.seeu.shopper.coupon.model.publiccoupon;

import com.seeu.shopper.coupon.model.COUPON_TYPE;
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
@Table(name = "coupon_pu_available_field_ids", indexes = {
        @Index(name = "COUPON_PU_FIELD_INDEX1", columnList = "field_id"),
        @Index(name = "COUPON_PU_FIELD_INDEX2", columnList = "code")
})
@DynamicUpdate
public class PublicAvailableFieldIds {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code", length = 5)
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
