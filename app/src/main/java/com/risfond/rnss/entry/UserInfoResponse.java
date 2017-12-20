package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/5/22.
 */

public class UserInfoResponse {

    /**
     * Code : 200
     * Status : true
     * Message : 请求成功
     * Data : {"PY":"","staffid":17,"headphoto":"http://static.risfond.com/images2/2017/2/a86ce8b77af44bcd906e2220a1fa563a.jpg","cnname":"黄斌","enname":"Jack","level":0,"workstatus":"正常","staffcode":17,"companynameid":5,"companyname":"成都公司","departnameid":7,"departname":"管理中心","positionnameid":4,"positionname":"分公司总","teams":"黄小平","phone":"028-86069858","mobilephone":"10900070801","email":"jack@risfond.com","easemobaccount":"jack"}
     */

    private int Code;
    private boolean Status;
    private String Message;
    private UserInfo Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public boolean getStatus() {
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

    public UserInfo getData() {
        return Data;
    }

    public void setData(UserInfo Data) {
        this.Data = Data;
    }


}
