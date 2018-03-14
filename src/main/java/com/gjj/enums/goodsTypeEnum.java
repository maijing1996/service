package com.gjj.enums;

/**
 * Created by gjj on 2018-03-13.
 */
public enum goodsTypeEnum {
//    digital product
    /**闲置数码*/
    DIGITAL_PRODUCT("闲置数码"),
    /**家具日用*/
    FURNITURE_COMMODITY("家具日用"),
    /**闲置书籍*/
    BOOK("闲置书籍"),
    /**鞋服配饰*/
    SHOES_CLOTHING_ACCESSORIES("鞋服配饰"),
    /**美妆*/
    COSMETICS("美妆"),
    /**文体户外*/
    CULTURE_SPORTS_OUTDOORS("文体户外"),
    /**其他*/
    OTHERS("其他");

    private String code;
    goodsTypeEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
}
