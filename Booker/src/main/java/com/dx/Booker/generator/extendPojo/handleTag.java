package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.label;
import com.dx.Booker.generator.po.tag;

/**
 * @Author: xiangXX
 * @Description: 用于管理员操作标签
 * @Date Created in 9:18 2018/6/20 0020
 */
public class handleTag extends label {

    private String tagName;
    private Integer tagId;

    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
