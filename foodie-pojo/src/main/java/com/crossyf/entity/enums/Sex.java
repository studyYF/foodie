package com.crossyf.entity.enums;

/**
 * @Desc: 性别 枚举
 */
public enum Sex {
    /**
     * 女性
     */
    woman(0, "女"),
    /**
     * 男性
     */
    man(1, "男"),
    /**
     * 保密
     */
    secret(2, "保密");

    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
