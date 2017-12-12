package com.seeu.shopper.product.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_stock_log", indexes = {
        @Index(name = "STOCKLOG_INDEX1", columnList = "specification_ids")
})
@DynamicUpdate
public class ProductStockLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "specification_ids", length = 30)
    private String specificationIds;

    /**
     * 0 - 入库
     * 1 - 出库
     */
    private Boolean type;

    private Integer amount;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作管理员id
     */
    @Column(name = "op_admin_id")
    private Integer opAdminId;

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

    public String getSpecificationIds() {
        return specificationIds;
    }

    public void setSpecificationIds(String specificationIds) {
        this.specificationIds = specificationIds;
    }

    /**
     * 获取0 - 入库
     * 1 - 出库
     *
     * @return type - 0 - 入库
     * 1 - 出库
     */
    public Boolean getType() {
        return type;
    }

    /**
     * 设置0 - 入库
     * 1 - 出库
     *
     * @param type 0 - 入库
     *             1 - 出库
     */
    public void setType(Boolean type) {
        this.type = type;
    }

    /**
     * @return amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
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
     * 获取操作管理员id
     *
     * @return op_admin_id - 操作管理员id
     */
    public Integer getOpAdminId() {
        return opAdminId;
    }

    /**
     * 设置操作管理员id
     *
     * @param opAdminId 操作管理员id
     */
    public void setOpAdminId(Integer opAdminId) {
        this.opAdminId = opAdminId;
    }
}