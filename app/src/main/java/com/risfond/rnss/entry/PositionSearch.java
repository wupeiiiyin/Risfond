package com.risfond.rnss.entry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abbott on 2017/7/7.
 */

public class PositionSearch implements Parcelable {
    /**
     * ID : 40336
     * Code : #40336
     * Title : 架构师
     * Salary : 10-20万
     * RunDay : 180
     * Status : 运作
     * LastCommunicationTime : 01.12
     * Locations : 北京
     * ClientName : 北京人众人科技发展有限公司
     */

    private int ID;
    private String Code;
    private String Title;
    private String Salary;
    private int RunDay;
    private String Status;
    private String LastCommunicationTime;
    private String Locations;
    private String ClientName;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String Salary) {
        this.Salary = Salary;
    }

    public int getRunDay() {
        return RunDay;
    }

    public void setRunDay(int RunDay) {
        this.RunDay = RunDay;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getLastCommunicationTime() {
        return LastCommunicationTime;
    }

    public void setLastCommunicationTime(String LastCommunicationTime) {
        this.LastCommunicationTime = LastCommunicationTime;
    }

    public String getLocations() {
        return Locations;
    }

    public void setLocations(String Locations) {
        this.Locations = Locations;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String ClientName) {
        this.ClientName = ClientName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.Code);
        dest.writeString(this.Title);
        dest.writeString(this.Salary);
        dest.writeInt(this.RunDay);
        dest.writeString(this.Status);
        dest.writeString(this.LastCommunicationTime);
        dest.writeString(this.Locations);
        dest.writeString(this.ClientName);
    }

    public PositionSearch() {
    }

    protected PositionSearch(Parcel in) {
        this.ID = in.readInt();
        this.Code = in.readString();
        this.Title = in.readString();
        this.Salary = in.readString();
        this.RunDay = in.readInt();
        this.Status = in.readString();
        this.LastCommunicationTime = in.readString();
        this.Locations = in.readString();
        this.ClientName = in.readString();
    }

    public static final Parcelable.Creator<PositionSearch> CREATOR = new Parcelable.Creator<PositionSearch>() {
        @Override
        public PositionSearch createFromParcel(Parcel source) {
            return new PositionSearch(source);
        }

        @Override
        public PositionSearch[] newArray(int size) {
            return new PositionSearch[size];
        }
    };
}
