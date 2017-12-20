package com.risfond.rnss.entry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abbott on 2017/5/22.
 */

public class UserInfo implements Parcelable {
    /**
     * PY :
     * staffid : 17
     * headphoto : http://static.risfond.com/images2/2017/2/a86ce8b77af44bcd906e2220a1fa563a.jpg
     * cnname : 黄斌
     * enname : Jack
     * level : 0
     * workstatus : 正常
     * staffcode : 17
     * companynameid : 5
     * companyname : 成都公司
     * departnameid : 7
     * departname : 管理中心
     * positionnameid : 4
     * positionname : 分公司总
     * teams : 黄小平
     * phone : 028-86069858
     * mobilephone : 10900070801
     * email : jack@risfond.com
     * easemobaccount : jack
     */

    private String PY;
    private int staffid;
    private String headphoto;
    private String cnname;
    private String enname;
    private int level;
    private String workstatus;
    private int staffcode;
    private int companynameid;
    private String companyname;
    private int departnameid;
    private String departname;
    private int positionnameid;
    private String positionname;
    private String teams;
    private String phone;
    private String mobilephone;
    private String email;
    private String easemobaccount;

    public String getPY() {
        return PY;
    }

    public void setPY(String PY) {
        this.PY = PY;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getWorkstatus() {
        return workstatus;
    }

    public void setWorkstatus(String workstatus) {
        this.workstatus = workstatus;
    }

    public int getStaffcode() {
        return staffcode;
    }

    public void setStaffcode(int staffcode) {
        this.staffcode = staffcode;
    }

    public int getCompanynameid() {
        return companynameid;
    }

    public void setCompanynameid(int companynameid) {
        this.companynameid = companynameid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public int getDepartnameid() {
        return departnameid;
    }

    public void setDepartnameid(int departnameid) {
        this.departnameid = departnameid;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    public int getPositionnameid() {
        return positionnameid;
    }

    public void setPositionnameid(int positionnameid) {
        this.positionnameid = positionnameid;
    }

    public String getPositionname() {
        return positionname;
    }

    public void setPositionname(String positionname) {
        this.positionname = positionname;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEasemobaccount() {
        return easemobaccount;
    }

    public void setEasemobaccount(String easemobaccount) {
        this.easemobaccount = easemobaccount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PY);
        dest.writeInt(this.staffid);
        dest.writeString(this.headphoto);
        dest.writeString(this.cnname);
        dest.writeString(this.enname);
        dest.writeInt(this.level);
        dest.writeString(this.workstatus);
        dest.writeInt(this.staffcode);
        dest.writeInt(this.companynameid);
        dest.writeString(this.companyname);
        dest.writeInt(this.departnameid);
        dest.writeString(this.departname);
        dest.writeInt(this.positionnameid);
        dest.writeString(this.positionname);
        dest.writeString(this.teams);
        dest.writeString(this.phone);
        dest.writeString(this.mobilephone);
        dest.writeString(this.email);
        dest.writeString(this.easemobaccount);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.PY = in.readString();
        this.staffid = in.readInt();
        this.headphoto = in.readString();
        this.cnname = in.readString();
        this.enname = in.readString();
        this.level = in.readInt();
        this.workstatus = in.readString();
        this.staffcode = in.readInt();
        this.companynameid = in.readInt();
        this.companyname = in.readString();
        this.departnameid = in.readInt();
        this.departname = in.readString();
        this.positionnameid = in.readInt();
        this.positionname = in.readString();
        this.teams = in.readString();
        this.phone = in.readString();
        this.mobilephone = in.readString();
        this.email = in.readString();
        this.easemobaccount = in.readString();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
