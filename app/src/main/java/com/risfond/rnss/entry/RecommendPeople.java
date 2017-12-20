package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/8/22.
 */

public class RecommendPeople {
    /**
     * JobId : 32834
     * JobName : 智能管道部经理
     * JobNum : 32834
     * JobCompany : 联某上某沃某G某台
     * DateTime : 2017.08.21
     */

    private int JobId;
    private String JobName;
    private String JobNum;
    private String JobCompany;
    private String DateTime;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getJobId() {
        return JobId;
    }

    public void setJobId(int JobId) {
        this.JobId = JobId;
    }

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String JobName) {
        this.JobName = JobName;
    }

    public String getJobNum() {
        return JobNum;
    }

    public void setJobNum(String JobNum) {
        this.JobNum = JobNum;
    }

    public String getJobCompany() {
        return JobCompany;
    }

    public void setJobCompany(String JobCompany) {
        this.JobCompany = JobCompany;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String DateTime) {
        this.DateTime = DateTime;
    }
}
