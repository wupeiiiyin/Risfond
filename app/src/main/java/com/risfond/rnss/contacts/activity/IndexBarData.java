package com.risfond.rnss.contacts.activity;

import android.os.Parcel;
import android.os.Parcelable;

import com.risfond.rnss.home.call.widget.BaseIndexPinyinBean;

/**
 * Created by Abbott on 2017/9/5.
 */

public class IndexBarData extends BaseIndexPinyinBean implements Parcelable {

    public IndexBarData() {
    }

    private String value;
    private boolean isTop;//是否是最上面的 不需要被转化成拼音的

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    protected IndexBarData(Parcel in) {
        value = in.readString();
        isTop = in.readByte() != 0;
    }

    public static final Creator<IndexBarData> CREATOR = new Creator<IndexBarData>() {
        @Override
        public IndexBarData createFromParcel(Parcel in) {
            return new IndexBarData(in);
        }

        @Override
        public IndexBarData[] newArray(int size) {
            return new IndexBarData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
        dest.writeByte((byte) (isTop ? 1 : 0));
    }

    @Override
    public String getTarget() {
        return value;
    }
}
