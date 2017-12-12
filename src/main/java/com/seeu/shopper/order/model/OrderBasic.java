package com.seeu.shopper.order.model;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_basic", indexes = {
        @Index(name = "ORDERBASIC_INDEX1", columnList = "uid"),
        @Index(name = "ORDERBASIC_INDEX2", columnList = "status")
})
@DynamicUpdate
public class OrderBasic {
    @Id
    @Column(length = 20)
    @Length(max = 20, message = "Order Id Length could not larger than 20")
    private String oid;

    /**
     * 1:已下单，未支付
     */
    private Integer status;

    private Integer uid;

    @Column(name = "user_name")
    private String userName;

    private Integer quantity;

    /**
     * 计价单位
     */
    @Column(length = 5)
    private String currency = "USD";

    @Column(name = "show_price")
    private BigDecimal showPrice;

    @Column(name = "ship_price")
    private BigDecimal shipPrice;

    @Column(name = "transaction_price")
    private BigDecimal transactionPrice;

    private Integer weight; // 单位：克 (g)


    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "oid") // 根据 list 的 id 去查询对应评论集合
//    @OrderBy("id DESC")
    private List<OrderProduct> products = new ArrayList<>();

    private String coupon;

    @Column(name = "coupon_type")
    private Integer couponType;

    /**
     * 物流方式id
     */
    @Column(name = "ship_id")
    private Integer shipId;

    @Column(name = "pay_method")
    private Integer payMethod;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 支付完成时间
     */
    @Column(name = "pay_finish_time")
    private Date payFinishTime;

    /**
     * 发货开始时间
     */
    @Column(name = "ship_start_time")
    private Date shipStartTime;

    /**
     * 交易完成关闭时间
     */
    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取1:已下单，未支付
     *
     * @return status - 1:已下单，未支付
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1:已下单，未支付
     *
     * @param status 1:已下单，未支付
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
     * @return ship_price
     */
    public BigDecimal getShipPrice() {
        return shipPrice;
    }

    /**
     * @param shipPrice
     */
    public void setShipPrice(BigDecimal shipPrice) {
        this.shipPrice = shipPrice;
    }

    /**
     * @return weight
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * @param weight
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * @return coupon
     */
    public String getCoupon() {
        return coupon;
    }

    /**
     * @param coupon
     */
    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    /**
     * @return coupon_type
     */
    public Integer getCouponType() {
        return couponType;
    }

    /**
     * @param couponType
     */
    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    /**
     * 获取物流方式id
     *
     * @return ship_id - 物流方式id
     */
    public Integer getShipId() {
        return shipId;
    }

    /**
     * 设置物流方式id
     *
     * @param shipId 物流方式id
     */
    public void setShipId(Integer shipId) {
        this.shipId = shipId;
    }

    /**
     * @return pay_method
     */
    public Integer getPayMethod() {
        return payMethod;
    }

    /**
     * @param payMethod
     */
    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取支付完成时间
     *
     * @return pay_finish_time - 支付完成时间
     */
    public Date getPayFinishTime() {
        return payFinishTime;
    }

    /**
     * 设置支付完成时间
     *
     * @param payFinishTime 支付完成时间
     */
    public void setPayFinishTime(Date payFinishTime) {
        this.payFinishTime = payFinishTime;
    }

    /**
     * 获取发货开始时间
     *
     * @return ship_start_time - 发货开始时间
     */
    public Date getShipStartTime() {
        return shipStartTime;
    }

    /**
     * 设置发货开始时间
     *
     * @param shipStartTime 发货开始时间
     */
    public void setShipStartTime(Date shipStartTime) {
        this.shipStartTime = shipStartTime;
    }

    /**
     * 获取交易完成关闭时间
     *
     * @return end_time - 交易完成关闭时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置交易完成关闭时间
     *
     * @param endTime 交易完成关闭时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }
}