package com.dx.Booker.generator.po;

/**
 * @Author: xiangXX
 * @Description: book分类小标签
 * @Date Created in 14:25 2018/6/19 0019
 */
public class tag {
    private  Integer id;
    private String name;
    private Integer labelId;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }
}
