package com.seeu.shopper.product.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "product_attribute", indexes = {
        @Index(name = "PRODUCTATTR_INDEX1", columnList = "pid")
})
@DynamicUpdate
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pid;

    @Column(name = "attribute_value")
    private String attributeValue;

    @Column(name = "attribute_name")
    private String attributeName;

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
     * @return attribute_value
     */
    public String getAttributeValue() {
        return attributeValue;
    }

    /**
     * @param attributeValue
     */
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    /**
     * @return attribute_name
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * @param attributeName
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}