package com.seeu.shopper.product.repository;

import com.seeu.shopper.product.model.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
    List<ProductImg> findAllByPidOrderByImgOrder(@Param("pid") Long pid);
}