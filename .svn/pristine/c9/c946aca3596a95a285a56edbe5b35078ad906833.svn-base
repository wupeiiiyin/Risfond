package com.risfond.rnss.entry;

import android.os.Parcel;
import android.os.Parcelable;

import com.risfond.rnss.home.call.widget.BaseIndexPinyinBean;

/**
 * Created by Abbott on 2017/5/18.
 */

public class UserList extends BaseIndexPinyinBean implements Parcelable {
    /**
     * staffid : 14
     * headphoto : http://static.risfond.com/images2/2015/6/2571790a7e464377964cf25bf7192113.jpg
     * cnname : 彭艳
     * enname : Emma
     * positionname : 资深顾问
     * easemobaccount : emma
     * easemobpwd : fbU7QVssvVR2KvfDN9+ajA==
     */

    private int staffid;
    private String headphoto;
    private String cnname;
    private String enname;
    private String positionname;
    private String easemobaccount;
    private String easemobpwd;
    private boolean isSelected;

    public UserList() {
    }

    public int getStaffid() {
        return staffid;
    }

    public void setStaffid(int staffid) {
        this.staffid = staffid;
    }

    public String getHeadphoto() {
        return headphoto;
    }

    public void setHeadphoto(String headphoto) {
        this.headphoto = headphoto;
    }

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getPositionname() {
        return positionname;
    }

    public void setPositionname(String positionname) {
        this.positionname = positionname;
    }

    public String getEasemobaccount() {
        return easemobaccount;
    }

    public void setEasemobaccount(String easemobaccount) {
        this.easemobaccount = easemobaccount;
    }

    public String getEasemobpwd() {
        return easemobpwd;
    }

    public void setEasemobpwd(String easemobpwd) {
        this.easemobpwd = easemobpwd;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    protected UserList(Parcel in) {
        staffid = in.readInt();
        headphoto = in.readString();
        cnname = in.readString();
        enname = in.readString();
        positionname = in.readString();
        easemobaccount = in.readString();
        easemobpwd = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<UserList> CREATOR = new Creator<UserList>() {
        @Override
        public UserList createFromParcel(Parcel in) {
            return new UserList(in);
        }

        @Override
        public UserList[] newArray(int size) {
            return new UserList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(staffid);
        dest.writeString(headphoto);
        dest.writeString(cnname);
        dest.writeString(enname);
        dest.writeString(positionname);
        dest.writeString(easemobaccount);
        dest.writeString(easemobpwd);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public String getTarget() {
        return cnname;
    }
}
