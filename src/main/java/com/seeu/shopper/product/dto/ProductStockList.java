package com.seeu.shopper.product.dto;

import com.seeu.shopper.product.model.ProductStock;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 09/12/2017
 * Time: 2:42 AM
 * Describe:
 * <p>
 * 用于 StockApi#addStocksMany() 方便增添一组库存数据
 */

public class ProductStockList {
    @NotNull
    @NotEmpty(message = "product stock list can not be empty")
    private List<ProductStock> productStockList;

    public List<ProductStock> getProductStockList() {
        return productStockList;
    }

    public void setProductStockList(List<ProductStock> productStockList) {
        this.productStockList = productStockList;
    }
}
