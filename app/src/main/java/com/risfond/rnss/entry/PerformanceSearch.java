package com.risfond.rnss.entry;

import java.math.BigDecimal;

/**
 * Created by Abbott on 2017/7/7.
 */

public class PerformanceSearch {

    /**
     * int Id //id
     * string CompanyName //公司名称
     * string StaffName //顾问姓名
     * int CheckStatus //审批状态  0审核中 1已通过 2已驳回
     * string JobCandidateName //人选姓名
     * string JobCandidateStatus //推荐状态  (2 推荐给客户 6客户面试 8发OFFER 9成功入职)
     * string JobSalary //薪资
     * string JobTitle //职位
     * string JobDate //日期
     */


    private int Id;
    private String CompanyName;
    private String StaffName;
    private int CheckStatus;
    private String JobCandidateName;
    private String JobCandidateStatus;
    private String JobSalary;
    private String JobTitle;
    private String JobDate;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public int getCheckStatus() {
        return CheckStatus;
    }

    public void setCheckStatus(int checkStatus) {
        CheckStatus = checkStatus;
    }

    public String getJobCandidateName() {
        return JobCandidateName;
    }

    public void setJobCandidateName(String jobCandidateName) {
        JobCandidateName = jobCandidateName;
    }

    public String getJobCandidateStatus() {
        return JobCandidateStatus;
    }

    public void setJobCandidateStatus(String jobCandidateStatus) {
        JobCandidateStatus = jobCandidateStatus;
    }

    public String getJobSalary() {
        return JobSalary;
    }

    public void setJobSalary(String jobSalary) {
        JobSalary = jobSalary;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getJobDate() {
        return JobDate;
    }

    public void setJobDate(String jobDate) {
        JobDate = jobDate;
    }
}
