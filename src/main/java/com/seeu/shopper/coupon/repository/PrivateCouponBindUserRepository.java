package com.seeu.shopper.coupon.repository;

import com.seeu.shopper.coupon.model.privatecoupon.PrivateCouponBindUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrivateCouponBindUserRepository extends JpaRepository<PrivateCouponBindUser, Long> {

    Page findAllByUid(Pageable pageable, @Param("uid") Long uid);

    PrivateCouponBindUser findByIdAndUid(@Param("id") Long id, @Param("uid") Long uid);

    List<PrivateCouponBindUser> findAllByUidAndCid(@Param("uid") Long uid, @Param("cid") Long cid);
}