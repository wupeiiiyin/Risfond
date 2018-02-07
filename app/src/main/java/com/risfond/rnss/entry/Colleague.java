package com.risfond.rnss.entry;

import java.math.BigDecimal;

/**
 * Created by Abbott on 2017/6/23.
 */

public class Colleague {

    private int StaffId;
    private String StaffName;
    private String StaffPhoto;
    private BigDecimal PerformanceAmount;
    private int Rank;

    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(int staffId) {
        StaffId = staffId;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public String getStaffPhoto() {
        return StaffPhoto;
    }

    public void setStaffPhoto(String staffPhoto) {
        StaffPhoto = staffPhoto;
    }

    public BigDecimal getPerformanceAmount() {
        return PerformanceAmount;
    }

    public void setPerformanceAmount(BigDecimal performanceAmount) {
        PerformanceAmount = performanceAmount;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }
}
