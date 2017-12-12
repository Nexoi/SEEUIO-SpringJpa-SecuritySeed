package com.seeu.shopper.coupon.repository;

import com.seeu.shopper.coupon.model.privatecoupon.PrivateCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 10/12/2017
 * Time: 4:53 AM
 * Describe:
 */

public interface PrivateCouponRepository extends JpaRepository<PrivateCoupon, Long> {
}
