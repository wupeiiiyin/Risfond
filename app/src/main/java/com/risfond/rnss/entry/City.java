package com.risfond.rnss.entry;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Abbott on 2017/8/10.
 */

public class City implements IPickerViewData {
    private String id;
    private String name;
    private String pinyin;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
