package net.avalon.goodsdemo.dao.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.avalon.goodsdemo.mapper.generator.po.OnSalePo;
import net.avalon.goodsdemo.mapper.generator.po.ProductPo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/21 - 23:13
 */
@Data
@NoArgsConstructor
public class Product {

    private Long id;
    /**
     * 其他相关商品
     */
    private List<Product> otherProduct = new ArrayList<>();
    /**
     *
     */
    private List<OnSale> onSaleList = new ArrayList<>();
    private String skuSn;

    private String name;

    private Long originalPrice;

    private Long weight;

    private String imageUrl;

    private String barcode;

    private String unit;

    private String originPlace;

    /**
     * 创建者
     */
    private User creator;

    /**
     * 修改者
     */
    private User modifier;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;


    /**
     * po -> bo
     * otherProduct 未赋值
     * onSaleList 未赋值
     * @param po
     */
    public Product(ProductPo po) {
        assert null!=po;
        id = po.getId();
        skuSn = po.getSkuSn();
        name = po.getName();
        originalPrice = po.getOriginalPrice();
        originPlace = po.getOriginPlace();
        weight = po.getWeight();
        imageUrl = po.getImageUrl();
        barcode = po.getBarcode();
        unit = po.getUnit();
        creator = new User(po.getCreatorId(), po.getCreatorName());
        modifier = new User(po.getModifierId(), po.getModifierName());
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }

    /**
     * bo -> po
     * @return
     */
    public ProductPo createPo(){
        ProductPo productPo = new ProductPo();
        productPo.setId(id);
        productPo.setSkuSn(skuSn);
        productPo.setName(name);
        productPo.setOriginalPrice(originalPrice);
        productPo.setWeight(weight);
        productPo.setImageUrl(imageUrl);
        productPo.setBarcode(barcode);
        productPo.setUnit(unit);
        return productPo;
    }

    /**
     * 添加其他相关商品
     * polist -> bolist
     * @param poList
     */
    public void addOtherProduct(List<ProductPo> poList){
        for (ProductPo productPo : poList){
            if (productPo.getId().equals(this.id)) {
                continue;
            }
            this.otherProduct.add(new Product(productPo));
        }
    }

    public void addOnSale(List<OnSalePo> poList){
        for (OnSalePo onSalePo : poList){
            this.onSaleList.add(new OnSale(onSalePo));
        }
    }
}
