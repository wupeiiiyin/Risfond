package com.risfond.rnss.entry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abbott on 2017/11/10.查询所有
 */

public class ResumeSearchAll implements Parcelable{

    private int Id;
    private String Name;
    private String ResumeCode;
    private String UpdateDate;
    private String Photo;
    private String GenderId;
    private int Age;
    private int WorkExperience;
    private String CompanyFullName;
    private String JobTitle;
    private String LiveLocationTxt;
    private String EducationLevelTxt;
    private String StatusTxt;
    private int keywordstype;
    private int jobid ;
    private int selecttype ;

    public int getKeywordstype() {
        return keywordstype;
    }

    public void setKeywordstype(int keywordstype) {
        this.keywordstype = keywordstype;
    }

    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public int getSelecttype() {
        return selecttype;
    }

    public void setSelecttype(int selecttype) {
        this.selecttype = selecttype;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getResumeCode() {
        return ResumeCode;
    }

    public void setResumeCode(String ResumeCode) {
        this.ResumeCode = ResumeCode;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String UpdateDate) {
        this.UpdateDate = UpdateDate;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    public String getGenderId() {
        return GenderId;
    }

    public void setGenderId(String GenderId) {
        this.GenderId = GenderId;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public int getWorkExperience() {
        return WorkExperience;
    }

    public void setWorkExperience(int WorkExperience) {
        this.WorkExperience = WorkExperience;
    }

    public String getCompanyFullName() {
        return CompanyFullName;
    }

    public void setCompanyFullName(String CompanyFullName) {
        this.CompanyFullName = CompanyFullName;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String JobTitle) {
        this.JobTitle = JobTitle;
    }

    public String getLiveLocationTxt() {
        return LiveLocationTxt;
    }

    public void setLiveLocationTxt(String LiveLocationTxt) {
        this.LiveLocationTxt = LiveLocationTxt;
    }

    public String getEducationLevelTxt() {
        return EducationLevelTxt;
    }

    public void setEducationLevelTxt(String EducationLevelTxt) {
        this.EducationLevelTxt = EducationLevelTxt;
    }

    public String getStatusTxt() {
        return StatusTxt;
    }

    public void setStatusTxt(String StatusTxt) {
        this.StatusTxt = StatusTxt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.Id);
        dest.writeString(this.Name);
        dest.writeString(this.ResumeCode);
        dest.writeString(this.UpdateDate);
        dest.writeString(this.Photo);
        dest.writeString(this.GenderId);
        dest.writeInt(this.Age);
        dest.writeInt(this.WorkExperience);
        dest.writeString(this.CompanyFullName);
        dest.writeString(this.JobTitle);
        dest.writeString(this.LiveLocationTxt);
        dest.writeString(this.EducationLevelTxt);
        dest.writeString(this.StatusTxt);
        dest.writeInt(this.keywordstype);
        dest.writeInt(this.jobid);
        dest.writeInt(this.selecttype);
    }
    public ResumeSearchAll(){

    }
    protected ResumeSearchAll(Parcel in) {
        this.Id = in.readInt();
        this.Name = in.readString();
        this.ResumeCode = in.readString();
        this.UpdateDate = in.readString();
        this.Photo = in.readString();
        this.GenderId = in.readString();
        this.Age = in.readInt();
        this.WorkExperience = in.readInt();
        this.CompanyFullName = in.readString();
        this.JobTitle = in.readString();
        this.LiveLocationTxt = in.readString();
        this.EducationLevelTxt = in.readString();
        this.StatusTxt = in.readString();
        this.keywordstype  = in.readInt();
        this.jobid  = in.readInt();
        this.selecttype  = in.readInt();
    }
    public static final Parcelable.Creator<ResumeSearchAll> CREATOR = new Parcelable.Creator<ResumeSearchAll>() {
        @Override
        public ResumeSearchAll createFromParcel(Parcel source) {
            return new ResumeSearchAll(source);
        }

        @Override
        public ResumeSearchAll[] newArray(int size) {
            return new ResumeSearchAll[size];
        }
    };
}
