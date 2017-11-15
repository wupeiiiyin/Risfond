package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/6/30.
 */

public class ResumeSearchAddResponse {

    /**
     * Code : 0
     * Total : 5485147
     * Status : true
     * Message : 请求成功
     * Data : [{"Id":6009723,"Name":"搞死机","ResumeCode":"6009723","UpdateDate":"2017.05.27","Photo":"","GenderId":"1","Age":33,"WorkExperience":7,"CompanyFullName":"北京通力互联科技服务有限公司","JobTitle":"<span class=\"keyword-highlight\">asp.net<\/span>开发工程师","LiveLocationTxt":"深圳","EducationLevelTxt":"大专","StatusTxt":"无"},{"Id":6009722,"Name":"姜慧慧 (seven)","ResumeCode":"6009722","UpdateDate":"2017.05.23","Photo":"","GenderId":"0","Age":0,"WorkExperience":0,"CompanyFullName":"锐仕方达","JobTitle":"猎头","LiveLocationTxt":"济南","EducationLevelTxt":"","StatusTxt":"无"},{"Id":6009721,"Name":"Desmond CHUNG","ResumeCode":"6009721","UpdateDate":"2017.05.18","Photo":"","GenderId":"0","Age":0,"WorkExperience":0,"CompanyFullName":"Global Executive Consultants Ltd.","JobTitle":"Executive, PRC","LiveLocationTxt":"","EducationLevelTxt":"","StatusTxt":"无"},{"Id":6009720,"Name":"吕博","ResumeCode":"6009720","UpdateDate":"2017.05.17","Photo":"","GenderId":"1","Age":32,"WorkExperience":9,"CompanyFullName":"海航集团有限公司","JobTitle":"平面设计师","LiveLocationTxt":"北京","EducationLevelTxt":"本科","StatusTxt":"无"},{"Id":6009719,"Name":"吕博","ResumeCode":"6009719","UpdateDate":"2017.05.17","Photo":"","GenderId":"1","Age":32,"WorkExperience":9,"CompanyFullName":"海航集团有限公司","JobTitle":"平面设计师","LiveLocationTxt":"北京","EducationLevelTxt":"本科","StatusTxt":"无"},{"Id":6009718,"Name":"Barry Ding","ResumeCode":"6009718","UpdateDate":"2017.05.16","Photo":"http://static.risfond.com/images2/2015/8/a4f6eb0a97e54f40bf96043a18c66221.jpg","GenderId":"0","Age":0,"WorkExperience":0,"CompanyFullName":"Rupro Consulting 睿璞管理咨询","JobTitle":"Partner","LiveLocationTxt":"浦东新区","EducationLevelTxt":"","StatusTxt":"无"},{"Id":6009717,"Name":"张俊","ResumeCode":"6009717","UpdateDate":"2017.05.16","Photo":"","GenderId":"1","Age":26,"WorkExperience":3,"CompanyFullName":"亿虎迎通有限公司","JobTitle":"前端开发工程师","LiveLocationTxt":"北京","EducationLevelTxt":"本科","StatusTxt":"无"},{"Id":6009716,"Name":"凌志宏","ResumeCode":"6009716","UpdateDate":"2017.05.15","Photo":"","GenderId":"0","Age":0,"WorkExperience":0,"CompanyFullName":"INNOLUX群創光電","JobTitle":"高級設備工程師","LiveLocationTxt":"","EducationLevelTxt":"","StatusTxt":"无"},{"Id":6009715,"Name":"Andie Zhou","ResumeCode":"6009715","UpdateDate":"2017.05.12","Photo":"","GenderId":"0","Age":0,"WorkExperience":0,"CompanyFullName":"Risfond Executive Search","JobTitle":"猎头顾问","LiveLocationTxt":"朝阳区","EducationLevelTxt":"","StatusTxt":"无"},{"Id":6009714,"Name":"Relly Kang","ResumeCode":"6009714","UpdateDate":"2017.05.12","Photo":"","GenderId":"0","Age":0,"WorkExperience":0,"CompanyFullName":"北京某猎头公司","JobTitle":"猎头顾问","LiveLocationTxt":"朝阳区","EducationLevelTxt":"","StatusTxt":"无"},{"Id":6009713,"Name":"Relly Kang","ResumeCode":"6009713","UpdateDate":"2017.05.12","Photo":"","GenderId":"0","Age":0,"WorkExperience":0,"CompanyFullName":"北京某猎头公司","JobTitle":"猎头顾问","LiveLocationTxt":"朝阳区","EducationLevelTxt":"","StatusTxt":"无"},{"Id":6009712,"Name":"Relly Kang","ResumeCode":"6009712","UpdateDate":"2017.05.12","Photo":"","GenderId":"0","Age":0,"WorkExperience":0,"CompanyFullName":"北京某猎头公司","JobTitle":"猎头顾问","LiveLocationTxt":"朝阳区","EducationLevelTxt":"","StatusTxt":"无"},{"Id":6009711,"Name":"victor Liu","ResumeCode":"6009711","UpdateDate":"2017.05.12","Photo":"","GenderId":"0","Age":0,"WorkExperience":0,"CompanyFullName":"上海杰易猎头公司 Shanghai JE Executive Search Co.,Ltd.","JobTitle":"猎头顾问","LiveLocationTxt":"烟台","EducationLevelTxt":"","StatusTxt":"无"},{"Id":6009710,"Name":"victor Liu","ResumeCode":"6009710","UpdateDate":"2017.05.12","Photo":"http://static.risfond.com/images2/2015/8/04583507970b49a28cdb125a9cd3d21b.jpg","GenderId":"0","Age":0,"WorkExperience":0,"CompanyFullName":"上海杰易猎头公司 Shanghai JE Executive Search Co.,Ltd.","JobTitle":"猎头顾问","LiveLocationTxt":"烟台","EducationLevelTxt":"","StatusTxt":"无"},{"Id":6009709,"Name":"Gangli (Teresa) Nie","ResumeCode":"6009709","UpdateDate":"2017.05.11","Photo":"","GenderId":"0","Age":0,"WorkExperience":0,"CompanyFullName":"Risfond Executive Search","JobTitle":"Senior Consultant","LiveLocationTxt":"济南","EducationLevelTxt":"","StatusTxt":"无"}]
     */

    private int Code;
    private int Total;
    private boolean Status;
    private String Message;
    private List<ResumeSearch> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
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

    public List<ResumeSearch> getData() {
        return Data;
    }

    public void setData(List<ResumeSearch> data) {
        Data = data;
    }
}
