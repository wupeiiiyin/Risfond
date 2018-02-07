package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/7/12.
 */

public class RecommendManagementResponse {

    /**
     * Code : 0
     * Status : true
     * Message : 请求成功
     * Data : [{"ID":40336,"Code":"#40336","Title":"架构师","Salary":"10-20万","RunDay":180,"Status":"运作","LastCommunicationTime":"01.12","Locations":"北京","ClientName":"北京人众人科技发展有限公司"}]
     */

    private int Code;
    private boolean Status;
    private String Message;
    private List<Recommend> Data;

    public List<Recommend> getData() {
        return Data;
    }

    public void setData(List<Recommend> data) {
        Data = data;
    }

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

}
