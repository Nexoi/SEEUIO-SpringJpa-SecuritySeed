package com.seeu.shopper.product.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_comment", indexes = {
        @Index(name = "PRODUCTCOMMENT_INDEX1", columnList = "pid")
})
@DynamicUpdate
public class ProductComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pid;

    @Column(name = "father_id")
    private Integer fatherId;

    private Integer uid;

    private String name; // 用户名

    private BigDecimal star;

    @Column(name = "content_html")
    private String contentHtml;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "is_reply")
    private Boolean isReply;

    @Column(name = "create_date")
    private Date createDate;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "father_id") // 根据 comment 的 id 去查询对应子评论集合
    private List<ProductComment> chlidComment = new ArrayList<>();

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return father_id
     */
    public Integer getFatherId() {
        return fatherId;
    }

    /**
     * @param fatherId
     */
    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
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

    /**
     * @return star
     */
    public BigDecimal getStar() {
        return star;
    }

    /**
     * @param star
     */
    public void setStar(BigDecimal star) {
        this.star = star;
    }

    /**
     * @return content_html
     */
    public String getContentHtml() {
        return contentHtml;
    }

    /**
     * @param contentHtml
     */
    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    /**
     * @return is_visible
     */
    public Boolean getIsVisible() {
        return isVisible;
    }

    /**
     * @param isVisible
     */
    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * @return is_reply
     */
    public Boolean getIsReply() {
        return isReply;
    }

    /**
     * @param isReply
     */
    public void setIsReply(Boolean isReply) {
        this.isReply = isReply;
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

    public List<ProductComment> getChlidComment() {
        return chlidComment;
    }

    public void setChlidComment(List<ProductComment> chlidComment) {
        this.chlidComment = chlidComment;
    }
}