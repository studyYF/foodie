package com.crossyf.entity;

import java.io.Serializable;

/**
 * 商品分类 (Category)实体类
 *
 * @author crossyf
 * @since 2020-08-18 16:19:24
 */
public class Category implements Serializable {
    private static final long serialVersionUID = 796539571972503380L;
    /**
    * 主键 分类id主键
    */
    private Integer id;
    /**
    * 分类名称 分类名称
    */
    private String name;
    /**
    * 分类类型 分类得类型，
1:一级大分类
2:二级分类
3:三级小分类
    */
    private Integer type;
    /**
    * 父id 父id 上一级依赖的id，1级分类则为0，二级三级分别依赖上一级
    */
    private Integer fatherId;
    /**
    * 图标 logo
    */
    private String logo;
    /**
    * 口号
    */
    private String slogan;
    /**
    * 分类图
    */
    private String catImage;
    /**
    * 背景颜色
    */
    private String bgColor;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

}