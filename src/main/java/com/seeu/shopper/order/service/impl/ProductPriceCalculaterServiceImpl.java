package com.seeu.shopper.order.service.impl;

import com.seeu.shopper.order.dto.PriceCalculateItem;
import com.seeu.shopper.order.dto.PriceCalculateResult;
import com.seeu.shopper.order.service.ProductPriceCalculaterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 10/12/2017
 * Time: 1:24 AM
 * Describe:
 */
@Service
public class ProductPriceCalculaterServiceImpl implements ProductPriceCalculaterService {
    @Override
    public PriceCalculateResult calculate(List<PriceCalculateItem> productItems) {
        return null;
    }

    @Override
    public PriceCalculateResult calculateWithPrivateCoupon(List<PriceCalculateItem> productItems, String couponCode) {
        return null;
    }

    @Override
    public PriceCalculateResult calculateWithPublicCoupon(List<PriceCalculateItem> productItems, String couponCode) {
        return null;
    }
}
