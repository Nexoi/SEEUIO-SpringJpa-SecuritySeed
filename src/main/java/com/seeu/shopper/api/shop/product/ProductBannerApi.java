package com.seeu.shopper.api.shop.product;

import com.seeu.shopper.product.model.ProductBanner;
import com.seeu.shopper.product.repository.ProductBannerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 09/12/2017
 * Time: 3:47 AM
 * Describe:
 */

@RestController
@RequestMapping("/api/shop/v2/product-banner")
public class ProductBannerApi {
    @Resource
    ProductBannerRepository productBannerRepository;

    @GetMapping
    public ResponseEntity listAll() {
        return ResponseEntity.ok(productBannerRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        ProductBanner banner = productBannerRepository.findOne(id);
        return banner == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(banner);
    }
}
