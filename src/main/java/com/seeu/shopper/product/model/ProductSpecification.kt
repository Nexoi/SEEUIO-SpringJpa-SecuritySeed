package com.seeu.shopper.product.model

import org.hibernate.annotations.DynamicUpdate
import java.util.ArrayList
import javax.persistence.*

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 29/11/2017
 * Time: 2:35 PM
 * Describe:
 *
 * DEMO:
 *
 *   Color  :  Blue,    Red,   Green,  Black,  White
 * [listId] : [spId1],[spId2],[spId3],[spId4],[spId5]
 *
 */

@Entity
@Table(name = "product_specification_list",
        indexes = arrayOf(
                Index(name = "SKU_INDEX1", columnList = "pid")
        )
)
@DynamicUpdate
class ProductSpecificationList(
        @Id @GeneratedValue
        var specificationListId: Long? = null,
        var pid: Long? = null,
        @Column(length = 45, nullable = false)
        var collectionName: String? = null,

        @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
        @JoinColumn(name = "specification_list_id") // 根据 list 的 id 去查询对应评论集合
        @OrderBy("specificationId DESC")
        var specificationList: List<ProductSpecification>? = ArrayList<ProductSpecification>()
)

@Entity
@Table(name = "product_specification")
@DynamicUpdate
data class ProductSpecification(
        @Column(name = "specification_list_id")
        var specificationListId: Long? = null, // 外键关联
        @Id @GeneratedValue
        var specificationId: Long? = null,
        @Column(length = Int.MAX_VALUE, nullable = false)
        var specificationName: String? = null
)