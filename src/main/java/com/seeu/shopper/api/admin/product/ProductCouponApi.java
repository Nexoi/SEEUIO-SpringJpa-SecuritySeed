package com.seeu.shopper.api.admin.product;

import com.seeu.shopper.product.model.ProductCoupon;
import com.seeu.shopper.product.repository.ProductCouponRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 08/12/2017
 * Time: 5:37 AM
 * Describe:
 * <p>
 * 产品 - 优惠券 信息表
 * 该表描述了一个产品支持哪些优惠券，或者一个优惠券支持哪些产品
 * <p>
 * 1. 获取所有的信息 【分页】
 * [GET] /product-coupon
 * <p>
 * 2. 获取某一条产品-优惠券记录
 * [GET] /product-coupon/{id}
 * <p>
 * 3. 获取某产品所有的优惠券记录
 * [GET] /product-coupon/product/{pid}
 * <p>
 * 4. 获取某优惠券支持的所有产品记录
 * [GET] /product-coupon/coupon/{cid}
 * <p>
 * 5. 添加一条新记录
 * [POST] /product-coupon
 * <p>
 * 6. 添加一组新记录
 * 7. 删除某条记录
 * [DELETE] /product-coupon/{id}
 * <p>
 * 8. 删除某产品下所有记录
 * [DELETE] /product-coupon/product/{pid}
 * <p>
 * 9. 删除某优惠券支持的所有记录
 * [DELETE] /product-coupon/coupon/{cid}
 */

@RestController("adminProductCouponApi")
@RequestMapping("/api/admin/v2/product-coupon")
@PreAuthorize("hasRole('ADMIN')")
public class ProductCouponApi {
    @Resource
    ProductCouponRepository productCouponRepository;

    @GetMapping
    public ResponseEntity list(@RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "10") Integer size) {
        Page productCouponPage = productCouponRepository.findAll(new PageRequest(page, size));
        return productCouponPage.getTotalElements() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(productCouponPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        ProductCoupon productCoupon = productCouponRepository.findOne(id);
        return productCoupon == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(productCoupon);
    }

    @GetMapping("/product/{pid}")
    public ResponseEntity getProductList(@PathVariable("pid") Long pid) {
        List<ProductCoupon> productCoupons = productCouponRepository.findAllByPid(pid);
        return productCoupons.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(productCoupons);
    }

    @GetMapping("/coupon/{cid}")
    public ResponseEntity getCouponList(@PathVariable("cid") String cid) {
        List<ProductCoupon> productCoupons = productCouponRepository.findAllByCid(cid);
        return productCoupons.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(productCoupons);
    }

    @PostMapping
    public ResponseEntity add(@Valid ProductCoupon productCoupon) {
        productCoupon.setId(null);
        return ResponseEntity.ok(productCouponRepository.save(productCoupon));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        productCouponRepository.delete(id);
        return ResponseEntity.ok("delete success");
    }

    @DeleteMapping("/product/{pid}")
    public ResponseEntity deleteByProduct(@PathVariable("pid") Long pid) {
        productCouponRepository.deleteAllByPid(pid);
        return ResponseEntity.ok("delete success");
    }


    @DeleteMapping("/coupon/{cid}")
    public ResponseEntity deleteByCoupon(@PathVariable("cid") String cid) {
        productCouponRepository.deleteAllByCid(cid);
        return ResponseEntity.ok("delete success");
    }

}
