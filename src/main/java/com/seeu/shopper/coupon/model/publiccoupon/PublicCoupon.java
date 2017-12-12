package com.seeu.shopper.coupon.model.publiccoupon;

import com.seeu.shopper.coupon.model.COUPON_TYPE;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 5 位长度，公开使用
 * <p>
 * 不需要验证用户信息，每一个用户都可以使用
 */
@Entity
@Table(name = "coupon_public", indexes = {
        @Index(name = "COUPON_INDEX2", columnList = "available_field")
})
@DynamicUpdate
public class PublicCoupon {
    @Id
    @Column(name = "code", length = 5)
    private String code;

    @Column(name = "is_available")
    private Boolean isAvailable;

    private String name;

    private String imgUrl;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "start_price")
    private BigDecimal startPrice; // 起步价，低于此价格优惠券不生效

    @Column(name = "available_field")
    private Integer availableField; // 0 表示全场  1 表示某几个分类／品牌  2 表示某几件商品

    @OneToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "code")
    private List<PublicAvailableFieldIds> availableFieldIds; // 品牌ids ／ 商品ids
    /**
     * 满减
     * 打折
     */
    @Enumerated
    private COUPON_TYPE type;

    /**
     * 1. 满减额
     * 2. 打折比例（0.7 表示打七折）
     */
    private BigDecimal benefit;


    // 该数据为 0 的时候表示该优惠券终止
    private Integer restQuantity;  // 剩余可领取数量，初始化时应该与 totalQuantity 一致

    private Integer totalQuantity; // 总共张数

    @Column(name = "create_time")
    private Date createTime;

    public PublicCoupon() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return start_price
     */
    public BigDecimal getStartPrice() {
        return startPrice;
    }

    /**
     * @param startPrice
     */
    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public COUPON_TYPE getType() {
        return type;
    }

    public void setType(COUPON_TYPE type) {
        this.type = type;
    }

    /**
     * @return is_available
     */
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * @param isAvailable
     */
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getAvailableField() {
        return availableField;
    }

    public void setAvailableField(Integer availableField) {
        this.availableField = availableField;
    }


    public List<PublicAvailableFieldIds> getAvailableFieldIds() {
        return availableFieldIds;
    }

    public void setAvailableFieldIds(List<PublicAvailableFieldIds> availableFieldIds) {
        this.availableFieldIds = availableFieldIds;
    }

    public BigDecimal getBenefit() {
        return benefit;
    }

    public void setBenefit(BigDecimal benefit) {
        this.benefit = benefit;
    }

    public Integer getRestQuantity() {
        return restQuantity;
    }

    public void setRestQuantity(Integer restQuantity) {
        this.restQuantity = restQuantity;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}