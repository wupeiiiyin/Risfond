package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/6/23.
 */

public class TimeArrivalResponse {


    /**
     * Code : 200
     * Status : true
     * Message : 请求成功
     * Data : [{"Amount":41400,"ConsultantStaffId":1651,"ConsultantStaffName":"勾元辰","MiddlePictureUrl":"http://static2.risfond.com/photos/49044df01f3d4303b31b8782db4e82a8.jpg?x-oss-process=image/resize,m_fixed,h_92,w_92","CompanyId":14,"CompanyName":"通州分部","RecordTime":"06-26","ClientExhibitionName":"中国航空某研究所"},{"Amount":20000,"ConsultantStaffId":1343,"ConsultantStaffName":"芮莉","MiddlePictureUrl":"http://static2.risfond.com/photos/4d3de846d35d4d58b676637fb4e84d24.png?x-oss-process=image/resize,m_fixed,h_92,w_92","CompanyId":8,"CompanyName":"南京公司","RecordTime":"06-26","ClientExhibitionName":"浙江某机械制造公司"},{"Amount":19320,"ConsultantStaffId":1942,"ConsultantStaffName":"于凤云","MiddlePictureUrl":"http://static2.risfond.com/photos/6963080618b646b8bce601d40b52b794.jpg?x-oss-process=image/resize,m_fixed,h_92,w_92","CompanyId":8,"CompanyName":"南京公司","RecordTime":"06-26","ClientExhibitionName":"国内某知名建筑装饰工程有限公司"},{"Amount":162039.6,"ConsultantStaffId":3422,"ConsultantStaffName":"曹玉恩","MiddlePictureUrl":"http://static2.risfond.com/photos/c189903e4800438d8d748a4436119540.png?x-oss-process=image/resize,m_fixed,h_92,w_92","CompanyId":40,"CompanyName":"丰台分部","RecordTime":"06-26","ClientExhibitionName":"北京某置业有限公司"},{"Amount":35000,"ConsultantStaffId":5653,"ConsultantStaffName":"谢阳","MiddlePictureUrl":"http://static2.risfond.com/photos/8580eed876c24b1ebf9f81858770d10c.jpg?x-oss-process=image/resize,m_fixed,h_92,w_92","CompanyId":40,"CompanyName":"丰台分部","RecordTime":"06-26","ClientExhibitionName":"知名互联网公司"},{"Amount":12960,"ConsultantStaffId":3082,"ConsultantStaffName":"付国伟","MiddlePictureUrl":"http://static2.risfond.com/photos/b9a35b21e7264d4dacf2fb7ff617d464.jpg?x-oss-process=image/resize,m_fixed,h_92,w_92","CompanyId":41,"CompanyName":"常营分部","RecordTime":"06-26","ClientExhibitionName":"某信息技术公司"}]
     */

    private int Code;
    private boolean Status;
    private String Message;
    private List<TimeArrival> Data;

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

    public List<TimeArrival> getData() {
        return Data;
    }

    public void setData(List<TimeArrival> Data) {
        this.Data = Data;
    }

}
