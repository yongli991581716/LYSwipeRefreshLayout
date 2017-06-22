package com.ly.library.bean;

/**
 * 控件模式
 *
 * @author liyong
 * @date 2017/6/21 10:42
 */
public enum Mode {
    NORAML("普通模式", 0), LIST("列表模式", 1), NORAML_EXTEND("普通可伸缩模式", 2), LIST_EXTEND("列表可伸缩模式", 3);
    /**
     * 名字
     */
    public String name;
    /**
     * 模式值
     */
    public int value;

    Mode(String name, int value) {
        this.name = name;
        this.value = value;
    }

}
