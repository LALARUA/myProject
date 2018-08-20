package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.DynamicInformation;

import java.util.ArrayList;

public class DynamicInformations {
    private ArrayList<DynamicInformation> dynamicInformations;
    private ArrayList<Integer> ids;

    public ArrayList<DynamicInformation> getDynamicInformations() {
        return dynamicInformations;
    }

    public void setDynamicInformations(ArrayList<DynamicInformation> dynamicInformations) {
        this.dynamicInformations = dynamicInformations;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }
}
