package net.avalon.goodsdemo.controller.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.avalon.goodsdemo.dao.bo.Product;
import net.avalon.goodsdemo.dao.bo.User;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/22 - 23:50
 */
@Data
@NoArgsConstructor
public class ProductVo {

    private Long id;

    private String skuSn;

    @NotBlank(message = "商品名称不能为空")
    private String name;

    @Min(value = 0,message = "原价不能为负数")
    private Long originalPrice;

    @Min(value = 0,message = "重量不能为负数")
    private Long weight;

    private String imageUrl;

    private String barcode;

    private String unit;

    private String originPlace;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private User creator;

    private User modifier;


    /**
     * 由Vo对象创建Goods对象
     * @return Goods对象
     */
    public Product createBo(){
        Product product = new Product();
        product.setName(this.name);
        product.setOriginalPrice(this.originalPrice);
        product.setWeight(this.weight);
        product.setImageUrl(this.imageUrl);
        product.setBarcode(this.barcode);
        product.setUnit(this.unit);
        product.setOriginPlace(this.originPlace);
        return product;
    }

    public ProductVo(Product product) {
        this.id = product.getId();
        this.skuSn = product.getSkuSn();
        this.name = product.getName();
        this.unit = product.getUnit();
        this.originalPrice = product.getOriginalPrice();
        this.barcode = product.getBarcode();
        this.imageUrl = product.getImageUrl();
        this.weight = product.getWeight();
        this.originPlace = product.getOriginPlace();
        this.gmtCreate = product.getGmtCreate();
        this.gmtModified = product.getGmtModified();
        this.creator = product.getCreator();
        this.modifier = product.getModifier();
    }
}
