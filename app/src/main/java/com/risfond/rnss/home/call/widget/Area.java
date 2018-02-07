package com.risfond.rnss.home.call.widget;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class Area extends BaseIndexPinyinBean implements Parcelable {

    private String areacode;//城市名字
    private String areacodeid;//城市名字
    private boolean isTop;//是否是最上面的 不需要被转化成拼音的

    public Area() {
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getAreacodeid() {
        return areacodeid;
    }

    public void setAreacodeid(String areacodeid) {
        this.areacodeid = areacodeid;
    }

    public Area(String areacode) {
        this.areacode = areacode;
    }

    public String getAreacode() {
        return areacode;
    }

    public Area setCity(String areacode) {
        this.areacode = areacode;
        return this;
    }

    public boolean isTop() {
        return isTop;
    }

    public Area setTop(boolean top) {
        isTop = top;
        return this;
    }

    @Override
    public String getTarget() {
        return areacode;
    }

    @Override
    public boolean isNeedToPinyin() {
        return !isTop;
    }


    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.areacode);
        dest.writeString(this.areacodeid);
        dest.writeByte(this.isTop ? (byte) 1 : (byte) 0);
    }

    protected Area(Parcel in) {
        this.areacode = in.readString();
        this.areacodeid = in.readString();
        this.isTop = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Area> CREATOR = new Parcelable.Creator<Area>() {
        @Override
        public Area createFromParcel(Parcel source) {
            return new Area(source);
        }

        @Override
        public Area[] newArray(int size) {
            return new Area[size];
        }
    };
}
