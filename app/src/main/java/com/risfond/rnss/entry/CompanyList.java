package com.risfond.rnss.entry;

import android.os.Parcel;
import android.os.Parcelable;

import com.risfond.rnss.home.call.widget.BaseIndexPinyinBean;

/**
 * Created by Abbott on 2017/5/18.
 */

public class CompanyList extends BaseIndexPinyinBean implements Parcelable {
    /**
     * id : 1
     * name : 北京公司
     * staffcount : 563
     */

    private int id;
    private String name;
    private int staffcount;
    private int count;

    public CompanyList() {
    }

    protected CompanyList(Parcel in) {
        id = in.readInt();
        name = in.readString();
        staffcount = in.readInt();
        count = in.readInt();
    }

    public static final Creator<CompanyList> CREATOR = new Creator<CompanyList>() {
        @Override
        public CompanyList createFromParcel(Parcel in) {
            return new CompanyList(in);
        }

        @Override
        public CompanyList[] newArray(int size) {
            return new CompanyList[size];
        }
    };

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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
        dest.writeInt(staffcount);
        dest.writeInt(count);
    }

    @Override
    public String getTarget() {
        return name;
    }
}
