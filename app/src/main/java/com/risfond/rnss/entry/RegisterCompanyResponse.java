package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/10/11.
 */

public class RegisterCompanyResponse {


    /**
     * Code : 0
     * Status : true
     * Message : 请求成功
     * Data : [{"CompanyId":1,"CompanyName":"北京公司"},{"CompanyId":2,"CompanyName":"上海公司"},{"CompanyId":3,"CompanyName":"广州公司"},{"CompanyId":4,"CompanyName":"深圳公司"},{"CompanyId":5,"CompanyName":"成都公司"},{"CompanyId":6,"CompanyName":"青岛公司"},{"CompanyId":7,"CompanyName":"重庆公司"},{"CompanyId":8,"CompanyName":"南京公司"},{"CompanyId":9,"CompanyName":"武汉公司"},{"CompanyId":10,"CompanyName":"西安公司"},{"CompanyId":11,"CompanyName":"高新分部"},{"CompanyId":12,"CompanyName":"长沙公司"},{"CompanyId":13,"CompanyName":"国贸分部"},{"CompanyId":14,"CompanyName":"通州分部"},{"CompanyId":15,"CompanyName":"天津公司"},{"CompanyId":16,"CompanyName":"苏州公司"},{"CompanyId":17,"CompanyName":"望京分部"},{"CompanyId":18,"CompanyName":"金融街分部"},{"CompanyId":19,"CompanyName":"郑州公司"},{"CompanyId":20,"CompanyName":"佛山公司"},{"CompanyId":21,"CompanyName":"徐家汇分部"},{"CompanyId":22,"CompanyName":"深南分部"},{"CompanyId":23,"CompanyName":"济南公司"},{"CompanyId":24,"CompanyName":"无锡公司"},{"CompanyId":25,"CompanyName":"大连公司"},{"CompanyId":26,"CompanyName":"长春公司"},{"CompanyId":27,"CompanyName":"东莞公司"},{"CompanyId":28,"CompanyName":"中关村分部"},{"CompanyId":29,"CompanyName":"燕郊分部"},{"CompanyId":30,"CompanyName":"厦门公司"},{"CompanyId":31,"CompanyName":"汉口分部"},{"CompanyId":32,"CompanyName":"武昌分部"},{"CompanyId":33,"CompanyName":"沈阳公司"},{"CompanyId":34,"CompanyName":"哈尔滨公司"},{"CompanyId":35,"CompanyName":"合肥公司"},{"CompanyId":36,"CompanyName":"宁波公司"},{"CompanyId":37,"CompanyName":"南宁公司"},{"CompanyId":38,"CompanyName":"杭州公司"},{"CompanyId":39,"CompanyName":"张江分部"},{"CompanyId":40,"CompanyName":"丰台分部"},{"CompanyId":41,"CompanyName":"常营分部"},{"CompanyId":42,"CompanyName":"秦淮事业部"},{"CompanyId":43,"CompanyName":"旧金山公司"},{"CompanyId":44,"CompanyName":"成都IT事业部"},{"CompanyId":45,"CompanyName":"常州公司"},{"CompanyId":46,"CompanyName":"昆明公司"},{"CompanyId":47,"CompanyName":"香港公司"},{"CompanyId":48,"CompanyName":"南昌公司"},{"CompanyId":49,"CompanyName":"福州公司"},{"CompanyId":50,"CompanyName":"立水桥公司"},{"CompanyId":51,"CompanyName":"苏州一分"},{"CompanyId":52,"CompanyName":"成都汽车事业部"},{"CompanyId":58,"CompanyName":"中山公司"},{"CompanyId":59,"CompanyName":"石家庄公司"},{"CompanyId":60,"CompanyName":"上海三分"},{"CompanyId":87,"CompanyName":"合肥一分"},{"CompanyId":88,"CompanyName":"大连二分"},{"CompanyId":89,"CompanyName":"北京亦庄"},{"CompanyId":90,"CompanyName":"北京回龙观"},{"CompanyId":91,"CompanyName":"郑州一分"},{"CompanyId":92,"CompanyName":"杭州一分"},{"CompanyId":93,"CompanyName":"微聘"},{"CompanyId":94,"CompanyName":"徐州公司"},{"CompanyId":95,"CompanyName":"绍兴公司"},{"CompanyId":96,"CompanyName":"泉州公司"},{"CompanyId":97,"CompanyName":"金边公司"}]
     */

    private int Code;
    private boolean Status;
    private String Message;
    private List<RegisterCompany> Data;

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

    public List<RegisterCompany> getData() {
        return Data;
    }

    public void setData(List<RegisterCompany> Data) {
        this.Data = Data;
    }

}
