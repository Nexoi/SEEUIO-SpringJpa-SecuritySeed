package com.seeu.shopper.api.admin.product;

import com.seeu.shopper.currency.model.Currency;
import com.seeu.shopper.currency.repository.CurrencyRepository;
import com.seeu.shopper.currency.service.CurrencyConversionService;
import com.seeu.shopper.product.model.*;
import com.seeu.shopper.product.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 06/12/2017
 * Time: 2:31 PM
 * Describe:
 * <p>
 * 产品所有信息查询，必须是与产品有直接子包含关系的属性才能在此被查询到
 * <p>
 * 管理员专属，往后可以为之定义更多的权限分配
 * <p>
 * 管理员进行商品管理的 API
 * <p>
 * <p>
 * 1. 查询商品信息【关键词】【分页】
 * [GET] /product?word=xx&page=0&size=10
 * <p>
 * 2. 查询某一个商品所有信息（列出所有信息）【上架】
 * [GET] /product/{pid}
 * <p>
 * <p>
 * 3. 新建商品（只会创建 basic 信息）
 * [POST] /product
 * <p>
 * <p>
 * 3. 删除商品
 * [DELETE] /product/{pid}
 * <p>
 * <p>
 * 3. 某一个商品简要信息（BasicInfo）（商品必须在【上架】状态）
 * [GET] /product/{pid}/info
 * [PUT] /product/{pid}/info
 * [DELETE] /product/{pid}/info 【会删除所有与之商品有关的信息，等同于 [DELETE] /product/{pid} 】
 * <p>
 * <p>
 * 4. 某一个商品属性信息
 * [GET] /product/{pid}/attributes
 * [PUT] /product/{pid}/attributes/{id}
 * [POST] /product/{pid}/attributes
 * [DELETE] /product/{pid}/attributes/{id}
 * <p>
 * <p>
 * <p>
 * 5. 某一个商品评论信息
 * [GET] /product/{pid}/comments 【分页】
 * [PUT] /product/{pid}/comments/{id}
 * [POST] /product/{pid}/comments
 * [DELETE] /product/{pid}/comments/{id}
 * <p>
 * <p>
 * <p>
 * 6. 某一个商品图片组信息，删除其中某一张图片，增加新图片
 * [GET] /product/{pid}/pictures
 * [POST] /product/{pid}/pictures
 * [DELETE] /product/{pid}/pictures/{id}
 * <p>
 * <p>
 * 7. 某一个商品详情介绍信息（没有 POST 请求，已经在创建商品的时候添加）
 * [GET] /product/{pid}/introduce
 * [PUT] /product/{pid}/introduce
 * <p>
 * <p>
 * 8. 某一个商品规格信息
 * [GET] /product/{pid}/specification
 * [PUT] /product/{pid}/specification/{listId}        更新列表名称
 * [PUT] /product/{pid}/specification/{listId}/{id}   更新 specification 具体信息
 * [POST] /product/{pid}/specification                添加一组新的 specification      【常用】
 * [POST] /product/{pid}/specification/{listId}       添加新 specification 在该组下   【不常用】
 * [DELETE] /product/{pid}/specification/{listId}     删除一组                       【常用】
 * [DELETE] /product/{pid}/specification/{listId}/{id}    删除一个                   【不常用】
 * <p>
 * <p>
 * 9. 某一个商品库存信息（？？）
 * [已经全部转移至：StockApi]
 */
@RestController("adminProductApi")
@RequestMapping("/api/admin/v2/product")
@PreAuthorize("hasRole('ADMIN')")
public class ProductApi {
    @Resource
    ProductRepository productRepository;
    @Resource
    CurrencyRepository currencyRepository;
    @Autowired
    CurrencyConversionService currencyConversionService;

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
    ProductSpecificationRepository productSKURepository;
    @Resource
    ProductStockRepository productStockRepository;

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


    /**
     * 获取该商品所有信息
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
        List<ProductSpecificationList> productSpecificationLists = productSKUListRepository.findAllByPidOrderBySpecificationListId(pid);
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
        result.put("specificationList", productSpecificationLists);
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
        return product == null || product.getStatus() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(product);
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
        Page productComments = productCommentRepository.findAllByPid(pid, new PageRequest(page, size));
        return productComments.getTotalElements() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(productComments);
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
        List<ProductSpecificationList> productSpecificationLists = productSKUListRepository.findAllByPidOrderBySpecificationListId(pid);
        return productSpecificationLists.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(productSpecificationLists);
    }


    /**
     * 产品修改 API
     */

    @PutMapping("/{pid}/info")
    public ResponseEntity updateBasicInfo(@PathVariable("pid") Long pid, Product product) {
        if (productRepository.exists(pid)) {
            product.setUpdatedate(new Date());
            product.setPid(pid);
            Product product1 = productRepository.saveAndFlush(product);
            return ResponseEntity.ok(product1);
        }
        return ResponseEntity.badRequest().body("no such product found");
    }

    @PutMapping("/{pid}/attributes/{id}")
    public ResponseEntity updateAttributes(@PathVariable("pid") Long pid,
                                           @PathVariable("id") Long id,
                                           ProductAttribute productAttribute) {
        if (productAttributeRepository.exists(id)) {
            productAttribute.setId(id);
            productAttribute.setPid(null); // selective update
            ProductAttribute attribute = productAttributeRepository.saveAndFlush(productAttribute);
            return ResponseEntity.ok(attribute);
        }
        return ResponseEntity.badRequest().body("no such product attribute found");
    }

    @PutMapping("/{pid}/comments/{id}")
    public ResponseEntity updateComments(@PathVariable("pid") Long pid,
                                         @PathVariable("id") Long id,
                                         ProductComment productComment) {
        if (productCommentRepository.exists(id)) {
            productComment.setId(id);
            productComment.setChlidComment(null);
            productComment.setUid(null);
            productComment.setPid(null); // selective update
            ProductComment comment = productCommentRepository.saveAndFlush(productComment);
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.badRequest().body("no such product comment found");
    }

    @PutMapping("/{pid}/introduce")
    public ResponseEntity updateIntroduce(@PathVariable("pid") Long pid,
                                          ProductIntro productIntro) {
        if (productIntroRepository.exists(pid)) {
            productIntro.setUpdateDate(new Date());
            ProductIntro intro = productIntroRepository.saveAndFlush(productIntro);
            return ResponseEntity.ok(intro);
        }
        return ResponseEntity.badRequest().body("no such product introduce found");
    }

    @PutMapping("/{pid}/specification/{id}")
    public ResponseEntity updateSKUListName(@PathVariable("pid") Long pid,
                                            @PathVariable("id") Long id,
                                            @RequestParam String collectionName) {
        if (productSKUListRepository.exists(id)) {
            ProductSpecificationList list = new ProductSpecificationList();
            list.setSpecificationListId(id);
            list.setCollectionName(collectionName);
            ProductSpecificationList specificationList = productSKUListRepository.saveAndFlush(list);
            return ResponseEntity.ok(specificationList);
        }
        return ResponseEntity.badRequest().body("no such product specification collection found");
    }

    @PutMapping("/{pid}/specification/{listId}/{id}")
    public ResponseEntity updateSKUs(@PathVariable("pid") Long pid,
                                     @PathVariable("listId") Long listId,
                                     @PathVariable("id") Long id,
                                     ProductSpecification productSKU) {
        if (productSKURepository.exists(id)) {
            productSKU.setSpecificationListId(null);
            ProductSpecification specification = productSKURepository.saveAndFlush(productSKU);
            return ResponseEntity.ok(specification);
        }
        return ResponseEntity.badRequest().body("no such product specification found");
    }


    /**
     * POST 商品
     */

    /**
     * 仅创建基础信息（basic、intro），其余信息根据不同的请求进行创建
     **/
    @PostMapping
    public ResponseEntity addBasicInfo(Product product) {
        product.setUpdatedate(new Date());
        product.setPid(null);
        Product product1 = productRepository.saveAndFlush(product);
        ProductIntro intro = new ProductIntro();
        intro.setPid(product1.getPid());
        intro.setUpdateDate(new Date());
        productIntroRepository.save(intro);
        // ..
        return ResponseEntity.ok(product1);
    }

    @PostMapping("/{pid}/attributes")
    public ResponseEntity addAttributes(@PathVariable("pid") Long pid,
                                        ProductAttribute productAttribute) {
        if (!productRepository.exists(pid))
            return ResponseEntity.badRequest().body("no such product found");
        productAttribute.setId(null);
        productAttribute.setPid(pid); // selective update
        ProductAttribute attribute = productAttributeRepository.saveAndFlush(productAttribute);
        return ResponseEntity.ok(attribute);
    }

    @PostMapping("/{pid}/comments")
    public ResponseEntity addComments(@PathVariable("pid") Long pid,
                                      ProductComment productComment) {
        if (!productRepository.exists(pid))
            return ResponseEntity.badRequest().body("no such product found");
        productComment.setId(null);
        productComment.setChlidComment(null);
        productComment.setPid(pid);
        ProductComment comment = productCommentRepository.saveAndFlush(productComment);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/{pid}/pictures")
    public ResponseEntity addPictures(@PathVariable("pid") Long pid,
                                      ProductImg productImg) {
        if (!productRepository.exists(pid))
            return ResponseEntity.badRequest().body("no such product found");
        productImg.setPid(pid);
        productImg.setId(null);
        productImg.setUpdateTime(new Date());
        ProductImg img = productImgRepository.save(productImg);
        return ResponseEntity.ok(img);
    }

    @PostMapping("/{pid}/specification")
    public ResponseEntity addSKUListName(@PathVariable("pid") Long pid,
                                         ProductSpecificationList productSKUList) {
        if (!productRepository.exists(pid))
            return ResponseEntity.badRequest().body("no such product found");
        productSKUList.setPid(pid);
        productSKUList.setSpecificationListId(null);
        List<ProductSpecification> specificationList = productSKUList.getSpecificationList();
        if (specificationList == null)
            return ResponseEntity.badRequest().body("no specification can save");
        for (ProductSpecification specification : specificationList) {
            if (specification == null) continue;
            specification.setSpecificationId(null);
            specification.setSpecificationListId(null);
        }
        ProductSpecificationList productSKUList1 = productSKUListRepository.save(productSKUList);
        return ResponseEntity.ok(productSKUList1);
    }

    @PostMapping("/{pid}/specification/{listId}")
    public ResponseEntity addSKUs(@PathVariable("pid") Long pid,
                                  @PathVariable("listId") Long listId,
                                  ProductSpecification productSKU) {
        if (!productRepository.exists(pid))
            return ResponseEntity.badRequest().body("no such product found");
        if (!productSKUListRepository.exists(listId))
            return ResponseEntity.badRequest().body("no such product specification collection found");
        productSKU.setSpecificationListId(listId);
        productSKU.setSpecificationId(null);
        ProductSpecification specification = productSKURepository.save(productSKU);
        return ResponseEntity.ok(specification);
    }


    /**
     * 删除 商品信息
     */

    @DeleteMapping(value = {"/{pid}", "/{pid}/info"})
    public ResponseEntity deleteBasicInfo(@PathVariable("pid") Long pid) {
        productRepository.delete(pid);
        productIntroRepository.delete(pid);
        return ResponseEntity.ok().body("delete success");
    }

    @DeleteMapping("/{pid}/attributes/{id}")
    public ResponseEntity deleteAttributes(@PathVariable("pid") Long pid,
                                           @PathVariable("id") Long id) {
        productAttributeRepository.delete(id);
        return ResponseEntity.ok().body("delete success");
    }

    @DeleteMapping("/{pid}/comments/{id}")
    public ResponseEntity deleteComments(@PathVariable("pid") Long pid,
                                         @PathVariable("id") Long id) {
        productCommentRepository.delete(id);
        return ResponseEntity.ok().body("delete success");
    }

    @DeleteMapping("/{pid}/specification/{id}")
    public ResponseEntity deleteSKUListName(@PathVariable("pid") Long pid,
                                            @PathVariable("id") Long id) {
        ProductSpecificationList productSKUList = productSKUListRepository.findOne(pid);
        if (productSKUList == null)
            return ResponseEntity.badRequest().body("no such specification collection found");
        productSKUListRepository.delete(id);
        // 还得顺带删除底下所有的 specification 标签
        List<ProductSpecification> specificationList = productSKUList.getSpecificationList();
        productSKURepository.delete(specificationList);
        return ResponseEntity.ok().body("delete success");
    }

    @DeleteMapping("/{pid}/specification/{listId}/{id}")
    public ResponseEntity deleteSKUs(@PathVariable("pid") Long pid,
                                     @PathVariable("listId") Long listId,
                                     @PathVariable("id") Long id) {
        productSKURepository.delete(id);
        return ResponseEntity.ok().body("delete success");
    }


}
