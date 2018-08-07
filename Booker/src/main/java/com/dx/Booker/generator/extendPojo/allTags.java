package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.label;
import com.dx.Booker.generator.po.tag;

import java.util.List;

/**
 * @Author: xiangXX
 * @Description: 所有tags
 * @Date Created in 14:29 2018/6/19 0019
 */
public class allTags extends label {
    private List<tag> tags;
    private List<Integer> tagIds;

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }

    public List<tag> getTags() {
        return tags;
    }

    public void setTags(List<tag> tags) {
        this.tags = tags;
    }
}
