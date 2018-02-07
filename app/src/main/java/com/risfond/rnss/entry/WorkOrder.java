package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/5/18.
 */

public class WorkOrder {

    /**
     * Count : 2
     * WorkOrderList : [{"Id":1007,"ClientId":null,"InCallTime":"2017-10-09T15:50:00","Location":"03","LocationDefinition":"河北","ConsultationType":1,"ConsultationTypeDefinition":"电话咨询","Industry":"21","IndustryDefinition":"耐用消费品(服饰/纺织/皮革/家具)","CompanyName":"中启计量体系认证中心","LinkMan":"666","LinkPhone":"18066666123","JobDescription":"备注：企业通过APP注册提交的招聘需求，请尽快受理.职位：999","IsOffer":0,"AddTime":"2017-10-17T17:49:34.517","CreatorStaffId":2,"CreatorName":"黄小平","CreatorCompany":1,"Status":2,"StatusDefinition":"已接收","WorkOrderStatusRemark":null,"WorkOrderStatusRemarkDefinition":null,"OtherReason":null,"CounselorId":26,"CounselorName":"何鑫","CounselorCompany":2,"SignInTime":"2017-10-18T17:54:41.43","HandleTime":null,"IsVerify":1,"BilledState":1},{"Id":1003,"ClientId":null,"InCallTime":"2017-10-17T15:42:00","Location":"09","LocationDefinition":"上海","ConsultationType":3,"ConsultationTypeDefinition":"在线留言","Industry":"10","IndustryDefinition":"计算机","CompanyName":"123","LinkMan":" 123","LinkPhone":" 12312312322","JobDescription":"1rbwftet we ","IsOffer":0,"AddTime":"2017-10-17T15:42:56.147","CreatorStaffId":2,"CreatorName":"黄小平","CreatorCompany":1,"Status":2,"StatusDefinition":"已接收","WorkOrderStatusRemark":null,"WorkOrderStatusRemarkDefinition":null,"OtherReason":null,"CounselorId":26,"CounselorName":"何鑫","CounselorCompany":2,"SignInTime":"2017-10-19T16:54:05.75","HandleTime":null,"IsVerify":1,"BilledState":1}]
     */

    private int Count;
    private List<WorkOrderList> WorkOrderList;

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public List<WorkOrderList> getWorkOrderList() {
        return WorkOrderList;
    }

    public void setWorkOrderList(List<WorkOrderList> workOrderList) {
        WorkOrderList = workOrderList;
    }
}
