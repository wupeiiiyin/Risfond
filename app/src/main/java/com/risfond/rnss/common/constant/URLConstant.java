package com.risfond.rnss.common.constant;


import com.risfond.rnss.BuildConfig;

/**
 * Created by Abbott on 2017/3/24.
 * 常量类
 */

public class URLConstant {

    /* URL头*/
    public static final String URL_TOP = BuildConfig.HOST;
    /* H5 URL头*/
    public static final String H5_URL_TOP = BuildConfig.H5_HOST;

    /* 登录接口URL*/
    public static final String URL_LOGIN = URL_TOP + "user/login";
    /* 手机号登录接口URL*/
    public static final String URL_MOBILE_LOGIN = URL_TOP + "user/mobilelogin";
    /* 获取验证码接口URL*/
    public static final String URL_MESSAGE_SEND = URL_TOP + "message/send";
    /* 公司列表接口URL*/
    public static final String URL_COMPANY_LIST = URL_TOP + "user/CompanyList";
    /* 部门列表接口URL*/
    public static final String URL_DEPART_LIST = URL_TOP + "user/DepartMentList";
    /* 员工列表接口URL*/
    public static final String URL_USER_LIST = URL_TOP + "user/list";
    /* 搜索页面接口URL*/
    public static final String URL_SEARCH = URL_TOP + "user/search";
    /* 员工详细信息查询接口URL*/
    public static final String URL_USER_INFO = URL_TOP + "user/get";
    /* APP检测更新接口URL*/
    public static final String URL_APP_UPDATE = URL_TOP + "Upgrade/get";
    /* 下载APP接口URL*/
    public static final String URL_APP_DOWNLOAD = URL_TOP + "Upgrade/DownLoad";
    /* 根据环信ID获取员工信息接口URL*/
    public static final String URL_GET_BY_ACCOUNT = URL_TOP + "user/GetByAccount";
    /* 根据名称搜索员工列表接口URL*/
    public static final String URL_SEARCH_BY_STAFF = URL_TOP + "user/SearchByStaff";
    /* 群组列表接口URL*/
    public static final String URL_GROUP_LIST = URL_TOP + "user/GetGroup";
    /* 运营平台接口URL*/
    public static final String URL_OPERATION_PLATFORM = URL_TOP + "index/performancesummary";
    /* 新同事列表接口URL*/
    public static final String URL_COLLEAGUE_LIST = URL_TOP + "index/newcolleague";
    /* 实时到账榜表接口URL*/
    public static final String URL_TIME_ARRIVAL_LIST = URL_TOP + "index/daozhang";
    /* 账号登录接口URL*/
    public static final String URL_ACCOUNT_LOGIN = URL_TOP + "user/AccountLogin";
    /* 发送验证码接口URL*/
    public static final String URL_SEND_BY_ACCOUNT = URL_TOP + "message/SendByAccount";
    /* 账号或手机号登录接口URL*/
    public static final String URL_BLEND_LOGIN = URL_TOP + "user/blendlogin";
    /* 发送验证码接口URL*/
    public static final String URL_SEND_BY_BLEND = URL_TOP + "message/sendbyblend";
    /* 龙虎榜接口URL*/
    public static final String URL_LONG_HU = URL_TOP + "index/longhu";
    /* 搜索简历接口URL*/
    public static final String URL_RESUME_SEARCH = URL_TOP + "resume/resumesearch";
    /* 搜索详情H5接口URL*/
    public static final String URL_RESUME_DETAIL_H5 = H5_URL_TOP + "resume/ltviewresume?";
    /* 客户接口URL*/
    @Deprecated
    public static final String URL_CUSTOMER_SEARCH = URL_TOP + "client/list";
    /* 客户接口URL_V2*/
    public static final String URL_CUSTOMER_SEARCH2 = URL_TOP + "client/list2";
    /* 客户详情接口URL*/
    public static final String URL_CUSTOMER_DETAIL = URL_TOP + "client/detail";
    /* 客户基本信息H5*/
    public static final String URL_CUSTOMER_INFO_H5 = H5_URL_TOP + "Client/ClientDetail?";
    /* 职位详情H5接口URL*/
    public static final String URL_JOB_DETAIL_H5 = H5_URL_TOP + "job/ltviewjob";
    /* 职位列表接口URL*/
    public static final String URL_JOB_SEARCH = URL_TOP + "job/list";
    /* 推荐管理接口URL*/
    public static final String URL_JOB_RECOMMEND = URL_TOP + "job/recommend";
    /* 推荐管理列表接口URL*/
    public static final String URL_JOB_RECOMMEND_LIST = URL_TOP + "job/recommendlist";
    /* 推荐流程接口URL*/
    public static final String URL_JOB_RECOMMEND_STEP = URL_TOP + "job/recommendstep";
    /* 推荐信息接口URL*/
    public static final String URL_RECOMMEND_INFO_H5 = H5_URL_TOP + "recommend/ltrecommendview";
    /* 拨打电话接口URL*/
    public static final String URL_CALL_PHONE = URL_TOP + "call/telphonecall";
    /* 推荐列表接口URL*/
    public static final String URL_RECOMMEND_MANAGE = URL_TOP + "publicclient/recommendlist";
    /* 公共客户接口URL*/
    public static final String URL_PUBLIC_CUSTOMER = URL_TOP + "publicclient/list";
    /* 公共客户详情接口URL*/
    public static final String URL_PUBLIC_CUSTOMER_DETAIL = URL_TOP + "publicclient/detail";
    /* 客户满意度调查接口URL*/
    public static final String URL_SATISFACTION_H5 = H5_URL_TOP + "Satisfaction/Satisfaction";
    /* 扫码接口URL*/
    public static final String URL_SCAN_LOGIN = "http://staff.risfond.com/vlogin/index";
    /* 绩效列表接口URL*/
    public static final String URL_PERFORMANCE_MANAGE = URL_TOP + "Assessment/list";
    /* 发票列表接口URL*/
    public static final String URL_INVOICE_MANAGE = URL_TOP + "Invoices/list";
    /* 发票详情接口URL*/
    public static final String URL_INVOIVCE_MANAGE_DETAIL = URL_TOP + "Invoices/detail";
    /* 我的考勤接口URL*/
    public static final String URL_MY_ATTENDANCE = URL_TOP + "Attendance/list";
    /* 我的外出接口URL*/
    public static final String URL_MY_WENTOUT = URL_TOP + "Attendance/goout";
    /* 我的请假接口URL*/
    public static final String URL_MY_ASKLEAVE = URL_TOP + "Attendance/leave";
    /* 课程列表接口URL*/
    public static final String URL_COURSE_LIST = URL_TOP + "course/list";
    /* 课程详情接口H5URL*/
    public static final String URL_COURSE_LTDETAIL = H5_URL_TOP + "course/ltdetail";
    /* 请假获取发送短信人id */
    public static final String URL_GETAPPROVALSAFF = URL_TOP + "Attendance/GetApprovalStaff";
    /* 请假申请 */
    public static final String URL_QUSTLEAVE = URL_TOP + "Attendance/AddApproval";
    /* 成功案例 */
    public static final String URL_SUCCESS_CASE = URL_TOP + "SuccessfulCase/SuccessArticle";
    /* 案例详情H5接口URL*/
    public static final String URL_CASE_DETAIL_H5 = H5_URL_TOP + "successfulcase/ltdetail?";
    /* 推荐人选列表接口URL*/
    public static final String URL_GET_JOBS = URL_TOP + "job/GetJobs";
    /* 推荐人选提交接口url*/
    public static final String URL_ADD_JOB_CANDIDATE_HANDLER = URL_TOP + "job/AddJobCandidateHandler";
    /* 流程跟进提交接口url*/
    public static final String URL_ADD_JOB_ADDLIUCHENG = URL_TOP + "job/AddLiucheng";
    /* 我的客户回访跟进接口url*/
    public static final String URL_RETURN_FOLLOW = URL_TOP + "Client/AddMemo";
    /* 公共客户申请转入接口url*/
    public static final String URL_CLIENT_APPLICATION = URL_TOP + "Client/AddPublicClientApplication";
    /* 添加动态接口url*/
    public static final String URL_ADD_INTERACTION = URL_TOP + "Interaction/AddInteraction";
    /*查询动态接口url*/
    public static final String URL_GET_PAGE_INTERACTION_MANAGE = URL_TOP + "Interaction/GetPageInteractionManage";
    /*更新动态接口url*/
    public static final String URL_INTERACTION = URL_TOP + "Interaction/Interaction";
    /*动态未读数量接口url*/
    public static final String URL_GET_TOP_INTERACTION = URL_TOP + "Interaction/GetTopInteraction";
    /*动态未读数量接口2url*/
    public static final String URL_GET_TOP_INTERACTION_V2 = URL_TOP + "Interaction/GetTopInteraction2";
    /*获取聊天客户接口url*/
    public static final String URL_GET_USER_HR = URL_TOP + "user/GetHrInfos";
    /*注册发送验证码接口url*/
    public static final String URL_GET_MESSAGE = URL_TOP + "user/GetMessage";
    /*注册第一步提交接口url*/
    public static final String URL_CHECK_MESS = URL_TOP + "user/CheckMess";
    /*注册第二步获取公司接口url*/
    public static final String URL_GET_COMPANY = URL_TOP + "user/GetCompany";
    /*注册第二步提交接口url*/
    public static final String URL_ADD_PERSON_INFO = URL_TOP + "user/AddPersonInfo";
    /*注册第三步提交接口url*/
    public static final String URL_ADD_EDU_INFO = URL_TOP + "user/AddEduInfo";
    /*客户认证接口url*/
    public static final String URL_REFRESH_CLIENT_STATUE = URL_TOP + "Client/RefreshClientStatue";
    /*工单接口url*/
    public static final String URL_WORK_ORDER_LIST = URL_TOP + "WorkOrder/WorkOrderList";
    /*工单详情接口url*/
    public static final String URL_WORK_ORDER_DETAIL = URL_TOP + "WorkOrder/WorkOrderDetail";
    /*工单转发接口url*/
    public static final String URL_WORK_ORDER_RELAY = URL_TOP + "WorkOrder/WorkOrderRelay";
    /*工单接收接口url*/
    public static final String URL_WORK_ORDER_ACCEPT = URL_TOP + "WorkOrder/WorkOrderAccept";
    /*新闻列表接口url*/
    @Deprecated
    public static final String URL_NEWS_LIST = URL_TOP + "news/list";
    /*新闻列表接口V2url*/
    public static final String URL_NEWS_LIST2 = URL_TOP + "news/list2";
    /*新闻详情接口url*/
    public static final String URL_NEWS_DETAIL_H5 = H5_URL_TOP + "news/ltviewnew?";

    /*保存查询条件接口url*/
    public static final String URL_RESUME_ADDRESUMEQUERY = URL_TOP + "Resume/AddResumeQuery";
    /*查询查询条件接口url*/
    public static final String URL_RESUME_SELECTRESUMEQUERY = URL_TOP + "Resume/SelectResumeQuery";
    /*删除查询条件接口url*/
    public static final String URL_RESUME_DELETERESUMEQUERY = URL_TOP + "Resume/DeleteResumeQuery";
    /*所有查询条件接口url*/
    public static final String URL_RESUME_SEARCHALL = URL_TOP + "Resume/ResumeSearchAll";
}
