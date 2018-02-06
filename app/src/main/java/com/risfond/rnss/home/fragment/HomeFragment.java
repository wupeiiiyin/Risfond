package com.risfond.rnss.home.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.TimeUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.AchievementResponse;
import com.risfond.rnss.entry.DynamicsResponse;
import com.risfond.rnss.entry.HomeProjectInfo;
import com.risfond.rnss.entry.ReturnPayResponse;
import com.risfond.rnss.home.adapter.ColleagueListAdapter;
import com.risfond.rnss.home.adapter.ProjectListAdapter;
import com.risfond.rnss.home.adapter.RealTimeArrivalListAdapter;
import com.risfond.rnss.home.call.activity.CallActivity;
import com.risfond.rnss.home.callback.AchievementCallback;
import com.risfond.rnss.home.callback.DynamicsUnReadCallback;
import com.risfond.rnss.home.callback.ReceivePayCallback;
import com.risfond.rnss.home.commonFuctions.dynamics.activity.DynamicsActivity;
import com.risfond.rnss.home.commonFuctions.invoiceManage.activity.InvoiceManageActivity;
import com.risfond.rnss.home.commonFuctions.myAttenDance.activity.MyAttendanceActivity;
import com.risfond.rnss.home.commonFuctions.myCourse.activity.MyCourseActivity;
import com.risfond.rnss.home.commonFuctions.news.activity.NewsMainActivity;
import com.risfond.rnss.home.commonFuctions.performanceManage.activity.PerformanceManageActivity;
import com.risfond.rnss.home.commonFuctions.publicCustomer.activity.PublicCustomerActivity;
import com.risfond.rnss.home.commonFuctions.referencemanage.activity.ReferenceManageActivity;
import com.risfond.rnss.home.commonFuctions.reminding.activity.RemindingActivity;
import com.risfond.rnss.home.commonFuctions.successCase.activity.SuccessCaseMainActivity;
import com.risfond.rnss.home.commonFuctions.workorder.activity.WorkOrderActivity;
import com.risfond.rnss.home.customer.activity.CustomerSearchActivity;
import com.risfond.rnss.home.modleImpl.AchievementImpl;
import com.risfond.rnss.home.modleImpl.DynamicsUnReadImpl;
import com.risfond.rnss.home.modleImpl.ReceivePayImpl;
import com.risfond.rnss.home.modleInterface.IAchievement;
import com.risfond.rnss.home.modleInterface.IDynamicsUnRead;
import com.risfond.rnss.home.modleInterface.IReceivePay;
import com.risfond.rnss.home.position.activity.PositionSearchActivity;
import com.risfond.rnss.home.resume.activity.ResumeSearchActivity;
import com.risfond.rnss.home.signature.SignatureActivity;
import com.risfond.rnss.widget.PanelView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 */
public class HomeFragment extends BaseFragment implements DynamicsUnReadCallback, AchievementCallback,
        ReceivePayCallback {
    private static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.tv_header_name)
    TextView tv_header_name;
    @BindView(R.id.tv_today)
    TextView tvToday;
    @BindView(R.id.rv_real_time_arrival)
    RecyclerView rvRealTimeArrival;
    @BindView(R.id.colleague)
    TextView colleague;
    @BindView(R.id.rv_colleague)
    RecyclerView rvColleague;
    @BindView(R.id.ll_resume_search)
    LinearLayout llResumeSearch;
    @BindView(R.id.ll_my_custom)
    LinearLayout llMyCustom;
    @BindView(R.id.ll_my_position)
    LinearLayout llMyPosition;
    @BindView(R.id.ll_call_phone)
    LinearLayout llCallPhone;
    @BindView(R.id.ll_operation_platform)
    LinearLayout llOperationPlatform;
    @BindView(R.id.ll_time_arrival)
    LinearLayout llTimeArrival;
    @BindView(R.id.ll_new_colleague)
    LinearLayout llNewColleague;
    @BindView(R.id.gv_application_project)
    RecyclerView gvApplicationProject;
    @BindView(R.id.ll_trans_remind)
    LinearLayout ll_trans_remind;
    @BindView(R.id.rv_percent)
    PanelView rv_percent;
    @BindView(R.id.tv_completed)
    TextView tv_completed;
    @BindView(R.id.tv_incompleted)
    TextView tv_incompleted;
    @BindView(R.id.tv_signature)
    TextView tv_signature;//头部个性签名
    @BindView(R.id.tv_edit_signature)
    TextView tv_edit_signature;//编辑个性签名
    @BindView(R.id.tv_payment_today)
    TextView tv_payment_today;
    @BindView(R.id.tv_payment_week)
    TextView tv_payment_week;
    @BindView(R.id.tv_receive_payment)
    TextView tv_receive_payment;
    @BindView(R.id.tv_entry_num)
    TextView tv_entry_num;
    @BindView(R.id.tv_new_customer)
    TextView tv_new_customer;
    @BindView(R.id.include_receive_payment)
    LinearLayout include_receive_payment;

    private Context context;
    private RealTimeArrivalListAdapter arrivalListAdapter;
    private ColleagueListAdapter colleagueListAdapter;
    private ProjectListAdapter mProjectListAdapter;
    private String token;

    private IDynamicsUnRead iDynamicsUnRead;
    private IAchievement iAchievement;
    private IReceivePay iReceivePay;

    private Map<String, String> request1 = new HashMap<>();
    private Map<String, String> request3 = new HashMap<>();
    private Map<String, String> request4 = new HashMap<>();

    private List<HomeProjectInfo> pictures;
    private int[] mProjectImg;
    private String[] mProjectName;
    private int[] mProjectItemId;

    public HomeFragment() {
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getActivity();
        tv_header_name.setText(SPUtil.loadName(context) + " , " + TimeUtil.getAPM() + "好!");
        pictures = new ArrayList<HomeProjectInfo>();

        tv_signature.setText(SPUtil.loadUserSignature(context));
        mProjectImg = new int[]{R.mipmap.iconclien, R.mipmap.iconmanag, R.mipmap.icontacke,
                R.mipmap.iconperformanc, R.mipmap.iconlesson, R.mipmap.iconcheckingin,
                R.mipmap.iconsuccessfulcase, R.mipmap.icondynamic, R.mipmap.icongongdan, R.mipmap.iconnews, R.mipmap.iconreminding};
        mProjectName = new String[]{"公共客户", "推荐管理", "发票管理", "绩效审核", "课程培训", "我的考勤", "成功案例", "信息互动", "我的工单", "公司新闻", "事务提醒"};
        mProjectItemId = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        for (int i = 0; i < mProjectImg.length; i++) {
            HomeProjectInfo picture = new HomeProjectInfo(mProjectName[i], mProjectImg[i], mProjectItemId[i], 0);
            pictures.add(picture);
        }

        token = SPUtil.loadToken(context);
        iDynamicsUnRead = new DynamicsUnReadImpl();
        iAchievement = new AchievementImpl();
        iReceivePay = new ReceivePayImpl();

        //设置布局管理器
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        GridLayoutManager linearLayoutManager3 = new GridLayoutManager(context, 4);

        mProjectListAdapter = new ProjectListAdapter(context, pictures);

        rvRealTimeArrival.setLayoutManager(linearLayoutManager1);
        rvColleague.setLayoutManager(linearLayoutManager2);
        gvApplicationProject.setLayoutManager(linearLayoutManager3);

        rvRealTimeArrival.setAdapter(arrivalListAdapter);
        rvColleague.setAdapter(colleagueListAdapter);
        gvApplicationProject.setAdapter(mProjectListAdapter);

        rv_percent.setArcWidth(100);
        rv_percent.setText("0%");
        rv_percent.setContext("完成率");
        tv_payment_today.setSelected(true);
        tv_payment_week.setSelected(false);
        onItemProjectClick();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            requestServer();
        }
    }

    @OnClick({R.id.ll_resume_search, R.id.ll_my_custom, R.id.ll_my_position, R.id.ll_call_phone, R.id.ll_trans_remind,
            R.id.tv_edit_signature, R.id.tv_payment_today, R.id.tv_payment_week})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_resume_search://简历搜索
                ResumeSearchActivity.StartAction(context);
                break;
            case R.id.ll_my_custom://我的客户
                CustomerSearchActivity.StartAction(context);
                break;
            case R.id.ll_my_position://我的职位
                PositionSearchActivity.StartAction(context);
                break;
            case R.id.ll_call_phone://拨打电话
                if (TextUtils.isEmpty(SPUtil.loadMobileNumber(context)) && TextUtils.isEmpty(SPUtil.loadTelNumber(context))) {
                    DialogUtil.getInstance().showMsgDialog(context, "请退出重新登录");
                } else {
                    CallActivity.startAction(context, "");
                }
                break;
            case R.id.ll_trans_remind:
//                RemindingActivity.StartAction(context);
                ToastUtil.showImgMessage(context,"提醒模块正在开发");
                break;
            case R.id.tv_edit_signature:
                Intent intent = new Intent(context, SignatureActivity.class);
                startActivityForResult(intent, 0x01);
                break;
            case R.id.tv_payment_today:
                tv_payment_today.setSelected(true);
                tv_payment_week.setSelected(false);
                request1.put("StaffId", String.valueOf(SPUtil.loadId(context)));
                request1.put("tag", "0");
                iReceivePay.iReceivePayRequest(token, request1, URLConstant.URL_RETURNMONEY, this);
                break;
            case R.id.tv_payment_week:
                tv_payment_today.setSelected(false);
                tv_payment_week.setSelected(true);
                request1.put("StaffId", String.valueOf(SPUtil.loadId(context)));
                request1.put("tag", "1");
                iReceivePay.iReceivePayRequest(token, request1, URLConstant.URL_RETURNMONEY, this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        request3.put("staffId", String.valueOf(SPUtil.loadId(context)));
        iDynamicsUnRead.dynamicsUnReadRequest(token, request3, URLConstant.URL_GET_TOP_INTERACTION_V2, this);
    }

    private void requestServer() {
        request3.put("staffId", String.valueOf(SPUtil.loadId(context)));
        iDynamicsUnRead.dynamicsUnReadRequest(token, request3, URLConstant.URL_GET_TOP_INTERACTION_V2, this);

        request4.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        request4.put("AssessmentYear", String.valueOf(TimeUtil.getYear()));
        request4.put("AssessmentQuarter", String.valueOf(TimeUtil.getQuarter()));
        iAchievement.iAchievementRequest(token, request4, URLConstant.URL_GETPERFORMANCE_PERCENTAGE, this);

        request1.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        if (tv_payment_today.isSelected()) {
            request1.put("tag", "0");
        } else {
            request1.put("tag", "1");
        }
        iReceivePay.iReceivePayRequest(token, request1, URLConstant.URL_RETURNMONEY, this);
    }

    private void onItemProjectClick() {
        mProjectListAdapter.setOnItemClickListener(new ProjectListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (mProjectItemId[position]) {
                    case 1://公共客户
                        PublicCustomerActivity.StartAction(context);
                        break;
                    case 2://推荐管理
                        ReferenceManageActivity.StartAction(context);
                        break;
                    case 3://发票管理
                        InvoiceManageActivity.StartAction(context);
                        break;
                    case 4://绩效审核
                        PerformanceManageActivity.StartAction(context);
                        break;
                    case 5://课程培训
                        MyCourseActivity.StartAction(context);
                        break;
                    case 6://我的考勤
                        MyAttendanceActivity.StartAction(context);
                        break;
                    case 7://成功案例
                        SuccessCaseMainActivity.startAction(context);
                        break;
                    case 8://我的绩效
                        DynamicsActivity.StartAction(context, false);
                        break;
                    case 9://工单
                        WorkOrderActivity.StartAction(context);
                        break;
                    case 10://新闻
                        NewsMainActivity.StartAction(context);
                        break;
                    case 11://提醒
                        RemindingActivity.StartAction(context);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onUnReadSuccess(final Object obj) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //动态，工单未接收，新闻 未读数提示
                if (obj instanceof DynamicsResponse) {
                    DynamicsResponse response = (DynamicsResponse) obj;
                    pictures.get(7).setNumber(response.getRecordCount());
                    pictures.get(8).setNumber(response.getOrderUnreadCount());
                    pictures.get(9).setNumber(response.getArticleCount());
                    mProjectListAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void onUnReadFailed(String str) {

    }

    @Override
    public void onUnReadError(String str) {

    }

    /**
     * 业绩完成率
     *
     * @param obj
     */
    @Override
    public void onAchievementSuccess(final Object obj) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (obj != null) {
                    if (obj instanceof AchievementResponse) {
                        AchievementResponse response = (AchievementResponse) obj;
                        if (response.getData() == null) {
                            return;
                        }
                        NumberFormat nf = NumberFormat.getPercentInstance();
                        try {
                            Number str = nf.parse(response.getData().getPercent());
                            rv_percent.setPercent(Float.parseFloat(str.toString()) * 100);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        rv_percent.setText(response.getData().getPercent());
                        rv_percent.setContext(response.getData().getQuarterStr() + "完成率");
                        tv_completed.setText(response.getData().getPerformanceAmount());
                        tv_incompleted.setText(response.getData().getUnfinishedPerformance());
                    }
                }
            }
        });
    }

    @Override
    public void onAchievementFailed(String str) {

    }

    @Override
    public void onAchievementError(String str) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x01 && resultCode == 0x02) {
            String signature_str = data.getExtras().getString("edit_signature");
            if (signature_str == null) {
                return;
            } else {
                SPUtil.saveUserSignature(context, signature_str.equals("") ? "让每一个为梦想奋斗的人,更幸福的生活!" : signature_str);
                tv_signature.setText(SPUtil.loadUserSignature(context));
            }
        }
    }

    @Override
    public void onReceivePaySuccess(final Object obj) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (obj != null) {
                    if (obj instanceof ReturnPayResponse) {
                        ReturnPayResponse response = (ReturnPayResponse) obj;
                        if (response.isStatus()) {
                            include_receive_payment.setVisibility(View.VISIBLE);
                            if (response.getData() != null) {
                                tv_receive_payment.setText(response.getData().getHuikuanCurrent() == null ? "¥0" : response.getData().getHuikuanCurrent());
                                tv_entry_num.setText(response.getData().getRuzhiCurrent() + "");
                                tv_new_customer.setText(response.getData().getQianyueCurrent() + "");
                            }
                        } else {
                            include_receive_payment.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onReceivePayFailed(String str) {

    }

    @Override
    public void onReceivePayError(String str) {

    }
}
