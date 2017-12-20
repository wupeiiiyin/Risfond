package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/9/21.
 */

public class MyCustomerResponse {


    /**
     * Code : 0
     * Status : true
     * Message : 请求成功
     * Data : [{"HrCompanyName":"九月小篮子公司","list":[{"HrId":"00000018","HrName":"张二牛","HrPhotoUtl":""},{"HrId":"00000018","HrName":"张二牛","HrPhotoUtl":""}]}]
     */

    private int Code;
    private boolean Status;
    private String Message;
    private List<MyCustomerCompany> Data;

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

    public List<MyCustomerCompany> getData() {
        return Data;
    }

    public void setData(List<MyCustomerCompany> Data) {
        this.Data = Data;
    }

}
