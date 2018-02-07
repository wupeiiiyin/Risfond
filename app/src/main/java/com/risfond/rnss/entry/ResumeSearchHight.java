package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/11/16.
 */

public class ResumeSearchHight {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int keyword;
    private String creatTime;

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    private int pageindex;
    private int staffid;
    private List<String> worklocation;
    private int yearfrom;
    private int yearto;
    private List<Integer> edu;
    private int agefrom;
    private int ageto;
    private List<String> gender;
    private String JobTitle;

    private List<String> worklocations;
    private List<String> edus;
    private List<String> langs;

    public List<String> getWorklocations() {
        return worklocations;
    }

    public void setWorklocations(List<String> worklocations) {
        this.worklocations = worklocations;
    }

    public List<String> getEdus() {
        return edus;
    }

    public void setEdus(List<String> edus) {
        this.edus = edus;
    }

    public List<String> getLangs() {
        return langs;
    }

    public void setLangs(List<String> langs) {
        this.langs = langs;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }
    //    private String genders [];
//
//    public String[] getGenders() {
//        return genders;
//    }
//
//    public void setGenders(String[] genders) {
//        this.genders = genders;
//    }

    private int salaryfrom;
    private int salaryto;
    private List<String> resumestatus;
    private List<String> lang;

    public int getKeyword() {
        return keyword;
    }

    public void setKeyword(int keyword) {
        this.keyword = keyword;
    }

    public int getPageindex() {
        return pageindex;
    }

    public void setPageindex(int pageindex) {
        this.pageindex = pageindex;
    }

    public int getStaffid() {
        return staffid;
    }

    public void setStaffid(int staffid) {
        this.staffid = staffid;
    }

    public List<String> getWorklocation() {
        return worklocation;
    }

    public void setWorklocation(List<String> worklocation) {
        this.worklocation = worklocation;
    }

    public int getYearfrom() {
        return yearfrom;
    }

    public void setYearfrom(int yearfrom) {
        this.yearfrom = yearfrom;
    }

    public int getYearto() {
        return yearto;
    }

    public void setYearto(int yearto) {
        this.yearto = yearto;
    }

    public List<Integer> getEdu() {
        return edu;
    }

    public void setEdu(List<Integer> edu) {
        this.edu = edu;
    }

    public int getAgefrom() {
        return agefrom;
    }

    public void setAgefrom(int agefrom) {
        this.agefrom = agefrom;
    }

    public int getAgeto() {
        return ageto;
    }

    public void setAgeto(int ageto) {
        this.ageto = ageto;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public int getSalaryfrom() {
        return salaryfrom;
    }

    public void setSalaryfrom(int salaryfrom) {
        this.salaryfrom = salaryfrom;
    }

    public int getSalaryto() {
        return salaryto;
    }

    public void setSalaryto(int salaryto) {
        this.salaryto = salaryto;
    }

    public List<String> getResumestatus() {
        return resumestatus;
    }

    public void setResumestatus(List<String> resumestatus) {
        this.resumestatus = resumestatus;
    }

    public List<String> getLang() {
        return lang;
    }

    public void setLang(List<String> lang) {
        this.lang = lang;
    }
}
