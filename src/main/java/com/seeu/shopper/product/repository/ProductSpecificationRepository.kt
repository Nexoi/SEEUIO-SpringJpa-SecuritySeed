package com.seeu.shopper.product.repository

import com.seeu.shopper.product.model.ProductSpecification
import com.seeu.shopper.product.model.ProductSpecificationList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 29/11/2017
 * Time: 2:46 PM
 * Describe:
 */


interface ProductSpecificationListRepository : JpaRepository<ProductSpecificationList, Long> {
    fun findAllByPidOrderBySpecificationListId(@Param("pid") pid: Long): List<ProductSpecificationList>

}

interface ProductSpecificationRepository : JpaRepository<ProductSpecification, Long> {
}