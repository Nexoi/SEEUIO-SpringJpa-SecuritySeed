package com.seeu.shopper.coupon.repository;

import com.seeu.shopper.coupon.model.publiccoupon.PublicCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 10/12/2017
 * Time: 4:53 AM
 * Describe:
 */

public interface PublicCouponRepository extends JpaRepository<PublicCoupon, String> {
//    这不就是 findOne 吗
//    PublicCoupon findByCode(@Param("code") String code);
}
