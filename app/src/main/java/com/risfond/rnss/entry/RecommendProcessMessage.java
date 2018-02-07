package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/7/17.
 */

public class RecommendProcessMessage {
    /**
     * CheckedStatus : 已通过
     * Content  : 参加了面试1
     * Staffid : 1
     * StaffName  : 黄小平
     * InComeTime  : 2017.03.01 10::00
     */

    private String CheckedStatus;
    private String Content;
    private int Staffid;
    private String StaffName;
    private String InComeTime;

    public String getCheckedStatus() {
        return CheckedStatus;
    }

    public void setCheckedStatus(String CheckedStatus) {
        this.CheckedStatus = CheckedStatus;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public int getStaffid() {
        return Staffid;
    }

    public void setStaffid(int Staffid) {
        this.Staffid = Staffid;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String StaffName) {
        this.StaffName = StaffName;
    }

    public String getInComeTime() {
        return InComeTime;
    }

    public void setInComeTime(String InComeTime) {
        this.InComeTime = InComeTime;
    }
}
