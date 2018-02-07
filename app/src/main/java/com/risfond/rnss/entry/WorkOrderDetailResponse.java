package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/10/23.
 */

public class WorkOrderDetailResponse {

    /**
     * Code : 200
     * Status : true
     * Message : 请求成功
     * Data : {"Id":1005,"ClientId":null,"InCallTime":"2017-10-09T15:50:00","InCallTimeStr":"2017-10-09 15:50","Location":"0201","LocationDefinition":"和平区","ConsultationType":1,"ConsultationTypeDefinition":"电话咨询","Industry":"43","IndustryDefinition":"餐饮服务","CompanyName":"四川成双防腐材料有限公司","LinkMan":"666","LinkPhone":"18066666666","JobDescription":"备注：企业通过APP注册提交的招聘需求，请尽快受理.职位：999","IsOffer":0,"AddTime":"2017-10-17T17:40:28.3","AddTimeStr":"2017-10-17 17:40","CreatorStaffId":2,"CreatorName":"黄小平","CreatorCompany":1,"Status":1,"StatusDefinition":"未接收","WorkOrderStatusRemark":null,"WorkOrderStatusRemarkDefinition":null,"OtherReason":null,"CounselorId":26,"CounselorName":"何鑫","CounselorCompany":2,"SignInTime":null,"SignInTimeStr":"","HandleTime":null,"HandleTimeStr":"","IsVerify":1,"BilledState":1}
     */

    private int Code;
    private boolean Status;
    private String Message;
    private WorkOrderDetail Data;

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

    public WorkOrderDetail getData() {
        return Data;
    }

    public void setData(WorkOrderDetail Data) {
        this.Data = Data;
    }

}
