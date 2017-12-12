package com.seeu.shopper.api.shop.user;

import com.seeu.shopper.coupon.model.privatecoupon.PrivateCouponBindUser;
import com.seeu.shopper.coupon.repository.PrivateCouponBindUserRepository;
import com.seeu.shopper.user.model.UserLogin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 26/11/2017.
 * <p>
 * 用户优惠券
 * <p>
 * 1. 查看自己的优惠券列表【分页】【本用户】
 * [GET] /user/coupon
 * <p>
 * 2. 查看该用户拥有该 cid 的所有优惠券信息【本用户】
 * [GET] /user/coupon/{cid}
 * <p>
 * 3. 查看某一条优惠券信息【本用户】（cid 可缺省为 0）
 * [GET] /user/coupon/{cid}/{id}
 */
@RestController
@RequestMapping("/api/shop/v2/user/coupon")
public class UserCouponApi {

    @Resource
    PrivateCouponBindUserRepository privateCouponBindUserRepository;

    /**
     * 查看自己的优惠券列表【分页】【本用户】
     *
     * @param authUser
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity listMineAll(@AuthenticationPrincipal UserLogin authUser,
                                      @RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size) {
        Page userCouponPage = privateCouponBindUserRepository.findAllByUid(new PageRequest(page, size), authUser.getUid());
        return userCouponPage.getTotalElements() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(userCouponPage);
    }

    /**
     * 查看该用户拥有该 cid 的所有优惠券信息【本用户】
     *
     * @param authUser
     * @param couponId
     * @return
     */
    @GetMapping("/{couponId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity listMine(@AuthenticationPrincipal UserLogin authUser,
                                   @PathVariable("couponId") Long couponId) {
        List<PrivateCouponBindUser> userCouponList = privateCouponBindUserRepository.findAllByUidAndCid(authUser.getUid(), couponId);
        return userCouponList.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(userCouponList);
    }

    /**
     * 查看某一条优惠券信息【本用户】（cid 可缺省为 0）
     *
     * @param authUser
     * @param couponId
     * @param id
     * @return
     */
    @GetMapping("/{couponId}/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getMine(@AuthenticationPrincipal UserLogin authUser,
                                  @PathVariable("couponId") Long couponId,
                                  @PathVariable("id") Long id) {
        PrivateCouponBindUser userCoupon = privateCouponBindUserRepository.findByIdAndUid(id, authUser.getUid());
        return userCoupon == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(userCoupon);
    }
}
