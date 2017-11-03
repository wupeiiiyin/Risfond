package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/5/19.
 */

public class SearchResponse {

    /**
     * Code : 200
     * Status : true
     * Message : 请求成功
     * Data : {"stafflist":[{"staffid":7,"headphoto":"http://static.risfond.com/images2/2014/12/546e7bd5d5bf450fb1170ae158dfca2d.jpg","cnname":"唐爱华北京","enname":"Alina","positionname":"猎头顾问","easemobaccount":"alina","easemobpwd":"fbU7QVssvVR2KvfDN9+ajA=="},{"staffid":771,"headphoto":"http://static.risfond.com/images2/2015/7/e9ae2098aa4d4758b94a2300d32474f6.jpg","cnname":"刘京丽","enname":"Julie","positionname":"实习顾问","easemobaccount":"julie.liu","easemobpwd":"fbU7QVssvVR2KvfDN9+ajA=="},{"staffid":868,"headphoto":"/static/images/defaultphoto_big.gif","cnname":"潘京阳","enname":"Jason","positionname":"猎头助理","easemobaccount":"jason.pan","easemobpwd":"fbU7QVssvVR2KvfDN9+ajA=="},{"staffid":1136,"headphoto":"http://static.risfond.com/images2/2014/4/d796f93feed9492083ec707e37827271.jpg","cnname":"范京鹏","enname":"Andy","positionname":"猎头助理","easemobaccount":"Andy.fan","easemobpwd":"fbU7QVssvVR2KvfDN9+ajA=="},{"staffid":1196,"headphoto":"http://static.risfond.com/images2/2014/5/d2cea831b24f4e34bf5d6e7a4a4b7e92.jpg","cnname":"郭京阳","enname":"Vic","positionname":"实习顾问","easemobaccount":"vic.guo","easemobpwd":"fbU7QVssvVR2KvfDN9+ajA=="},{"staffid":1445,"headphoto":"http://static.risfond.com/images2/2015/7/4db3636a91744ad68e8c8e6a08dc1795.jpg","cnname":"邸京虎","enname":"Tiger","positionname":"猎头助理","easemobaccount":"tiger.di","easemobpwd":"fbU7QVssvVR2KvfDN9+ajA=="},{"staffid":2392,"headphoto":"http://static.risfond.com/images2/2015/8/abd8e097c33d40af95cb15308e69cad3.jpg","cnname":"李京玲","enname":"Taylor","positionname":"助理顾问","easemobaccount":"Taylor.li","easemobpwd":"fbU7QVssvVR2KvfDN9+ajA=="}],"companylist":[{"id":1,"name":"北京公司","count":563},{"id":8,"name":"南京公司","count":126},{"id":17,"name":"望京分部","count":42}]}
     */

    private int Code;
    private boolean Status;
    private String Message;
    private Search Data;

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

    public Search getData() {
        return Data;
    }

    public void setData(Search Data) {
        this.Data = Data;
    }

}
