package com.risfond.rnss.entry;

import android.os.Parcel;
import android.os.Parcelable;

import com.risfond.rnss.home.call.widget.BaseIndexPinyinBean;

/**
 * Created by Abbott on 2017/5/18.
 */

public class DepartList extends BaseIndexPinyinBean implements Parcelable {
    private int id;
    private String name;
    private int companyid;
    private int staffcount;

    public DepartList() {
    }

    protected DepartList(Parcel in) {
        id = in.readInt();
        name = in.readString();
        companyid = in.readInt();
        staffcount = in.readInt();
    }

    public static final Creator<DepartList> CREATOR = new Creator<DepartList>() {
        @Override
        public DepartList createFromParcel(Parcel in) {
            return new DepartList(in);
        }

        @Override
        public DepartList[] newArray(int size) {
            return new DepartList[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public int getStaffcount() {
        return staffcount;
    }

    public void setStaffcount(int staffcount) {
        this.staffcount = staffcount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(companyid);
        dest.writeInt(staffcount);
    }

    @Override
    public String getTarget() {
        return name;
    }
}
