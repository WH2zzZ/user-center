package com.oowanghan.project.user.common;

/**
 * @Author WangHan
 * @Create 2021/3/7 5:28 下午
 */
public enum GenderTypeEnum {

    /**
     * 保密
     */
    UNKNOW(0),
    /**
     * 男
     */
    MAN(1),
    /**
     * 女
     */
    WOMAN(2),
    ;

    private int type;
    GenderTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
