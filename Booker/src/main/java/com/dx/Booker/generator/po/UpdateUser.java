package com.dx.Booker.generator.po;

import java.util.List;

/**
 * @Author: xiangXX
 * @Description:
 * @Date Created in 11:58 2018/6/11 0011
 */
public class UpdateUser extends User {
    private List<String> loves;

    public List<String> getLoves() {
        return loves;
    }

    public void setLoves(List<String> loves) {
        this.loves = loves;
    }
}
