package com.crossyf.entity.enums;

/**
 * @author yangf
 */

public enum CommentLevel {
    /**
     * 好评
     */
    GOOD(1, "好评"),
    /**
     * 中评
     */
    NORMAL(2, "中评"),
    /**
     * 差评
     */
    BAD(3, "差评");

    public final Integer type;
    public final String value;

    CommentLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
