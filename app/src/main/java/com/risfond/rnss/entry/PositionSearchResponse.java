package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/7/12.
 */

public class PositionSearchResponse {

    /**
     * Code : 0
     * Total : 1
     * Status : true
     * Message : 请求成功
     * Data : [{"ID":40336,"Code":"#40336","Title":"架构师","Salary":"10-20万","RunDay":180,"Status":"运作","LastCommunicationTime":"01.12","Locations":"北京","ClientName":"北京人众人科技发展有限公司"}]
     */

    private int Code;
    private int Total;
    private boolean Status;
    private String Message;
    private List<PositionSearch> Data;

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

    public List<PositionSearch> getData() {
        return Data;
    }

    public void setData(List<PositionSearch> data) {
        Data = data;
    }
}
