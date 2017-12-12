package com.seeu.shopper.api.admin.product;

import com.seeu.shopper.product.model.ProductCategory;
import com.seeu.shopper.product.repository.ProductCategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 06/12/2017
 * Time: 3:58 PM
 * Describe:
 * <p>
 * 商品 Category 信息，一般为树状图
 * <p>
 * 1. 获取所有 Category 信息
 * [GET] /product-category
 * <p>
 * 2. 获取某一条 Category 信息
 * [GET] /product-category/{id}
 * <p>
 * 3. 增加一条全新的 Category 信息
 * [POST] /product-category
 * <p>
 * 4. 增加一条 Category 信息，挂在某一个分类下
 * [POST] /product-category/{fatherId}
 * <p>
 * 5. 修改一条 Category 信息
 * [PUT] /product-category/{id}
 * <p>
 * 6. 删除一条 Category 信息
 * [DELETE] /product-category/{id}
 */
@RestController("adminProductCategoryApi")
@RequestMapping("/api/admin/v2/product-category")
@PreAuthorize("hasRole('ADMIN')")
public class ProductCategoryApi {

    @Resource
    ProductCategoryRepository productCategoryRepository;

    @GetMapping
    public ResponseEntity listAll() {
        List<ProductCategory> categoryList = productCategoryRepository.findAllByFatherIdIsNull();
        return categoryList.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(categoryList);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        ProductCategory category = productCategoryRepository.findOne(id);
        return category == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity addNewTree(@Valid ProductCategory productCategory) {
        // 名字不能有重复
        ProductCategory category = productCategoryRepository.findFirstByName(productCategory.getName());
        if (category != null)
            return ResponseEntity.badRequest().body("name was repeated");
        productCategory.setId(null);
        productCategory.setFatherId(null);
        return ResponseEntity.ok().body(productCategoryRepository.save(productCategory));
    }

    @PostMapping("/{fatherId}")
    public ResponseEntity add(@PathVariable("fatherId") Long fatherId,
                              @Valid ProductCategory productCategory) {
        // 名字不能有重复
        ProductCategory category = productCategoryRepository.findFirstByName(productCategory.getName());
        if (category != null)
            return ResponseEntity.badRequest().body("name was repeated");
        // 该 father 得存在
        if (!productCategoryRepository.exists(fatherId))
            return ResponseEntity.badRequest().body("no such category id [" + fatherId + "] found");
        productCategory.setId(null);
        productCategory.setFatherId(fatherId);
        return ResponseEntity.ok().body(productCategoryRepository.save(productCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id,
                                 @Valid ProductCategory productCategory) {
        // 名字不能有重复
        ProductCategory category = productCategoryRepository.findFirstByName(productCategory.getName());
        if (category != null)
            return ResponseEntity.badRequest().body("name was repeated");
        productCategory.setId(id);
        return ResponseEntity.ok().body(productCategoryRepository.saveAndFlush(productCategory));
    }

    /**
     * 树下所有子类都会被删除
     **/
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        productCategoryRepository.delete(id);
        return ResponseEntity.ok().body("delete success");
    }
}
