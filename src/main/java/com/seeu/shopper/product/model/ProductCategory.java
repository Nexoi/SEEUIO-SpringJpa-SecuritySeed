package com.seeu.shopper.product.model;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_category", indexes = {
        @Index(name = "CATE_INDEX1", columnList = "name")
})
@DynamicUpdate
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * default is -1
     */
    @Column(name = "father_id")
    private Long fatherId;

    @NotNull
    @Length(max = 20, message = "category length cloud not be larger than 20")
    @Column(length = 20)
    private String name;

    private String detail;

    public List<ProductCategory> getChlidCategory() {
        return chlidCategory;
    }

    public void setChlidCategory(List<ProductCategory> chlidCategory) {
        this.chlidCategory = chlidCategory;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "father_id") // 根据 category 的 id 去查询对应子集合
    private List<ProductCategory> chlidCategory = new ArrayList<>();

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
     * 获取default is -1
     *
     * @return father_id - default is -1
     */
    public Long getFatherId() {
        return fatherId;
    }

    /**
     * 设置default is -1
     *
     * @param fatherId default is -1
     */
    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
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
}