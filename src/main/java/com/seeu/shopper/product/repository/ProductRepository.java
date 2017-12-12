package com.seeu.shopper.product.repository;

import com.seeu.shopper.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page findAllByKeywordLikeAndCurrentPriceGreaterThanEqualAndCurrentPriceLessThanEqualAndCategoryIdOrderBySalesDesc(
            Pageable pageable,
            @Param("keyword") String keyword,
            @Param("greater") BigDecimal greaterThan,
            @Param("less") BigDecimal lessThan,
            @Param("categoryId") Integer categoryId
    );
    Page findAllByKeywordLikeAndCurrentPriceGreaterThanEqualAndCurrentPriceLessThanEqualAndCategoryIdOrderByClickTimesDesc(
            Pageable pageable,
            @Param("keyword") String keyword,
            @Param("greater") BigDecimal greaterThan,
            @Param("less") BigDecimal lessThan,
            @Param("categoryId") Integer categoryId
    );
    Page findAllByKeywordLikeAndCurrentPriceGreaterThanEqualAndCurrentPriceLessThanEqualAndCategoryIdOrderByClickTimesAsc(
            Pageable pageable,
            @Param("keyword") String keyword,
            @Param("greater") BigDecimal greaterThan,
            @Param("less") BigDecimal lessThan,
            @Param("categoryId") Integer categoryId
    );
}