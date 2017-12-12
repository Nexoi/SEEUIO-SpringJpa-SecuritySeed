package com.seeu.shopper.order.dto

import com.seeu.shopper.product.model.ProductSpecification
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Min

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 10/12/2017
 * Time: 1:33 AM
 * Describe:
 */

enum class PRICE_CALCULATE_STATUS {
    SUCCESS, // 计价成功
    SUCCESS_COUPON_PRIVATE, // 计价成功，优惠券被消费 20 位私有码
    SUCCESS_COUPON_PUBLIC, // 计价成功，优惠券被消费 5 位公开码
    FAILURE, // 计价失败
    FAILURE_NO_PRODUCT, // 计价失败，无此商品信息
    FAILURE_NO_STOCKINFO, // 计价失败，无库存（规格）信息（不是库存不够，是没有这个规格信息）
    FAILURE_COUPON_ERROR, // 计价失败，优惠券信息有误
    FAILURE_COUPON_BEFORE_TIME, // 计价失败，未到优惠券使用期间
    FAILURE_COUPON_OUT_TIME, // 计价失败，优惠券过期
    FAILURE_COUPON_NOT_AVAILABLE  // 计价失败，优惠券不可使用，未开启
}

class PriceCalculateResult(
        var status: PRICE_CALCULATE_STATUS? = null,
        @NotNull
        var productList: List<PriceCalculateItem>? = null,
        var showPrice: BigDecimal? = null,
        var transactionPrice: BigDecimal? = null,

        var couponId: String? = null,
        var couponCode: String? = null,
        var couponType: Int? = null,

        var weight: BigDecimal? = null,
        var shipPrice: BigDecimal? = null,

        var totalPrice: BigDecimal? = null
)

class PriceCalculateItem(
        @NotNull
        var pid: Long? = null,
        @NotNull
        var sku: String? = null, // specificationIds, like: 34.65.77.91
        @NotNull
        @Min(1)
        var quantity: Long? = null
)