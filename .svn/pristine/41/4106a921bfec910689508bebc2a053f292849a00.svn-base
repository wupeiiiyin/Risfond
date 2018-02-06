package com.risfond.rnss.entry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abbott on 2017/7/7.
 */

public class CustomerRecord implements Parcelable {
    /**
     * HFDate : 2016.12.23 15:03
     * HFSafffName : 黄小平
     * HFContent : f的是非得失
     */

    private boolean isExpanded;

    private String HFDate;
    private String HFSafffName;
    private String HFContent;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getHFDate() {
        return HFDate;
    }

    public void setHFDate(String HFDate) {
        this.HFDate = HFDate;
    }

    public String getHFSafffName() {
        return HFSafffName;
    }

    public void setHFSafffName(String HFSafffName) {
        this.HFSafffName = HFSafffName;
    }

    public String getHFContent() {
        return HFContent;
    }

    public void setHFContent(String HFContent) {
        this.HFContent = HFContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isExpanded ? (byte) 1 : (byte) 0);
        dest.writeString(this.HFDate);
        dest.writeString(this.HFSafffName);
        dest.writeString(this.HFContent);
    }

    public CustomerRecord() {
    }

    protected CustomerRecord(Parcel in) {
        this.isExpanded = in.readByte() != 0;
        this.HFDate = in.readString();
        this.HFSafffName = in.readString();
        this.HFContent = in.readString();
    }

    public static final Parcelable.Creator<CustomerRecord> CREATOR = new Parcelable.Creator<CustomerRecord>() {
        @Override
        public CustomerRecord createFromParcel(Parcel source) {
            return new CustomerRecord(source);
        }

        @Override
        public CustomerRecord[] newArray(int size) {
            return new CustomerRecord[size];
        }
    };
}
