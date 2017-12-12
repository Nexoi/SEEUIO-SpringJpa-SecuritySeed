package com.seeu.shopper.api.admin.product;

import com.seeu.shopper.product.model.ProductBanner;
import com.seeu.shopper.product.repository.ProductBannerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 06/12/2017
 * Time: 3:58 PM
 * Describe:
 * <p>
 * 商品 Banner 信息，一般放在商品展示列表页面每一个商品图片信息的左右上角处
 * 一般可设计为：打折、促销、优惠降价等等几种标志
 * <p>
 * 1. 获取所有 Banner 信息
 * [GET] /product-banner
 * <p>
 * 2. 获取某一条 Banner 信息
 * [GET] /product-banner/{id}
 * <p>
 * 3. 增加一条 Banner 信息
 * [POST] /product-banner
 * <p>
 * 4. 修改一条 Banner 信息
 * [PUT] /product-banner/{id}
 * <p>
 * 4. 删除一条 Banner 信息
 * [DELETE] /product-banner/{id}
 */
@RestController("adminProductBannerApi")
@RequestMapping("/api/admin/v2/product-banner")
@PreAuthorize("hasRole('ADMIN')")
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

    @PostMapping
    public ResponseEntity add(ProductBanner productBanner) {
        productBanner.setId(null);
        productBannerRepository.save(productBanner);
        return ResponseEntity.ok().body("save success");
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id,
                                 ProductBanner productBanner) {
        productBanner.setId(id);
        productBannerRepository.saveAndFlush(productBanner);
        return ResponseEntity.ok().body("update success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        productBannerRepository.delete(id);
        return ResponseEntity.ok().body("delete success");
    }
}
