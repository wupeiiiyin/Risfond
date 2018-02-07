package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/8/8.
 */

public class MyCourse {

    /**
     * Id : 1114
     * CourseStar : 5
     * CourseBanner : http://static2.risfond.com/photos/2e00f6984e7f4021bc48e0df774321c1.jpg?x-oss-process=image/resize,m_fixed,h_135,w_180
     * Title : 新手营系列课程
     * Teacher : 陆冰、马丽、孙传彩
     * Desc : 当你刚进入公司还感觉到迷茫的时候，对公司的企业文化、制度、猎头sop流程还不是很了解的时候，那么你就带着问题来学习一下作为猎头小白和新人猎头伙伴们的特约课程吧，相信你一定会有所收获的。加油~   我们在新秀营等着大家
     * TrainingObjectName : 猎头助理
     * KechengFeatureName : 新人指南
     * ShowNum : 4157
     * CreatedTime : 2017.06.30
     * Detail : []
     */

    private int Id;
    private int CourseStar;
    private String CourseBanner;
    private String Title;
    private String Teacher;
    private String Desc;
    private String TrainingObjectName;
    private String KechengFeatureName;
    private int ShowNum;
    private String CreatedTime;
    private List<?> Detail;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getCourseStar() {
        return CourseStar;
    }

    public void setCourseStar(int CourseStar) {
        this.CourseStar = CourseStar;
    }

    public String getCourseBanner() {
        return CourseBanner;
    }

    public void setCourseBanner(String CourseBanner) {
        this.CourseBanner = CourseBanner;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String Teacher) {
        this.Teacher = Teacher;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public String getTrainingObjectName() {
        return TrainingObjectName;
    }

    public void setTrainingObjectName(String TrainingObjectName) {
        this.TrainingObjectName = TrainingObjectName;
    }

    public String getKechengFeatureName() {
        return KechengFeatureName;
    }

    public void setKechengFeatureName(String KechengFeatureName) {
        this.KechengFeatureName = KechengFeatureName;
    }

    public int getShowNum() {
        return ShowNum;
    }

    public void setShowNum(int ShowNum) {
        this.ShowNum = ShowNum;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String CreatedTime) {
        this.CreatedTime = CreatedTime;
    }

    public List<?> getDetail() {
        return Detail;
    }

    public void setDetail(List<?> Detail) {
        this.Detail = Detail;
    }
}
