package com.risfond.rnss.entry;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author  @zhangchuan622@gmail.com
 * @version 1.0
 * @create  2017/12/5
 * @desc
 */
public class BaseWhole implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public BaseWhole() {
    }

    protected BaseWhole(Parcel in) {
    }

}
