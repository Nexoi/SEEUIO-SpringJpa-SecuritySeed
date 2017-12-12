package com.seeu.shopper.api.shop.product;

import com.seeu.shopper.currency.model.Currency;
import com.seeu.shopper.currency.repository.CurrencyRepository;
import com.seeu.shopper.currency.service.CurrencyConversionService;
import com.seeu.shopper.product.model.ProductStock;
import com.seeu.shopper.product.repository.ProductStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 09/12/2017
 * Time: 3:51 AM
 * Describe:
 */

@RestController
@RequestMapping("/api/shop/v2/product/{pid}/stocks")
public class StockApi {

    @Resource
    ProductStockRepository productStockRepository;
    @Resource
    CurrencyRepository currencyRepository;
    @Autowired
    CurrencyConversionService currencyConversionService;

    @GetMapping
    public ResponseEntity list(@PathVariable("pid") Long pid,
                               @RequestParam(defaultValue = "USD") String currency) {
        List<ProductStock> productStocks = productStockRepository.findAllByPid(pid);

        /* 转化成对应的货币价值 */
        Currency currencyModel = currencyRepository.findOne(currency);
        if (currencyModel != null) {
            // 存在该信息，所以转化，否则不予转化，默认 美元 $USD
            BigDecimal currencyWeight = currencyModel.getWeight();
            for (ProductStock stock : productStocks) {
                stock.setPrice(currencyConversionService.convert(currencyWeight.multiply(stock.getPrice())));
                stock.setOriginPrice(currencyConversionService.convert(currencyWeight.multiply(stock.getOriginPrice())));
            }
        }
        return productStocks.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(productStocks);
    }

    @GetMapping("/{specificationIds}")
    public ResponseEntity get(@PathVariable("pid") Long pid,
                              @PathVariable("specificationIds") String specificationIds,
                              @RequestParam(defaultValue = "USD") String currency) {
        ProductStock productStock = productStockRepository.findOne(specificationIds);

        /* 转化成对应的货币价值 */
        Currency currencyModel = currencyRepository.findOne(currency);
        if (currencyModel != null) {
            // 存在该信息，所以转化，否则不予转化，默认 美元 $USD
            BigDecimal currencyWeight = currencyModel.getWeight();
            productStock.setPrice(currencyConversionService.convert(currencyWeight.multiply(productStock.getPrice())));
            productStock.setOriginPrice(currencyConversionService.convert(currencyWeight.multiply(productStock.getOriginPrice())));
        }
        return productStock == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(productStock);
    }

}
