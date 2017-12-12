package com.seeu.shopper.order.service;

import com.seeu.shopper.order.dto.PriceCalculateItem;
import com.seeu.shopper.order.dto.PriceCalculateResult;

import java.util.List;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 09/12/2017
 * Time: 6:16 AM
 * Describe:
 * <p>
 * 商品价格计算器
 * <p>
 * 支持：
 * 1. 普通计价
 * 2. 优惠券计价（PRIVATE 20 位码计价）
 * 3. 优惠券计价（PUBLIC 5 位码计价）
 * 3. 全场设定打折／促销计价【未开发】
 */
public interface ProductPriceCalculaterService {
    PriceCalculateResult calculate(List<PriceCalculateItem> productItems);

    PriceCalculateResult calculateWithPrivateCoupon(List<PriceCalculateItem> productItems, String couponCode);

    PriceCalculateResult calculateWithPublicCoupon(List<PriceCalculateItem> productItems, String couponCode);
}
