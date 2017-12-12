package com.seeu.shopper.api.shop.product;

import com.seeu.shopper.product.model.ProductCategory;
import com.seeu.shopper.product.repository.ProductCategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 09/12/2017
 * Time: 3:48 AM
 * Describe:
 */
@RestController
@RequestMapping("/api/shop/v2/product-category")
public class ProductCategoryApi {
    @Resource
    ProductCategoryRepository productCategoryRepository;

    @GetMapping
    public ResponseEntity listAll() {
        List<ProductCategory> categoryList = productCategoryRepository.findAllByFatherIdIsNull();
        return categoryList.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(categoryList);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        ProductCategory category = productCategoryRepository.findOne(id);
        return category == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(category);
    }
}
