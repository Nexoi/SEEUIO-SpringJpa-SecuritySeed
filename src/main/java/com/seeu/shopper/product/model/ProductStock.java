package com.seeu.shopper.product.model;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * sku集合组成的字符串 e.g: 34.40.45.78 长度不可超过 30
 */
@Entity
@Table(name = "product_stock", indexes = {
        @Index(name = "STOCK_INDEX1", columnList = "pid")
//        @Index(name = "STOCK_INDEX2", columnList = "specification_ids")
})
@DynamicUpdate
public class ProductStock {
    /**
     * 规格id，点号隔开，升序
     * 3.6.8.12
     * <p>
     * 其实这个就是 sku
     */
    @Id
    @Column(name = "specification_ids", length = 30)
    @Length(max = 30)
    private String specificationIds;
//
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long sid;

    private Long pid;


    /**
     * 库存
     */
    private Integer stock;

    @Column(name = "origin_price")
    private BigDecimal originPrice;

    private BigDecimal price;

    /**
     * 启动该库存
     */
    @Column(name = "is_ing")
    private Boolean isIng;

    @Column(name = "update_date")
    private Date updateDate;

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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getSpecificationIds() {
        return specificationIds;
    }

    public void setSpecificationIds(String specificationIds) {
        this.specificationIds = specificationIds;
    }

    /**
     * @return origin_price
     */
    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    /**
     * @param originPrice
     */
    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    /**
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取启动该库存
     *
     * @return is_ing - 启动该库存
     */
    public Boolean getIsIng() {
        return isIng;
    }

    /**
     * 设置启动该库存
     *
     * @param isIng 启动该库存
     */
    public void setIsIng(Boolean isIng) {
        this.isIng = isIng;
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}