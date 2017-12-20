package com.risfond.rnss.entry;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by Abbott on 2017/8/10.
 */

public class Province implements IPickerViewData {
    private String id;
    private String name;
    private String pinyin;
    private boolean isSelect;
    private boolean isTip;
    private List<City> cities;

    public boolean isTip() {
        return isTip;
    }

    public void setTip(boolean tip) {
        isTip = tip;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
