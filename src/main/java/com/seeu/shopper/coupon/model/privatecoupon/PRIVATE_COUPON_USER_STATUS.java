package com.seeu.shopper.coupon.model.privatecoupon;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 10/12/2017
 * Time: 3:16 AM
 * Describe:
 */

public enum PRIVATE_COUPON_USER_STATUS {
    WAIT_FOR_USE,    // 未使用，等待使用
    WAIT_FOR_PAY,    // 已经在使用，等待支付
    PAID_USED,       // 支付完成，表示已经使用；若支付取消，需要回滚到最初状态
    OUT_TIME;        // 过期
}
