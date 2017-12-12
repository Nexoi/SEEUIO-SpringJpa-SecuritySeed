package com.seeu.shopper.user.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_favorite", indexes = {
        @Index(name = "USERFAVORITE_INDEX1", unique = false, columnList = "pid")
})
@DynamicUpdate
public class UserFavorite {
    @Id
    private Long uid;

    private Long pid;

    @Column(name = "create_date")
    private Date createDate = new Date();

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