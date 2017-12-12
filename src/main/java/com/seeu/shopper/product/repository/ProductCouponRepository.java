package com.seeu.shopper.product.repository;

import com.seeu.shopper.product.model.ProductCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductCouponRepository extends JpaRepository<ProductCoupon, Long> {
    List<ProductCoupon> findAllByPid(@Param("pid") Long pid);

    List<ProductCoupon> findAllByCid(@Param("cid") String cid);

    void deleteAllByPid(@Param("pid") Long pid);

    void deleteAllByCid(@Param("cid") String cid);
}