package com.seeu.shopper.order.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_log", indexes = {
        @Index(name = "ORDERLOG_INDEX1", columnList = "oid")
})
public class OrderLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "oid", length = 20)
    private String oid;

    /**
     * 1 ROLE_USER
     * 2 ROLE_ADMIN
     */
    @Column(name = "user_type")
    private Integer userType;  // 用户角色表 id

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    private String type;

    private String detail;

    @Column(name = "create_date")
    private Date createDate;

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
     * 获取1 user
     * 2 admin
     *
     * @return user_type - 1 user
     * 2 admin
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置1 user
     * 2 admin
     *
     * @param userType 1 user
     *                 2 admin
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}