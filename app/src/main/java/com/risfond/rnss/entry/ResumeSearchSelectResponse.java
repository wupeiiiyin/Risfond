package com.risfond.rnss.entry;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbott on 2017/6/30.
 * update by zhangchuan on 2017/11/29 数据格式调整
 */

public class ResumeSearchSelectResponse  implements Serializable, Parcelable {


    /**
     * Code : 0
     * Status : true
     * Message : 请求成功
     * Data : [{"keywordstype":2,"creatTime":"2017.11.10 06:43","resumequeryid":1,"keyword":"ios开发","pageindex":4,"staffid":6272,"worklocation":null,"worklocations":null,"yearfrom":0,"yearto":0,"edu":null,"edus":null,"agefrom":0,"ageto":0,"gender":null,"genders":null,"salaryfrom":0,"salaryto":0,"resumestatus":null,"lang":null,"langs":null}]
     * Total : 0
     * JobCount : 2
     */

    private int Code;
    private boolean Status;
    private String Message;
    private int Total;
    private int JobCount;
    private List<DataBean> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public int getJobCount() {
        return JobCount;
    }

    public void setJobCount(int JobCount) {
        this.JobCount = JobCount;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean implements Parcelable {
        /**
         * keywordstype : 2
         * creatTime : 2017.11.10 06:43
         * resumequeryid : 1
         * keyword : ios开发
         * pageindex : 4
         * staffid : 6272
         * worklocation : null
         * worklocations : null
         * yearfrom : 0
         * yearto : 0
         * edu : null
         * edus : null
         * agefrom : 0
         * ageto : 0
         * gender : null
         * genders : null
         * salaryfrom : 0
         * salaryto : 0
         * resumestatus : null
         * lang : null
         * langs : null
         */

        private int keywordstype;
        private String creatTime;
        private int resumequeryid;
        private String keyword;
        private int pageindex;
        private int staffid;
        private List<String> worklocation;
        private List<String> worklocations;
        private int yearfrom;
        private int yearto;
        private List<String> edu;
        private List<String> edus;
        private int agefrom;
        private int ageto;
        private List<String> gender;
        private List<String> genders;
        private int salaryfrom;
        private int salaryto;
        private List<String> resumestatus;
        private List<String> lang;
        private List<String> langs;

        @Override
        public String toString() {
            return "DataBean{" +
                    "keywordstype=" + keywordstype +
                    ", creatTime='" + creatTime + '\'' +
                    ", resumequeryid=" + resumequeryid +
                    ", keyword='" + keyword + '\'' +
                    ", pageindex=" + pageindex +
                    ", staffid=" + staffid +
                    ", worklocation=" + worklocation +
                    ", worklocations=" + worklocations +
                    ", yearfrom=" + yearfrom +
                    ", yearto=" + yearto +
                    ", edu=" + edu +
                    ", edus=" + edus +
                    ", agefrom=" + agefrom +
                    ", ageto=" + ageto +
                    ", gender=" + gender +
                    ", genders=" + genders +
                    ", salaryfrom=" + salaryfrom +
                    ", salaryto=" + salaryto +
                    ", resumestatus=" + resumestatus +
                    ", lang=" + lang +
                    ", langs=" + langs +
                    '}';
        }

        public int getKeywordstype() {
            return keywordstype;
        }

        public void setKeywordstype(int keywordstype) {
            this.keywordstype = keywordstype;
        }

        public String getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
        }

        public int getResumequeryid() {
            return resumequeryid;
        }

        public void setResumequeryid(int resumequeryid) {
            this.resumequeryid = resumequeryid;
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

        public List<String> getWorklocations() {
            return worklocations;
        }

        public void setWorklocations(List<String> worklocations) {
            this.worklocations = worklocations;
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

        public List<String> getEdu() {
            return edu;
        }

        public void setEdu(List<String> edu) {
            this.edu = edu;
        }

        public List<String> getEdus() {
            return edus;
        }

        public void setEdus(List<String> edus) {
            this.edus = edus;
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

        public List<String> getGenders() {
            return genders;
        }

        public void setGenders(List<String> genders) {
            this.genders = genders;
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

        public List<String> getLangs() {
            return langs;
        }

        public void setLangs(List<String> langs) {
            this.langs = langs;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.keywordstype);
            dest.writeString(this.creatTime);
            dest.writeInt(this.resumequeryid);
            dest.writeString(this.keyword);
            dest.writeInt(this.pageindex);
            dest.writeInt(this.staffid);
            dest.writeStringList(this.worklocation);
            dest.writeStringList(this.worklocations);
            dest.writeInt(this.yearfrom);
            dest.writeInt(this.yearto);
            dest.writeStringList(this.edu);
            dest.writeStringList(this.edus);
            dest.writeInt(this.agefrom);
            dest.writeInt(this.ageto);
            dest.writeStringList(this.gender);
            dest.writeStringList(this.genders);
            dest.writeInt(this.salaryfrom);
            dest.writeInt(this.salaryto);
            dest.writeStringList(this.resumestatus);
            dest.writeStringList(this.lang);
            dest.writeStringList(this.langs);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.keywordstype = in.readInt();
            this.creatTime = in.readString();
            this.resumequeryid = in.readInt();
            this.keyword = in.readString();
            this.pageindex = in.readInt();
            this.staffid = in.readInt();
            this.worklocation = in.createStringArrayList();
            this.worklocations = in.createStringArrayList();
            this.yearfrom = in.readInt();
            this.yearto = in.readInt();
            this.edu = in.createStringArrayList();
            this.edus = in.createStringArrayList();
            this.agefrom = in.readInt();
            this.ageto = in.readInt();
            this.gender = in.createStringArrayList();
            this.genders = in.createStringArrayList();
            this.salaryfrom = in.readInt();
            this.salaryto = in.readInt();
            this.resumestatus = in.createStringArrayList();
            this.lang = in.createStringArrayList();
            this.langs = in.createStringArrayList();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Code);
        dest.writeByte(this.Status ? (byte) 1 : (byte) 0);
        dest.writeString(this.Message);
        dest.writeInt(this.Total);
        dest.writeInt(this.JobCount);
        dest.writeList(this.Data);
    }

    public ResumeSearchSelectResponse() {
    }

    protected ResumeSearchSelectResponse(Parcel in) {
        this.Code = in.readInt();
        this.Status = in.readByte() != 0;
        this.Message = in.readString();
        this.Total = in.readInt();
        this.JobCount = in.readInt();
        this.Data = new ArrayList<DataBean>();
        in.readList(this.Data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ResumeSearchSelectResponse> CREATOR = new Parcelable.Creator<ResumeSearchSelectResponse>() {
        @Override
        public ResumeSearchSelectResponse createFromParcel(Parcel source) {
            return new ResumeSearchSelectResponse(source);
        }

        @Override
        public ResumeSearchSelectResponse[] newArray(int size) {
            return new ResumeSearchSelectResponse[size];
        }
    };
}
