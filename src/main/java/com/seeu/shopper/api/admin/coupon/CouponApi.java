package com.seeu.shopper.api.admin.coupon;

import com.seeu.shopper.coupon.repository.PrivateCouponRepository;
import com.seeu.shopper.coupon.repository.PublicCouponRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 10/12/2017
 * Time: 5:00 AM
 * Describe:
 */
@RestController("adminCouponApi")
@RequestMapping("/api/admin/v2/coupon")
@PreAuthorize("hasRole('ADMIN')")
public class CouponApi {

    @Resource
    PublicCouponRepository publicCouponRepository;
    @Resource
    PrivateCouponRepository privateCouponRepository;

    @GetMapping("/public")
    public ResponseEntity listPublicAll(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size) {
        Page couponPage = publicCouponRepository.findAll(new PageRequest(page, size));
        return couponPage.getTotalElements() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(couponPage);
    }

    @GetMapping("/private")
    public ResponseEntity listPrivateAll(@RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size) {
        Page couponPage = privateCouponRepository.findAll(new PageRequest(page, size));
        return couponPage.getTotalElements() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(couponPage);
    }
}
