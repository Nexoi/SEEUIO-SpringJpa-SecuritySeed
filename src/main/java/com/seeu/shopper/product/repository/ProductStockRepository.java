package com.seeu.shopper.product.repository;

import com.seeu.shopper.product.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductStockRepository extends JpaRepository<ProductStock, String> {
    List<ProductStock> findAllByPid(@Param("pid") Long pid);

//    这不就是 findOne 吗
//    ProductStock findBySpecificationIds(@Param("specificationIds") String specificationIds);

    void deleteAllByPid(@Param("pid") Long pid);


    /* 入库 */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update ProductStock p set p.stock = p.stock + :amount where p.specificationIds = :specificationIds")
    void pushStock(@Param("specificationIds") String specificationIds, @Param("amount") Long amount);

    /* 出库 */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update ProductStock p set p.stock = p.stock - :amount where p.specificationIds = :specificationIds")
    void popStock(@Param("specificationIds") String specificationIds, @Param("amount") Long amount);
}