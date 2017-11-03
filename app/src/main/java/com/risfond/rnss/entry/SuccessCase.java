package com.risfond.rnss.entry;

import java.math.BigDecimal;

/**
 * Created by Abbott on 2017/6/30.
 */

public class SuccessCase {
    /**
     public int ArticleId { get; set; }//文章ID
     public string Tittle { get; set; }//文章标题
     public string Name { get; set; }//发布用户
     public DateTime CreatedTime { get; set; }//发布时间
     public string JobName { get; set; }//职位名称
     public string EnterpriseName { get; set; }//企业名称
     public int PositionCycle { get; set; }//职位周期
     public string JoinStaffs { get; set; }//顾问团队
     public decimal PositionYearlySalary { get; set; }//职位年薪
     public string WorkingPlace { get; set; }//工作地点
     public string JobTitle { get; set; }//所在行业
     public int? EmploymenterNum { get; set; }//上岗人数
     public string StaffPhoto { get; set; }//顾问头像
     public string StaffName { get; set; }//中文名字
     public string EnName { get; set; }//英文名字

     */

    private int ArticleId;
    private String Tittle;
    private String Name;
    private String CreatedTime;
    private String JobName;
    private String EnterpriseName;
    private int PositionCycle;
    private String JoinStaffs;
    private BigDecimal PositionYearlySalary;
    private String WorkingPlace;
    private String JobTitle;
    private int EmploymenterNum;
    private String StaffPhoto;
    private String StaffName;
    private String EnName;


    public int getArticleId() {
        return ArticleId;
    }

    public void setArticleId(int articleId) {
        ArticleId = articleId;
    }

    public String getTittle() {
        return Tittle;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }

    public String getEnterpriseName() {
        return EnterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        EnterpriseName = enterpriseName;
    }

    public int getPositionCycle() {
        return PositionCycle;
    }

    public void setPositionCycle(int positionCycle) {
        PositionCycle = positionCycle;
    }

    public String getJoinStaffs() {
        return JoinStaffs;
    }

    public void setJoinStaffs(String joinStaffs) {
        JoinStaffs = joinStaffs;
    }

    public BigDecimal getPositionYearlySalary() {
        return PositionYearlySalary;
    }

    public void setPositionYearlySalary(BigDecimal positionYearlySalary) {
        PositionYearlySalary = positionYearlySalary;
    }

    public String getWorkingPlace() {
        return WorkingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        WorkingPlace = workingPlace;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public int getEmploymenterNum() {
        return EmploymenterNum;
    }

    public void setEmploymenterNum(int employmenterNum) {
        EmploymenterNum = employmenterNum;
    }

    public String getStaffPhoto() {
        return StaffPhoto;
    }

    public void setStaffPhoto(String staffPhoto) {
        StaffPhoto = staffPhoto;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public String getEnName() {
        return EnName;
    }

    public void setEnName(String enName) {
        EnName = enName;
    }
}
