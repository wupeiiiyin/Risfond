package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/5/18.
 */

public class CompanyListResponse {

    /**
     * Code : 200
     * Status : true
     * Message : 请求成功
     * Data : [{"id":1,"name":"北京公司","staffcount":563},{"id":2,"name":"上海公司","staffcount":151},{"id":3,"name":"广州公司","staffcount":170},{"id":4,"name":"深圳公司","staffcount":117},{"id":5,"name":"成都公司","staffcount":215},{"id":6,"name":"青岛公司","staffcount":117},{"id":7,"name":"重庆公司","staffcount":115},{"id":8,"name":"南京公司","staffcount":126},{"id":9,"name":"武汉公司","staffcount":84},{"id":10,"name":"西安公司","staffcount":66},{"id":11,"name":"高新分部","staffcount":46},{"id":12,"name":"长沙公司","staffcount":76},{"id":13,"name":"国贸分部","staffcount":29},{"id":14,"name":"通州分部","staffcount":68},{"id":15,"name":"天津公司","staffcount":16},{"id":16,"name":"苏州公司","staffcount":45},{"id":17,"name":"望京分部","staffcount":42},{"id":18,"name":"金融街分部","staffcount":24},{"id":19,"name":"郑州公司","staffcount":91},{"id":20,"name":"佛山公司","staffcount":36},{"id":21,"name":"徐家汇分部","staffcount":35},{"id":22,"name":"深南分部","staffcount":26},{"id":23,"name":"济南公司","staffcount":38},{"id":24,"name":"无锡公司","staffcount":34},{"id":25,"name":"大连公司","staffcount":25},{"id":26,"name":"长春公司","staffcount":14},{"id":27,"name":"东莞公司","staffcount":27},{"id":28,"name":"中关村分部","staffcount":41},{"id":29,"name":"燕郊分部","staffcount":43},{"id":30,"name":"厦门公司","staffcount":15},{"id":31,"name":"汉口分部","staffcount":12},{"id":32,"name":"武昌分部","staffcount":17},{"id":33,"name":"沈阳公司","staffcount":23},{"id":34,"name":"哈尔滨公司","staffcount":9},{"id":35,"name":"合肥公司","staffcount":13},{"id":36,"name":"宁波公司","staffcount":7},{"id":37,"name":"南宁公司","staffcount":15},{"id":38,"name":"杭州公司","staffcount":11},{"id":39,"name":"张江分部","staffcount":16},{"id":40,"name":"丰台分部","staffcount":12},{"id":41,"name":"常营分部","staffcount":17},{"id":42,"name":"秦淮事业部","staffcount":6},{"id":43,"name":"旧金山公司","staffcount":6},{"id":44,"name":"成都IT事业部","staffcount":6},{"id":45,"name":"常州公司","staffcount":0},{"id":46,"name":"昆明公司","staffcount":0},{"id":47,"name":"香港公司","staffcount":0},{"id":48,"name":"南昌公司","staffcount":0},{"id":49,"name":"福州公司","staffcount":0},{"id":50,"name":"立水桥公司","staffcount":0},{"id":51,"name":"苏州一分","staffcount":0},{"id":52,"name":"成都汽车事业部","staffcount":11},{"id":58,"name":"中山公司","staffcount":0},{"id":59,"name":"石家庄公司","staffcount":0},{"id":60,"name":"上海三分","staffcount":0}]
     */

    private int Code;
    private boolean Status;
    private String Message;
    private List<CompanyList> Data;

    public List<CompanyList> getData() {
        return Data;
    }

    public void setData(List<CompanyList> data) {
        Data = data;
    }

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


}
