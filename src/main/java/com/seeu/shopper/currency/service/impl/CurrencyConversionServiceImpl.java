package com.seeu.shopper.currency.service.impl;

import com.seeu.shopper.currency.service.CurrencyConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 10/12/2017
 * Time: 4:42 AM
 * Describe:
 * <p>
 * 统一保留精度为 2 位，进一法
 */
@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {
    @Override
    public BigDecimal convert(BigDecimal oldValue) {
        if (oldValue == null)
            return null;
        return oldValue.setScale(2, BigDecimal.ROUND_UP);
    }
}
