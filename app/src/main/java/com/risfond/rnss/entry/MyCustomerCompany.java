package com.risfond.rnss.entry;

import android.os.Parcel;
import android.os.Parcelable;

import com.risfond.rnss.home.call.widget.BaseIndexPinyinBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbott on 2017/9/21.
 */

public class MyCustomerCompany extends BaseIndexPinyinBean implements Parcelable {

    /**
     * HrCompanyName : 九月小篮子公司
     * list : [{"HrId":"00000018","HrName":"张二牛","HrPhotoUtl":""},{"HrId":"00000018","HrName":"张二牛","HrPhotoUtl":""}]
     */

    private String HrCompanyName;
    private ArrayList<UserList> list;

    protected MyCustomerCompany(Parcel in) {
        HrCompanyName = in.readString();
        list = in.createTypedArrayList(UserList.CREATOR);
    }

    public static final Creator<MyCustomerCompany> CREATOR = new Creator<MyCustomerCompany>() {
        @Override
        public MyCustomerCompany createFromParcel(Parcel in) {
            return new MyCustomerCompany(in);
        }

        @Override
        public MyCustomerCompany[] newArray(int size) {
            return new MyCustomerCompany[size];
        }
    };

    public String getHrCompanyName() {
        return HrCompanyName;
    }

    public void setHrCompanyName(String HrCompanyName) {
        this.HrCompanyName = HrCompanyName;
    }

    public ArrayList<UserList> getList() {
        return list;
    }

    public void setList(ArrayList<UserList> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(HrCompanyName);
        dest.writeTypedList(list);
    }

    @Override
    public String getTarget() {
        return HrCompanyName;
    }
}
