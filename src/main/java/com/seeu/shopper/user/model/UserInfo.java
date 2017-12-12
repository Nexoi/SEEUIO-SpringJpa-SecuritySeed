package com.seeu.shopper.user.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "user_info")
@DynamicUpdate
public class UserInfo {
    @Id
    private Long uid;

    @Column(name = "user_name")
    private String userName;

    /**
     * 1 famale
0 male
-1 unknow
     */
    private Integer gender;

    private String email;

    private String phone;


    private BigDecimal amount;

    @Column(name = "create_date")
    private Date createDate;

    /**
     * @return uid
     */
    public Long getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Long uid) {
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
     * 获取1 famale
0 male
-1 unknow
     *
     * @return gender - 1 famale
0 male
-1 unknow
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 设置1 famale
0 male
-1 unknow
     *
     * @param gender 1 famale
0 male
-1 unknow
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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