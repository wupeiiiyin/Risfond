package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/7/14.
 */

public class RecommendList {
    private int Recomid;
    private String Photo;
    private String Name;
    private String Jobtitle;
    private String Companyname;
    private String Mobile;
    private int Talktotal;
    private String Updatetime;
    private String Gender;
    private int Status;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getRecomid() {
        return Recomid;
    }

    public void setRecomid(int recomid) {
        Recomid = recomid;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getJobtitle() {
        return Jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        Jobtitle = jobtitle;
    }

    public String getCompanyname() {
        return Companyname;
    }

    public void setCompanyname(String companyname) {
        Companyname = companyname;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public int getTalktotal() {
        return Talktotal;
    }

    public void setTalktotal(int talktotal) {
        Talktotal = talktotal;
    }

    public String getUpdatetime() {
        return Updatetime;
    }

    public void setUpdatetime(String updatetime) {
        Updatetime = updatetime;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
