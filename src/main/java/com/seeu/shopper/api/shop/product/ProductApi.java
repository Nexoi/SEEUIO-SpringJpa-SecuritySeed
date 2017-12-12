package com.seeu.shopper.api.shop.product;

import com.seeu.shopper.currency.model.Currency;
import com.seeu.shopper.currency.repository.CurrencyRepository;
import com.seeu.shopper.currency.service.CurrencyConversionService;
import com.seeu.shopper.product.model.*;
import com.seeu.shopper.product.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 27/11/2017
 * Time: 5:16 PM
 * Describe:
 * <p>
 * <p>
 * 1. 查询商品信息【关键词】【分页】
 * [GET] /product?word=xx&page=0&size=10
 * <p>
 * 2. 查询某一个商品所有信息（列出所有信息）【上架】
 * [GET] /product/{pid}
 * update 2017/12/09 增加 currency 信息，用于单位转换
 * <p>
 * <p>
 * <p>
 * 3. 查询某一个商品简要信息（商品必须在【上架】状态）
 * [GET] /product/{pid}/info
 * <p>
 * 4. 查询某一个商品属性信息
 * [GET] /product/{pid}/attribute
 * <p>
 * 5. 查询某一个商品评论信息
 * [GET] /product/{pid}/comment 【分页】
 * <p>
 * 6. 查询某一个商品图片组信息
 * [GET] /product/{pid}/pictures
 * <p>
 * 7. 查询某一个商品详情介绍信息
 * [GET] /product/{pid}/introduce
 * <p>
 * 8. 查询某一个商品规格信息
 * [GET] /product/{pid}/specification
 * <p>
 */
@RestController
@RequestMapping("/api/shop/v2/product")
public class ProductApi {
    @Resource
    ProductRepository productRepository;
    @Resource
    CurrencyRepository currencyRepository;
    @Autowired
    CurrencyConversionService currencyConversionService;

    /**
     * 根据条件搜索商品信息，可以做单位换算（USD->CNY | USD->CAD ..）【分页】
     *
     * @param word
     * @param category
     * @param currency
     * @param sort
     * @param desc
     * @param start
     * @param end
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public ResponseEntity list(@RequestParam(defaultValue = "") String word,
                               @RequestParam(defaultValue = "0.00") BigDecimal start,
                               @RequestParam(defaultValue = "99999999.99") BigDecimal end,
                               @RequestParam(defaultValue = "0") Integer category,  // category id
                               @RequestParam(defaultValue = "USD") String currency, // like. USD
                               @RequestParam(defaultValue = "sale") String sort,   // sort column
                               @RequestParam(defaultValue = "true") Boolean desc,   // sort desc
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "10") Integer size) {
        Page productPage;
        word = "%" + word + "%";
        switch (sort) {
            case "sale":
                productPage = productRepository.findAllByKeywordLikeAndCurrentPriceGreaterThanEqualAndCurrentPriceLessThanEqualAndCategoryIdOrderBySalesDesc(
                        new PageRequest(page, size),
                        word,
                        start,
                        end,
                        category
                );
                break;
            case "hot":
                productPage = desc
                        ?
                        productRepository.findAllByKeywordLikeAndCurrentPriceGreaterThanEqualAndCurrentPriceLessThanEqualAndCategoryIdOrderByClickTimesDesc(
                                new PageRequest(page, size),
                                word,
                                start,
                                end,
                                category)
                        :
                        productRepository.findAllByKeywordLikeAndCurrentPriceGreaterThanEqualAndCurrentPriceLessThanEqualAndCategoryIdOrderByClickTimesDesc(
                                new PageRequest(page, size),
                                word,
                                start,
                                end,
                                category
                        );
                break;
            default:
                productPage = productRepository.findAllByKeywordLikeAndCurrentPriceGreaterThanEqualAndCurrentPriceLessThanEqualAndCategoryIdOrderBySalesDesc(
                        new PageRequest(page, size),
                        word,
                        start,
                        end,
                        category
                );
                break;
        }
        /* 转化成对应的货币价值 */
        Currency currencyModel = currencyRepository.findOne(currency);
        if (currencyModel != null) {
            // 存在该信息，所以转化，否则不予转化，默认 美元 $USD
            BigDecimal currencyWeight = currencyModel.getWeight();
            List<Product> productList = productPage.getContent();
            for (Product product : productList) {
                if (product == null) continue;
                product.setCurrentPrice(currencyConversionService.convert(currencyWeight.multiply(product.getCurrentPrice())));
                product.setOriginPrice(currencyConversionService.convert(currencyWeight.multiply(product.getOriginPrice())));
                product.setCurrency(currencyModel.getCurrency());
            }
        }
        return productPage.getTotalElements() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(productPage);
    }


    @Resource
    ProductAttributeRepository productAttributeRepository;
    @Resource
    ProductCommentRepository productCommentRepository;
    @Resource
    ProductImgRepository productImgRepository;
    @Resource
    ProductIntroRepository productIntroRepository;
    @Resource
    ProductSpecificationListRepository productSKUListRepository;
    @Resource
    ProductStockRepository productStockRepository;

    /**
     * 获取该商品所有信息
     * <p>
     * update 2017/12/09 增加 currency 信息，用于单位转换
     *
     * @param pid
     * @return
     */
    @GetMapping("/{pid}")
    public ResponseEntity getAll(@PathVariable("pid") Long pid,
                                 @RequestParam(defaultValue = "USD") String currency) {
        Product product = productRepository.findOne(pid);
        if (product == null)
            return ResponseEntity.noContent().build();
        List<ProductAttribute> productAttributes = productAttributeRepository.findAllByPid(pid);
        List<ProductComment> productComments = productCommentRepository.findAllByPid(pid);
        List<ProductImg> productImgs = productImgRepository.findAllByPidOrderByImgOrder(pid);
        ProductIntro productIntro = productIntroRepository.findOne(pid);
        List<ProductSpecificationList> productSKULists = productSKUListRepository.findAllByPidOrderBySpecificationListId(pid);
        List<ProductStock> productStocks = productStockRepository.findAllByPid(pid);

        /* 转化成对应的货币价值 */
        Currency currencyModel = currencyRepository.findOne(currency);
        if (currencyModel != null) {
            // 存在该信息，所以转化，否则不予转化，默认 美元 $USD
            BigDecimal currencyWeight = currencyModel.getWeight();
            product.setCurrentPrice(currencyConversionService.convert(currencyWeight.multiply(product.getCurrentPrice())));
            product.setOriginPrice(currencyConversionService.convert(currencyWeight.multiply(product.getOriginPrice())));
            product.setCurrency(currencyModel.getCurrency());
            for (ProductStock stock : productStocks) {
                stock.setPrice(currencyConversionService.convert(currencyWeight.multiply(stock.getPrice())));
                stock.setOriginPrice(currencyConversionService.convert(currencyWeight.multiply(stock.getOriginPrice())));
            }
        }

        Map result = new HashMap();
        result.put("info", product);
        result.put("attribute", productAttributes);
        result.put("comments", productComments);
        result.put("pictures", productImgs);
        result.put("specificationList", productSKULists);
        result.put("stockList", productStocks);
        result.put("introduce", productIntro);
        return ResponseEntity.ok().body(result);
    }


    /**
     * 查询某一件商品简要信息
     *
     * @param pid
     * @return
     */
    @GetMapping("/{pid}/info")
    public ResponseEntity getBasicInfo(@PathVariable("pid") Long pid) {
        Product product = productRepository.findOne(pid);
        // 商品下架状态为 0，上架状态为 1
        return product == null || product.getStatus() == 0 ? ResponseEntity.notFound().build() : ResponseEntity.ok(product);
    }

    @GetMapping("/{pid}/attributes")
    public ResponseEntity getAttributes(@PathVariable("pid") Long pid) {
        List<ProductAttribute> productAttributes = productAttributeRepository.findAllByPid(pid);
        return productAttributes.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(productAttributes);
    }

    @GetMapping("/{pid}/comments")
    public ResponseEntity getComments(@PathVariable("pid") Long pid,
                                      @RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size) {
        Page pageComment = productCommentRepository.findAllByPid(pid, new PageRequest(page, size));
        return pageComment.getTotalElements() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(pageComment);
    }

    @GetMapping("/{pid}/pictures")
    public ResponseEntity getPictures(@PathVariable("pid") Long pid) {
        List<ProductImg> productImgs = productImgRepository.findAllByPidOrderByImgOrder(pid);
        return productImgs.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(productImgs);
    }

    @GetMapping("/{pid}/introduce")
    public ResponseEntity getIntroduce(@PathVariable("pid") Long pid) {
        ProductIntro productIntro = productIntroRepository.findOne(pid);
        return productIntro == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(productIntro);
    }

    @GetMapping("/{pid}/specifications")
    public ResponseEntity getSKUs(@PathVariable("pid") Long pid) {
        List<ProductSpecificationList> productSKULists = productSKUListRepository.findAllByPidOrderBySpecificationListId(pid);
        return productSKULists.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(productSKULists);
    }


}
