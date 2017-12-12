package com.seeu.shopper.api.admin.product;

import com.seeu.shopper.currency.model.Currency;
import com.seeu.shopper.currency.repository.CurrencyRepository;
import com.seeu.shopper.currency.service.CurrencyConversionService;
import com.seeu.shopper.product.dto.ProductStockList;
import com.seeu.shopper.product.model.ProductStock;
import com.seeu.shopper.product.model.ProductStockLog;
import com.seeu.shopper.product.repository.ProductRepository;
import com.seeu.shopper.product.repository.ProductStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 09/12/2017
 * Time: 1:47 AM
 * Describe:
 * <p>
 * 产品库存信息
 * <p>
 * 最初：产品库存会随着 规格（specification）改变而自动发生变化，而不由前端进行手动把控
 * update：产品规格由管理员自行 CRUD，并提供一个清除全部的功能，以满足前端管理员进行操作
 * <p>
 * 1. 获取某一个产品所有库存信息
 * [GET] /product/{pid}/stocks
 * <p>
 * 2. 获取某一个产品下某一个 specification组 的库存信息
 * [GET] /product/{pid}/stocks/{specificationIds}
 * <p>
 * 3. 更新某一个产品某一个（一个 sku = 一组 specification ）库存信息
 * [PUT] /product/{pid}/stocks/{specificationIds}
 * <p>
 * 4. 添加一条库存信息
 * [POST] /product/{pid}/stocks
 * <p>
 * 5. 添加一组库存信息
 * [POST] /product/{pid}/stocks/many
 * <p>
 * 6. 删除一条库存信息
 * [DELETE] /product/{pid}/stocks/{specificationIds}
 * <p>
 * 7. 清除所有库存信息
 * [DELETE] /product/{pid}/stocks
 * <p>
 * 8. 入库
 * [PATCH] /product/{pid}/stocks/{specificationIds}/push
 * <p>
 * 9. 出库
 * [PATCH] /product/{pid}/stocks/{specificationIds}/pop
 */

@RestController("adminStockApi")
@RequestMapping("/api/admin/v2/product/{pid}/stocks")
@PreAuthorize("hasRole('ADMIN')")
public class StockApi {

    @Resource
    ProductRepository productRepository;
    @Resource
    ProductStockRepository productStockRepository;
    @Resource
    CurrencyRepository currencyRepository;
    @Autowired
    CurrencyConversionService currencyConversionService;

    /* XX [放弃]>>产品库存信息，由于需要对应到不同的 specification，所以每次增加一个 specification 就更新一次 stock（sku）信息 */

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

    @PostMapping
    public ResponseEntity addStocks(@PathVariable("pid") Long pid,
                                    ProductStock productStock) {
        if (!productRepository.exists(pid))
            return ResponseEntity.badRequest().body("no such product found");
        productStock.setPid(pid);
        productStock.setUpdateDate(new Date());
        ProductStock stock = productStockRepository.saveAndFlush(productStock);
        return ResponseEntity.ok(stock);
    }

    /* 提供一次添加多组库存信息 */
    @PostMapping("/many")
    public ResponseEntity addStocksMany(@PathVariable("pid") Long pid,
                                        @Valid ProductStockList productStockList) {
        if (!productRepository.exists(pid))
            return ResponseEntity.badRequest().body("no such product found");
        List<ProductStock> stocks = productStockList.getProductStockList();
        if (stocks == null || stocks.size() == 0)
            return ResponseEntity.badRequest().body("please input at less 1 row data");
        for (ProductStock stock : stocks) {
            stock.setPid(pid);
            stock.setUpdateDate(new Date());
        }
        List<ProductStock> savedStocks = productStockRepository.save(stocks);
        return ResponseEntity.ok(savedStocks);
    }


    @PutMapping("/{specificationIds}")
    public ResponseEntity updateStocks(@PathVariable("pid") Long pid,
                                       @PathVariable("specificationIds") String specificationIds,
                                       ProductStock productStock) {
        if (productStockRepository.exists(specificationIds)) {
            productStock.setPid(null);
            productStock.setUpdateDate(new Date());
            ProductStock stock = productStockRepository.saveAndFlush(productStock);
            return ResponseEntity.ok(stock);
        }
        return ResponseEntity.badRequest().body("no such product stock found");
    }

    @DeleteMapping("/{specificationIds}")
    public ResponseEntity deleteStocks(@PathVariable("pid") Long pid,
                                       @PathVariable("specificationIds") String specificationIds) {
        productStockRepository.delete(specificationIds);
        return ResponseEntity.ok().body("delete success");
    }

    @DeleteMapping
    public ResponseEntity deleteStocksByProductId(@PathVariable("pid") Long pid) {
        productStockRepository.deleteAllByPid(pid);
        return ResponseEntity.ok().body("delete success");
    }

    /* 出库入库操作 */

    /**
     * 入库
     *
     * @param specificationIds
     * @param amount
     * @return
     */
    @PatchMapping("/{specificationIds}/push")
    public ResponseEntity pushStock(@PathVariable("specificationIds") String specificationIds,
                                    @RequestParam(defaultValue = "0") Long amount) {
        if (amount <= 0)
            return ResponseEntity.badRequest().body("amount should larger than 0");
        if (!productStockRepository.exists(specificationIds))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("no such stock information found");
        productStockRepository.pushStock(specificationIds, amount);
        return ResponseEntity.ok().body("push stock success, stock increased by " + amount);
    }

    /**
     * 出库
     *
     * @param specificationIds
     * @param amount
     * @return
     */
    @PatchMapping("/{specificationIds}/pop")
    public ResponseEntity popStock(@PathVariable("specificationIds") String specificationIds,
                                   @RequestParam(defaultValue = "0") Long amount) {
        if (amount <= 0)
            return ResponseEntity.badRequest().body("amount should larger than 0");
        // 看看库存是不是足够
        ProductStock stock = productStockRepository.findOne(specificationIds);
        if (stock == null || stock.getStock() < amount)
            return stock == null
                    ?
                    ResponseEntity.status(HttpStatus.NO_CONTENT).body("no such stock information found")
                    :
                    ResponseEntity.badRequest().body("amount was too large, must less than " + stock.getStock());
        productStockRepository.popStock(specificationIds, amount);
        return ResponseEntity.ok().body("push stock success, stock reduced by " + amount);
    }

}
