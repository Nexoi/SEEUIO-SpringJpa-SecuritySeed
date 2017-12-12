package com.seeu.shopper.order.model;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_product", indexes = {
        @Index(name = "ORDERPRODUCT_INDEX1", columnList = "oid")
})
@DynamicUpdate
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "oid", length = 20)
    @Length(max = 20)
    private String oid;

    private Integer pid;

    private String sku; // 23.34.36.57

    private String name;

    @Column(name = "show_price")
    private BigDecimal showPrice; // 产品展示售价

    @Column(name = "transaction_price")
    private BigDecimal transactionPrice; // 产品真实成交价格

    private Integer quantity;

    @Column(name = "is_deal")
    private Boolean isDeal;  // 是否促销，如果有，则一般 showPrice 和 transactionPrice 价格会不一致

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return oid
     */
    public String getOid() {
        return oid;
    }

    /**
     * @param oid
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(BigDecimal showPrice) {
        this.showPrice = showPrice;
    }

    public BigDecimal getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(BigDecimal transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    /**
     * @return is_deal
     */
    public Boolean getIsDeal() {
        return isDeal;
    }

    /**
     * @param isDeal
     */
    public void setIsDeal(Boolean isDeal) {
        this.isDeal = isDeal;
    }
}