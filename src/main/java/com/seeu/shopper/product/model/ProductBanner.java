package com.seeu.shopper.product.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 目前信息就只有一张图片便于存储
 * <p>
 * 以后可以尝试在此添加更多详细的信息便于对商品分类。Product 类里面可以对 bannerId 建立索引进行更多维度的操作。
 */
@Entity
@Table(name = "product_banner")
@DynamicUpdate
public class ProductBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url; // 一张图片的地址

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
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}