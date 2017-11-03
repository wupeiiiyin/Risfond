package com.risfond.rnss.home.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.TimeUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.contacts.activity.ContactsInfoActivity;
import com.risfond.rnss.entry.Colleague;
import com.risfond.rnss.entry.DynamicsResponse;
import com.risfond.rnss.entry.HomeProjectInfo;
import com.risfond.rnss.entry.OperationPlatform;
import com.risfond.rnss.entry.TimeArrival;
import com.risfond.rnss.home.adapter.ColleagueListAdapter;
import com.risfond.rnss.home.adapter.ProjectListAdapter;
import com.risfond.rnss.home.adapter.RealTimeArrivalListAdapter;
import com.risfond.rnss.home.call.activity.CallActivity;
import com.risfond.rnss.home.callback.ColleagueCallback;
import com.risfond.rnss.home.callback.DynamicsUnReadCallback;
import com.risfond.rnss.home.callback.ITimeArrivalCallback;
import com.risfond.rnss.home.callback.OperationPlatformCallback;
import com.risfond.rnss.home.commonFuctions.invoiceManage.activity.InvoiceManageActivity;
import com.risfond.rnss.home.commonFuctions.myAttenDance.activity.MyAttendanceActivity;
import com.risfond.rnss.home.commonFuctions.myCourse.activity.MyCourseActivity;
import com.risfond.rnss.home.commonFuctions.news.activity.NewsMainActivity;
import com.risfond.rnss.home.commonFuctions.performanceManage.activity.PerformanceManageActivity;
import com.risfond.rnss.home.commonFuctions.dynamics.activity.DynamicsActivity;
import com.risfond.rnss.home.commonFuctions.publicCustomer.activity.PublicCustomerActivity;
import com.risfond.rnss.home.commonFuctions.referencemanage.activity.ReferenceManageActivity;
import com.risfond.rnss.home.commonFuctions.successCase.activity.SuccessCaseActivity;
import com.risfond.rnss.home.commonFuctions.workorder.activity.WorkOrderActivity;
import com.risfond.rnss.home.customer.activity.CustomerSearchActivity;
import com.risfond.rnss.home.modleImpl.ColleagueImpl;
import com.risfond.rnss.home.modleImpl.DynamicsUnReadImpl;
import com.risfond.rnss.home.modleImpl.OperationPlatformImpl;
import com.risfond.rnss.home.modleImpl.TimeArrivalImpl;
import com.risfond.rnss.home.modleInterface.IColleague;
import com.risfond.rnss.home.modleInterface.IDynamicsUnRead;
import com.risfond.rnss.home.modleInterface.IOperationPlatform;
import com.risfond.rnss.home.modleInterface.ITimeArrival;
import com.risfond.rnss.home.position.activity.PositionSearchActivity;
import com.risfond.rnss.home.resume.activity.ResumeSearchActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 */
public class HomeFragment extends BaseFragment implements OperationPlatformCallback, ITimeArrivalCallback,
        ColleagueCallback, DynamicsUnReadCallback {
    private static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.tv_today)
    TextView tvToday;
    @BindView(R.id.payment)
    TextView payment;
    @BindView(R.id.entry)
    TextView entry;
    @BindView(R.id.offer)
    TextView offer;
    @BindView(R.id.sing)
    TextView sing;
    @BindView(R.id.resume)
    TextView resume;
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

    private Context context;
    private RealTimeArrivalListAdapter arrivalListAdapter;
    private ColleagueListAdapter colleagueListAdapter;
    private ProjectListAdapter mProjectListAdapter;
    private String token;

    private IColleague iColleague;
    private IOperationPlatform iOperationPlatform;
    private ITimeArrival iTimeArrival;
    private IDynamicsUnRead iDynamicsUnRead;
    private Map<String, String> request1 = new HashMap<>();
    private Map<String, String> request2 = new HashMap<>();
    private Map<String, String> request3 = new HashMap<>();

    private OperationPlatform operationPlatform;
    private List<TimeArrival> timeArrivals;
    private List<Colleague> colleagues;
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
        timeArrivals = new ArrayList<>();
        colleagues = new ArrayList<>();
        pictures = new ArrayList<HomeProjectInfo>();

        mProjectImg = new int[]{R.mipmap.iconclien, R.mipmap.iconmanag, R.mipmap.icontacke,
                R.mipmap.iconperformanc, R.mipmap.iconlesson, R.mipmap.iconcheckingin,
                R.mipmap.iconsuccessfulcase, R.mipmap.icondynamic, R.mipmap.icongongdan, R.mipmap.iconnews};
        mProjectName = new String[]{"公共客户", "推荐管理", "发票管理", "绩效审核", "课程培训", "我的考勤", "成功案例", "动态", "工单", "新闻"};
        mProjectItemId = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < mProjectImg.length; i++) {
            HomeProjectInfo picture = new HomeProjectInfo(mProjectName[i], mProjectImg[i], mProjectItemId[i], 0);
            pictures.add(picture);
        }

        token = SPUtil.loadToken(context);
        iOperationPlatform = new OperationPlatformImpl();
        iTimeArrival = new TimeArrivalImpl();
        iColleague = new ColleagueImpl();
        iDynamicsUnRead = new DynamicsUnReadImpl();

        //设置布局管理器
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        GridLayoutManager linearLayoutManager3 = new GridLayoutManager(context, 4);

        arrivalListAdapter = new RealTimeArrivalListAdapter(context, timeArrivals);
        colleagueListAdapter = new ColleagueListAdapter(context, colleagues);
        mProjectListAdapter = new ProjectListAdapter(context, pictures);

        rvRealTimeArrival.setLayoutManager(linearLayoutManager1);
        rvColleague.setLayoutManager(linearLayoutManager2);
        gvApplicationProject.setLayoutManager(linearLayoutManager3);

        rvRealTimeArrival.setAdapter(arrivalListAdapter);
        rvColleague.setAdapter(colleagueListAdapter);
        gvApplicationProject.setAdapter(mProjectListAdapter);

        onItemColleagueClick();
        onItemProjectClick();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            requestServer();
        }
    }

    @OnClick({R.id.ll_resume_search, R.id.ll_my_custom, R.id.ll_my_position, R.id.ll_call_phone})
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
        request1.put("dateCurrentStart", TimeUtil.getTodayTime());
        request1.put("dateCurrentEnd", TimeUtil.getNextDayTime());
        iOperationPlatform.operationPlatformRequest(token, request1, URLConstant.URL_OPERATION_PLATFORM, this);

        iTimeArrival.timeArrivalRequest(token, null, URLConstant.URL_TIME_ARRIVAL_LIST, this);

        request2.put("companyid", String.valueOf(SPUtil.loadCompanyId(context)));
        iColleague.colleagueRequest(token, request2, URLConstant.URL_LONG_HU, this);

        request3.put("staffId", String.valueOf(SPUtil.loadId(context)));
        iDynamicsUnRead.dynamicsUnReadRequest(token, request3, URLConstant.URL_GET_TOP_INTERACTION_V2, this);

    }

    private void onItemColleagueClick() {
        colleagueListAdapter.setOnItemClickListener(new ColleagueListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ContactsInfoActivity.startAction(context, String.valueOf(colleagues.get(position).getStaffId()));
            }
        });
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
                        SuccessCaseActivity.StartAction(context);
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
    public void onColleagueSuccess(final Object obj) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (obj instanceof ArrayList) {
                    colleagues = (List<Colleague>) obj;
                    colleagueListAdapter.updateData(colleagues);
                }
            }
        });
    }

    @Override
    public void onColleagueFailed(String str) {
        Log.d(TAG + "ColleagueFailed", str);
    }

    @Override
    public void onColleagueError(String str) {
        Log.d(TAG + "ColleagueError", str);
    }

    @Override
    public void onTimeArrivalSuccess(final Object obj) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (obj instanceof ArrayList) {
                    timeArrivals = (List<TimeArrival>) obj;
                    arrivalListAdapter.updateData(timeArrivals);
                }
            }
        });
    }

    @Override
    public void onTimeArrivalFailed(String str) {
        Log.d(TAG + "TimeArrivalFailed", str);
    }

    @Override
    public void onTimeArrivalError(String str) {
        Log.d(TAG + "TimeArrivalError", str);
    }

    @Override
    public void onOperationPlatformSuccess(final Object obj) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (obj instanceof OperationPlatform) {
                    operationPlatform = (OperationPlatform) obj;
                    payment.setText(NumberUtil.payment(operationPlatform.getHuikuanCurrent()));
                    entry.setText(String.valueOf(operationPlatform.getRuzhiCurrent()));
                    offer.setText(String.valueOf(operationPlatform.getOfferCurrent()));
                    sing.setText(String.valueOf(operationPlatform.getQianyueCurrent()));
                    resume.setText(String.valueOf(operationPlatform.getResumeCurrent()));
                    tvToday.setText(TimeUtil.getTodayTime());
                }
            }
        });
    }

    @Override
    public void onOperationPlatformFailed(final String str) {
        Log.d(TAG + "OperationPlatformFailed", str);
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (str.equals(PropertiesUtil.getMessageTextByCode("201"))) {
                    DialogUtil.getInstance().showToLoginDialog(context);
                }
            }
        });

    }

    @Override
    public void onOperationPlatformError(String str) {
        Log.d(TAG + "OperationPlatformError", str);
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
}
