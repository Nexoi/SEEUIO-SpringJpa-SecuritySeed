package com.seeu.shopper.currency.service;

import java.math.BigDecimal;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 10/12/2017
 * Time: 4:41 AM
 * Describe:
 */

public interface CurrencyConversionService {
    BigDecimal convert(BigDecimal oldValue);
}
