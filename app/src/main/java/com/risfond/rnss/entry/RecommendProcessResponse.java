package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/7/17.
 */

public class RecommendProcessResponse {

    /**
     * Code : 0
     * Status : true
     * Message : 请求成功
     * Data : [{"Status ":"1","StatusText":"加入项目","list":[{"CheckedStatus":"已通过","Content ":"参加了面试1","Staffid":"1","StaffName ":"黄小平","InComeTime ":"2017.03.01 10::00"},{"CheckedStatus":"已通过","Content ":"参加了面试2","Staffid":"1","StaffName ":"黄小平","InComeTime ":"2017.03.02 10::00"}]}]
     */

    private int Code;
    private boolean Status;
    private String Message;
    private List<RecommendProcess> Data;

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

    public List<RecommendProcess> getData() {
        return Data;
    }

    public void setData(List<RecommendProcess> Data) {
        this.Data = Data;
    }

}
