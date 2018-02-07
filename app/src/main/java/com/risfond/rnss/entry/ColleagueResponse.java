package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/6/23.
 */

public class ColleagueResponse {

    /**
     * Code : 200
     * Status : true
     * Message : null
     * Data : [{"StaffId":6374,"Name":"中文姓名","EnFirstName":"Ab","MiddlePictureUrl":"http://static.risfond.com/images2/2017/5/9f5e51b9012949888c09a724331194b6.jpg"},{"StaffId":3998,"Name":"测试用户2","EnFirstName":"Ce","MiddlePictureUrl":"http://static.risfond.com/images2/2017/3/a150784858434368b92c4f97b8a5e51b.jpg"},{"StaffId":2998,"Name":"测试员工","EnFirstName":"Yg","MiddlePictureUrl":"http://static.risfond.com/images2/2017/1/9d4554929c074e1e9a54d4555fb868d4.jpg"},{"StaffId":2999,"Name":"dsfdsfdsssds","EnFirstName":"Gabriel","MiddlePictureUrl":"http://static.risfond.com/images2/2017/1/7f105a7f2a5f49dca5571bb9fdba880a.jpg"},{"StaffId":2997,"Name":"夏尔","EnFirstName":"JACKY","MiddlePictureUrl":"http://static.risfond.com/images2/2016/11/03430aec1177418ca0fdfc3ac5f6787c.jpg"},{"StaffId":2954,"Name":"陈波","EnFirstName":"Andy","MiddlePictureUrl":"http://static.risfond.com/images2/2016/1/1ea14e91027843cfbf55ba8e113c1f54.png"}]
     */

    private int Code;
    private boolean Status;
    private Object Message;
    private List<Colleague> Data;

    public List<Colleague> getData() {
        return Data;
    }

    public void setData(List<Colleague> data) {
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

    public Object getMessage() {
        return Message;
    }

    public void setMessage(Object Message) {
        this.Message = Message;
    }

}
