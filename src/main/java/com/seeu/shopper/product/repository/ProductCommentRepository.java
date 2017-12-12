package com.seeu.shopper.product.repository;

import com.seeu.shopper.product.model.ProductComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {
    List<ProductComment> findAllByPid(@Param("pid") Long pid);

    Page findAllByPid(@Param("pid") Long pid, Pageable pageable);
}