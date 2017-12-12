package com.seeu.shopper.currency.repository;

import com.seeu.shopper.currency.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 28/11/2017
 * Time: 12:59 AM
 * Describe:
 */
public interface CurrencyRepository extends JpaRepository<Currency, String> {
    
}
