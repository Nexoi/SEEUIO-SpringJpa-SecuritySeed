package com.seeu.shopper.currency.model;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 28/11/2017
 * Time: 12:54 AM
 * Describe:
 * <p>
 * Model
 * <p>
 * 相较于 $USD 1 美元的价格比例
 */
@Entity
@Table(name = "currency", indexes = {
        @Index(name = "CURRENCY_INDEX1", unique = true, columnList = "currency")
})
@DynamicUpdate
public class Currency {
    @Id
    @Column(length = 3)
    @Length(max = 3, message = "currency should be like: USD, CAD. length could not be larger than 3")
    private String currency;
    @Column(precision = 19, scale = 4) // 精度设为 4 位小数点
    private BigDecimal weight;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
