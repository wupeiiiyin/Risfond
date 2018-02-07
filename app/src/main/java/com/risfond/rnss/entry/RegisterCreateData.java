package com.risfond.rnss.entry;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Abbott on 2017/10/11.
 */

public class RegisterCreateData implements IPickerViewData {

    private String value;
    private String code;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getPickerViewText() {
        return value;
    }
}
