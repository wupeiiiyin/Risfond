package com.risfond.rnss.entry;


import android.os.Parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/5
 * @desc 简历条件
 */
public class ResumeWhole extends BaseWhole {
    public int keywordstype;
    public String keyword = "";
    public int pageindex = 1;
    public String staffid;
    public int yearfrom;//工作经验 开始 0 不限
    public int yearto;//工作经验 结束 0 不限
    public int agefrom;//年龄开始 0 不限
    public int ageto;//年龄结束 0 不限
    public int salaryfrom;//年薪开始
    public int salaryto;//年薪结束
    public String schoolname ="";//学校名称
    public String major = "";//专业名称
    public String lastupdatetimefrom ="";//更新开始时间
    public String lastupdatetimeto = "";//更新结束时间
    public String lastupdatetimeTag = "0";
    public int history;//推荐历史 1 有  2  无


    //推荐状态 0不限 1 可推 2 勿扰
    /// <summary>
    /// 00 不限 01 汉语 02 英语 03 日语 04 法语 05德语 06韩语、朝鲜语 07 俄语 09 西班牙语 12 阿拉伯语 13 意大利语 14 葡萄牙语
    /// </summary>
    public List<String> lang = new ArrayList<>();//语言  字符串数组
    public List<String> langs = new ArrayList<>();//语言  字符串数组
    public List<String> industrys = new ArrayList<>();//当前行业
    private List<String> industrysTip = new ArrayList<>();

    public List<String> desiredIndustries = new ArrayList<>();//期望行业
    public List<String> desiredIndustriesTip = new ArrayList<>();//期望行业

    public List<String> desiredlocations = new ArrayList<>();//期望地点
    public List<String> desiredlocationsTip = new ArrayList<>();//期望地点

    public List<String> desiredoccupations = new ArrayList<>();//期望职位
    public List<String> desiredoccupationsTip = new ArrayList<>();//期望职位

    public List<String> worklocation = new ArrayList<>();//工作地点  字符串数组
    public List<String> worklocations = new ArrayList<>();//工作地点  字符串数组
    public List<Integer> edu = new ArrayList<>();//教育开始  0不限 1 博士 2 硕士 3 本科 4 大专
    public List<Integer> edus = new ArrayList<>();//教育开始  0不限 1 博士 2 硕士 3 本科 4 大专
    public List<String> gender = Arrays.asList("0");//性别  空 不限  1男 2女
    public List<String> resumestatus = Arrays.asList("0"); //推荐状态

    @Override
    public String toString() {
        return "ResumeWhole{" +
                "keywordstype=" + keywordstype +
                ", keyword='" + keyword + '\'' +
                ", pageindex=" + pageindex +
                ", staffid='" + staffid + '\'' +
                ", yearfrom=" + yearfrom +
                ", yearto=" + yearto +
                ", agefrom=" + agefrom +
                ", ageto=" + ageto +
                ", salaryfrom=" + salaryfrom +
                ", salaryto=" + salaryto +
                ", schoolname='" + schoolname + '\'' +
                ", major='" + major + '\'' +
                ", lastupdatetimefrom='" + lastupdatetimefrom + '\'' +
                ", lastupdatetimeto='" + lastupdatetimeto + '\'' +
                ", lastupdatetimeTag='" + lastupdatetimeTag + '\'' +
                ", history=" + history +
                ", lang=" + lang +
                ", langs=" + langs +
                ", industrys=" + industrys +
                ", industrysTip=" + industrysTip +
                ", desiredIndustries=" + desiredIndustries +
                ", desiredIndustriesTip=" + desiredIndustriesTip +
                ", desiredlocations=" + desiredlocations +
                ", desiredlocationsTip=" + desiredlocationsTip +
                ", desiredoccupations=" + desiredoccupations +
                ", desiredoccupationsTip=" + desiredoccupationsTip +
                ", worklocation=" + worklocation +
                ", worklocations=" + worklocations +
                ", edu=" + edu +
                ", edus=" + edus +
                ", gender=" + gender +
                ", resumestatus=" + resumestatus +
                '}';
    }

    public String getLastupdatetimeTag() {
        return lastupdatetimeTag;
    }

    public void setLastupdatetimeTag(String lastupdatetimeTag) {
        this.lastupdatetimeTag = lastupdatetimeTag;
    }

    public int getKeywordstype() {
        return keywordstype;
    }

    public void setKeywordstype(int keywordstype) {
        this.keywordstype = keywordstype;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPageindex() {
        return pageindex;
    }

    public void setPageindex(int pageindex) {
        this.pageindex = pageindex;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
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

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLastupdatetimefrom() {
        return lastupdatetimefrom;
    }

    public void setLastupdatetimefrom(String lastupdatetimefrom) {
        this.lastupdatetimefrom = lastupdatetimefrom;
    }

    public String getLastupdatetimeto() {
        return lastupdatetimeto;
    }

    public void setLastupdatetimeto(String lastupdatetimeto) {
        this.lastupdatetimeto = lastupdatetimeto;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    public List<String> getLang() {
        return lang;
    }

    public void setLang(List<String> lang) {
        this.lang = lang;
    }

    public List<String> getLangs() {
        return langs;
    }

    public void setLangs(List<String> langs) {
        this.langs = langs;
    }

    public List<String> getIndustrys() {
        return industrys;
    }

    public void setIndustrys(List<String> industrys) {
        this.industrys = industrys;
    }

    public List<String> getIndustrysTip() {
        return industrysTip;
    }

    public void setIndustrysTip(List<String> industrysTip) {
        this.industrysTip = industrysTip;
    }

    public List<String> getDesiredIndustries() {
        return desiredIndustries;
    }

    public void setDesiredIndustries(List<String> desiredIndustries) {
        this.desiredIndustries = desiredIndustries;
    }

    public List<String> getDesiredIndustriesTip() {
        return desiredIndustriesTip;
    }

    public void setDesiredIndustriesTip(List<String> desiredIndustriesTip) {
        this.desiredIndustriesTip = desiredIndustriesTip;
    }

    public List<String> getDesiredlocations() {
        return desiredlocations;
    }

    public void setDesiredlocations(List<String> desiredlocations) {
        this.desiredlocations = desiredlocations;
    }

    public List<String> getDesiredlocationsTip() {
        return desiredlocationsTip;
    }

    public void setDesiredlocationsTip(List<String> desiredlocationsTip) {
        this.desiredlocationsTip = desiredlocationsTip;
    }

    public List<String> getDesiredoccupations() {
        return desiredoccupations;
    }

    public void setDesiredoccupations(List<String> desiredoccupations) {
        this.desiredoccupations = desiredoccupations;
    }

    public List<String> getDesiredoccupationsTip() {
        return desiredoccupationsTip;
    }

    public void setDesiredoccupationsTip(List<String> desiredoccupationsTip) {
        this.desiredoccupationsTip = desiredoccupationsTip;
    }

    public List<String> getWorklocation() {
        return worklocation;
    }

    public void setWorklocation(List<String> worklocation) {
        this.worklocation = worklocation;
    }

    public List<String> getWorklocations() {
        return worklocations;
    }

    public void setWorklocations(List<String> worklocations) {
        this.worklocations = worklocations;
    }

    public List<Integer> getEdu() {
        return edu;
    }

    public void setEdu(List<Integer> edu) {
        this.edu = edu;
    }

    public List<Integer> getEdus() {
        return edus;
    }

    public void setEdus(List<Integer> edus) {
        this.edus = edus;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public List<String> getResumestatus() {
        return resumestatus;
    }

    public void setResumestatus(List<String> resumestatus) {
        this.resumestatus = resumestatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.keywordstype);
        dest.writeString(this.keyword);
        dest.writeInt(this.pageindex);
        dest.writeString(this.staffid);
        dest.writeInt(this.yearfrom);
        dest.writeInt(this.yearto);
        dest.writeInt(this.agefrom);
        dest.writeInt(this.ageto);
        dest.writeInt(this.salaryfrom);
        dest.writeInt(this.salaryto);
        dest.writeString(this.schoolname);
        dest.writeString(this.major);
        dest.writeString(this.lastupdatetimefrom);
        dest.writeString(this.lastupdatetimeto);
        dest.writeInt(this.history);
        dest.writeStringList(this.lang);
        dest.writeStringList(this.langs);
        dest.writeStringList(this.industrys);
        dest.writeStringList(this.industrysTip);
        dest.writeStringList(this.desiredIndustries);
        dest.writeStringList(this.desiredIndustriesTip);
        dest.writeStringList(this.desiredlocations);
        dest.writeStringList(this.desiredlocationsTip);
        dest.writeStringList(this.desiredoccupations);
        dest.writeStringList(this.desiredoccupationsTip);
        dest.writeStringList(this.worklocation);
        dest.writeStringList(this.worklocations);
        dest.writeList(this.edu);
        dest.writeList(this.edus);
        dest.writeStringList(this.gender);
        dest.writeStringList(this.resumestatus);
    }

    public ResumeWhole() {
    }

    protected ResumeWhole(Parcel in) {
        super(in);
        this.keywordstype = in.readInt();
        this.keyword = in.readString();
        this.pageindex = in.readInt();
        this.staffid = in.readString();
        this.yearfrom = in.readInt();
        this.yearto = in.readInt();
        this.agefrom = in.readInt();
        this.ageto = in.readInt();
        this.salaryfrom = in.readInt();
        this.salaryto = in.readInt();
        this.schoolname = in.readString();
        this.major = in.readString();
        this.lastupdatetimefrom = in.readString();
        this.lastupdatetimeto = in.readString();
        this.history = in.readInt();
        this.lang = in.createStringArrayList();
        this.langs = in.createStringArrayList();
        this.industrys = in.createStringArrayList();
        this.industrysTip = in.createStringArrayList();
        this.desiredIndustries = in.createStringArrayList();
        this.desiredIndustriesTip = in.createStringArrayList();
        this.desiredlocations = in.createStringArrayList();
        this.desiredlocationsTip = in.createStringArrayList();
        this.desiredoccupations = in.createStringArrayList();
        this.desiredoccupationsTip = in.createStringArrayList();
        this.worklocation = in.createStringArrayList();
        this.worklocations = in.createStringArrayList();
        this.edu = new ArrayList<Integer>();
        in.readList(this.edu, Integer.class.getClassLoader());
        this.edus = new ArrayList<Integer>();
        in.readList(this.edus, Integer.class.getClassLoader());
        this.gender = in.createStringArrayList();
        this.resumestatus = in.createStringArrayList();
    }

    public static final Creator<ResumeWhole> CREATOR = new Creator<ResumeWhole>() {
        @Override
        public ResumeWhole createFromParcel(Parcel source) {
            return new ResumeWhole(source);
        }

        @Override
        public ResumeWhole[] newArray(int size) {
            return new ResumeWhole[size];
        }
    };
}
