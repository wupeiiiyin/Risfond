package com.risfond.rnss.home.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.google.gson.Gson;
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
import com.risfond.rnss.home.Bizreader.Bizreader_Bean.BizreaderBean;
import com.risfond.rnss.home.Bizreader.Bizreader_Bean.Bizreader_Edit_Bean;
import com.risfond.rnss.home.Bizreader.FileUtil;
import com.risfond.rnss.home.Bizreader.RecognizeService;
import com.risfond.rnss.home.Bizreader_Activity.Bizreader_Activity;
import com.risfond.rnss.home.Bizreader_Activity.FirstEvent;
import com.risfond.rnss.home.adapter.ColleagueListAdapter;
import com.risfond.rnss.home.adapter.ProjectListAdapter;
import com.risfond.rnss.home.adapter.RealTimeArrivalListAdapter;
import com.risfond.rnss.home.call.activity.CallActivity;
import com.risfond.rnss.home.callback.AchievementCallback;
import com.risfond.rnss.home.callback.Bizreader_Callback;
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
import com.risfond.rnss.home.commonFuctions.reminding.activity.DetailsTimeActivity;
import com.risfond.rnss.home.commonFuctions.reminding.activity.RemindingActivity;
import com.risfond.rnss.home.commonFuctions.reminding.activity.TransactiondatabaseSQL;
import com.risfond.rnss.home.commonFuctions.reminding.adapter.MyHomeRemindListAdapter;
import com.risfond.rnss.home.commonFuctions.reminding.utils.DateUtilBean;
import com.risfond.rnss.home.commonFuctions.reminding.utils.TestDate;
import com.risfond.rnss.home.commonFuctions.reminding.view.MySwipeMenuListview;
import com.risfond.rnss.home.commonFuctions.successCase.activity.SuccessCaseMainActivity;
import com.risfond.rnss.home.commonFuctions.workorder.activity.WorkOrderActivity;
import com.risfond.rnss.home.customer.activity.CustomerSearchActivity;
import com.risfond.rnss.home.modleImpl.AchievementImpl;
import com.risfond.rnss.home.modleImpl.Bizreader_mpl;
import com.risfond.rnss.home.modleImpl.DynamicsUnReadImpl;
import com.risfond.rnss.home.modleImpl.ReceivePayImpl;
import com.risfond.rnss.home.modleInterface.IAchievement;
import com.risfond.rnss.home.modleInterface.IDynamicsUnRead;
import com.risfond.rnss.home.modleInterface.IReceivePay;
import com.risfond.rnss.home.position.activity.PositionSearchActivity;
import com.risfond.rnss.home.resume.activity.ResumeSearchActivity;
import com.risfond.rnss.home.signature.SignatureActivity;
import com.risfond.rnss.widget.ProgressView;
import com.risfond.rnss.widget.ScrollInterceptScrollView;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 主页
 */
public class HomeFragment extends BaseFragment implements DynamicsUnReadCallback, AchievementCallback,
        ReceivePayCallback, Bizreader_Callback {
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
    @BindView(R.id.pv_achievement)
    ProgressView pv_achievement;
    @BindView(R.id.tv_achievement_rate)
    TextView tv_achievement_rate;
    @BindView(R.id.tv_achievement_rate2)
    TextView tv_achievement_rate2;
    @BindView(R.id.tv_achievement_have)
    TextView tv_achievement_have;
    @BindView(R.id.tv_achievement_nohave)
    TextView tv_achievement_no;
    @BindView(R.id.tv_completed)
    TextView tv_completed;
    @BindView(R.id.tv_incompleted)
    TextView tv_incompleted;
    @BindView(R.id.tv_signature)
    TextView tv_signature;//头部个性签名撒
    @BindView(R.id.tv_edit_signature)
    TextView tv_edit_signature;//编辑个性签名sadsad撒旦撒旦撒旦的污染法人个人提供染发第三方斯蒂芬斯蒂芬
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
    @BindView(R.id.scroll_view)
    public ScrollInterceptScrollView mScrollView;
    @BindView(R.id.remind_lin)
    LinearLayout remindLin;
    @BindView(R.id.list_reminding_item)
    MySwipeMenuListview listRemindingItem;
    @BindView(R.id.list)
    LinearLayout list;
    @BindView(R.id.rl_achievement)
    RelativeLayout rlAchievement;
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
    Unbinder unbinder;


    private Context context;
    private RealTimeArrivalListAdapter arrivalListAdapter;
    private ColleagueListAdapter colleagueListAdapter;
    private ProjectListAdapter mProjectListAdapter;
    private String token;

    private IDynamicsUnRead iDynamicsUnRead;
    private IAchievement iAchievement;
    private IReceivePay iReceivePay;
    private Map<String, String> request = new HashMap<>();
    private Map<String, String> request1 = new HashMap<>();
    private Map<String, String> request3 = new HashMap<>();
    private Map<String, String> request4 = new HashMap<>();
    private static final int REQUEST_CODE_GENERAL_BASIC = 106;
    private List<HomeProjectInfo> pictures;
    private int[] mProjectImg;
    private String[] mProjectName;
    private int[] mProjectItemId;
    private boolean hasGotToken;
    private AlertDialog.Builder alertDialog;
    private Thread mUiThread;
    private String mTitle1;
    private String mMessage1;
    private boolean isLoadMore;
    private BizreaderBean.DataBean data;
    //名片数据
    private List<String> keywords;
    private int cardId;
    private String qq;
    private String weXin;
    private String email;
    private String address;
    private String company;
    private String job;
    private String mobilePhone;
    private String name;
    private Bizreader_mpl mBizreader_mpl;
    private String mToken;
    private TransactiondatabaseSQL ttdbsqlite;
    private Cursor c;
    private String cursorString1;
    private MyHomeRemindListAdapter Adapter;
    private List<String> list_positionSearches = new ArrayList();
    private List<String> list_positionSearches_time = new ArrayList();
    private Map<String, Object> map;
    private List<Integer> ids = new ArrayList<>();
    private int id;
    List<String> times = new ArrayList<>();
    List<String> descs = new ArrayList<>();
    boolean flag = false;
    private String sj;
    private ArrayList<TestDate> testDates;


    public HomeFragment() {
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;

    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getActivity();
        initAdapter();
        DialogUtil.getInstance().closeLoadingDialog();
        tv_header_name.setText(SPUtil.loadEnglishName(context) + " , " + TimeUtil.getAPM() + "好!");
        pictures = new ArrayList<HomeProjectInfo>();
        alertDialog = new AlertDialog.Builder(context);
        // 请选择您的初始化方式获取Token
        initAccessToken();
//        initAccessTokenWithAkSk();

        tv_signature.setText(SPUtil.loadUserSignature(context));
        mProjectImg = new int[]{R.mipmap.iconclien, R.mipmap.iconmanag, R.mipmap.icontacke,
                R.mipmap.iconperformanc, R.mipmap.iconlesson, R.mipmap.iconcheckingin,
                R.mipmap.iconsuccessfulcase, R.mipmap.icondynamic, R.mipmap.icongongdan, R.mipmap.iconnews, R.mipmap.iconreminding, R.mipmap.giconanalyze};
        mProjectName = new String[]{"公共客户", "推荐管理", "发票管理", "绩效审核", "课程培训", "我的考勤", "成功案例", "信息互动", "我的工单", "公司新闻", "事务提醒", "名片扫描"};
        mProjectItemId = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
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

        pv_achievement.setProgress(0);
        tv_payment_today.setSelected(true);
        tv_payment_week.setSelected(false);
        onItemProjectClick();


    }
    //将List按照时间倒序排列
    @SuppressLint("SimpleDateFormat")
    private List<TestDate> invertOrderList(List<TestDate> L){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1;
        Date d2;
        TestDate temp_r = new TestDate();
        //做一个冒泡排序，大的在数组的前列
        for(int i=0; i<L.size()-1; i++){
            for(int j=i+1; j<L.size();j++){
                ParsePosition pos1 = new ParsePosition(0);
                ParsePosition pos2 = new ParsePosition(0);
                d1 = sdf.parse(L.get(i).getDate(), pos1);
                d2 = sdf.parse(L.get(j).getDate(), pos2);
                if(d1.before(d2)){//如果队前日期靠前，调换顺序
                    temp_r = L.get(i);
                    L.set(i, L.get(j));
                    L.set(j, temp_r);
                }
            }
        }
        return L;
    }
    private void initAdapter() {
        ttdbsqlite = new TransactiondatabaseSQL(getActivity().getApplication());
        c = ttdbsqlite.checktransaction();
        c.moveToFirst();
        testDates = new ArrayList<>();
        while (c.moveToNext()) {
            id = c.getInt(c.getColumnIndex("_id"));
            //内容
            cursorString1 = c.getString(c.getColumnIndex("name"));
            Log.e("TAG+cursorString1", "initAdapter: "+cursorString1 );
            Log.e("TAG+id", "initAdapter: "+id );

            String time = c.getString(c.getColumnIndex("time"));//时间 年月日时分
            Log.e("TAG+time", "initAdapter: "+time );
            testDates.add(new TestDate(time, cursorString1));
        }
        if (testDates.size() != 0) {//-1
            remindLin.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
           // invertOrderList(testDates);
            Collections.sort(testDates, new Comparator<TestDate>() {
                /**
                 *
                 * @param lhs
                 * @param rhs
                 * @return an integer < 0 if lhs is less than rhs, 0 if they are
                 *         equal, and > 0 if lhs is greater than rhs,比较数据大小时,这里比的是时间
                 */
                @Override
                public int compare(TestDate lhs, TestDate rhs) {
                    Date date1 = DateUtilBean.stringToDate(lhs.getDate());
                    Date date2 = DateUtilBean.stringToDate(rhs.getDate());
                    // 对日期字段进行升序，如果欲降序可采用after方法
                    if (date1.before(date2)) {
                        return 1;
                    }
                    return -1;
                }
            });

            Log.e("TAG+size", "initAdapter: "+testDates.size() );
            testDates.size();
            MyHomeRemindListAdapter myHomeRemindListAdapter = new MyHomeRemindListAdapter(getContext(), testDates);
            listRemindingItem.setAdapter(myHomeRemindListAdapter);
            listRemindingItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getContext(), DetailsTimeActivity.class);
                    intent.putExtra("position",i);
                    intent.putExtra("tv_itemcontent", testDates.get(i).getName());
                    intent.putExtra("tv_itemtime", testDates.get(i).getDate());
                    startActivity(intent);
                }
            });
        } else if (list_positionSearches.size() == 0) {
            remindLin.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        }

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
                Intent intent1 = new Intent(context, RemindingActivity.class);
                startActivityForResult(intent1, 1);
                // TODO: 2018/4/3
                //                ToastUtil.showImgMessage(context,"提醒模块正在开发");
                break;
            case R.id.tv_edit_signature:
                Log.i("TAG", "HAHHHHHHHH ");

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
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initAdapter();
        Calendar c = Calendar.getInstance();//
        int YEAR = c.get(Calendar.YEAR);// 获取当前年份
        int month = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int day = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        sj = YEAR + "-" + month + "-" + day;
//        mToken = "o7g6gyZ2vkS7DM1s2bZAxWdA";
//        request3.put("staffId", "2");
        request3.put("staffId", String.valueOf(SPUtil.loadId(context)));
//        iDynamicsUnRead.dynamicsUnReadRequest(token, request3, URLConstant.URL_GET_TOP_INTERACTION_V3, this);
        iDynamicsUnRead.dynamicsUnReadRequest(token, request3, URLConstant.URL_GET_TOP_INTERACTION_V3, this);

    }

    private void requestServer() {
        request3.put("staffId", String.valueOf(SPUtil.loadId(context)));
        iDynamicsUnRead.dynamicsUnReadRequest(token, request3, URLConstant.URL_GET_TOP_INTERACTION_V2, this);

        request4.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        request4.put("AssessmentYear", String.valueOf(TimeUtil.getYear()));
        request4.put("AssessmentQuarter", String.valueOf(TimeUtil.getQuarter()));
        //        request4.put("StaffId", "6020");
        //        request4.put("AssessmentYear", "2017");
        //        request4.put("AssessmentQuarter", "4");
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
                    case 12://名片扫描
                        if (!checkTokenStatus()) {
                            return;
                        }
//                        DialogUtil.getInstance().showLoadingDialog(getContext(),"文字识别中..");
//                        ToastUtil.showShort();
                        //Toast.makeText(context, "文字识别中", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, CameraActivity.class);

                        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                                FileUtil.getSaveFile(context).getAbsolutePath());
                        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                                CameraActivity.CONTENT_TYPE_GENERAL);
                        startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);


                        break;
                    default:
                        break;
                }
            }
        });
    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(context, "token还未成功获取,请重新打开App", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    private void initAccessToken() {
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {


            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("licence方式获取token失败", error.getMessage());
            }
        }, context);
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, getContext(), "PYIP4I9PawipWtDm5EhgpEsB", "2PiHF3pcGUwGXIqmXH8faDSnMrQfyuXw");
    }

    private void alertText(final String title, final String message) {
        Log.e("TAG", "alertText: " + message);
        if (message != null) {
            Gson gson = new Gson();
            Bizreader_Edit_Bean bizreader_edit_bean = gson.fromJson(message, Bizreader_Edit_Bean.class);
            mBizreader_mpl = new Bizreader_mpl();
            mToken = SPUtil.loadToken(getContext());
            request.put("Staffid", String.valueOf(SPUtil.loadId(getContext())));
            request.put("Data", new Gson().toJson(bizreader_edit_bean));
            mBizreader_mpl.BizreaderRequest(URLConstant.URL_BIZREADER, request, mToken, this);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showShort(getActivity(), "文字扫描信息为空!");
                }
            });
        }

//        Log.e("TAG", "alertText:================= "+data );
//        mTitle1 = title;
//        mMessage1 = message;
//        this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                alertDialog.setTitle(title)
//                        .setMessage(message)
//                        .setPositiveButton("确定", null)
//                        .show();
//            }
//        });
    }

    final Handler mHandler = new Handler();

    private void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != mUiThread) {
            mHandler.post(action);
        } else {
            action.run();
        }
    }

    private void infoPopText(final String result) {
        alertText("", result);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initAccessToken();
        } else {
            Toast.makeText(context, "需要android.permission.READ_PHONE_STATE", Toast.LENGTH_LONG).show();
        }
    }

    //    @Override
    //    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        super.onActivityResult(requestCode, resultCode, data);
    //
    //
    //
    //
    //    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance().release();
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onUnReadSuccess(final Object obj) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "run:动态，工单未接收，新闻 未读数提示 " + obj.toString());
                //动态，工单未接收，新闻 未读数提示
                if (obj instanceof DynamicsResponse) {
                    DynamicsResponse response = (DynamicsResponse) obj;
//                    pictures.get(5).setNumber(50);
                    pictures.get(5).setNumber(response.getAttendanceCount());
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
                            pv_achievement.setProgress(Float.parseFloat(str.toString()) * 100);
                            tv_achievement_rate.setText(response.getData().getPercent());
                            tv_achievement_rate2.setText(response.getData().getQuarterStr() + "完成率");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tv_achievement_have.setText(response.getData().getPerformanceAmount());
                        tv_achievement_no.setText(response.getData().getUnfinishedPerformance() + "(万)");
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

        // 识别成功回调，通用文字识别
        if (requestCode == REQUEST_CODE_GENERAL_BASIC && resultCode == Activity.RESULT_OK) {

            DialogUtil.getInstance().showLoadingDialog(getContext(), "正在识别文字");
            RecognizeService.recGeneralBasic(FileUtil.getSaveFile(context).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            DialogUtil.getInstance().showLoadingDialog(getContext(), "识别成功");
                            infoPopText(result);
                            Intent intent = new Intent(context, Bizreader_Activity.class);
                            intent.putExtra("title", mTitle1);
                            intent.putExtra("message", mMessage1);

                            startActivity(intent);
                            DialogUtil.getInstance().closeLoadingDialog();
                        }
                    });
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
    //名片扫描

    /***
     * 名片识别获取的接口信息
     * @param obj
     */
    @Override
    public void onBizreaderSuccess(final Object obj) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.e("TAG", "tttttttttttttt成功ttttttttttttttt " + "=========================");
                if (obj != null) {
                    if (obj instanceof BizreaderBean) {
                        BizreaderBean response = (BizreaderBean) obj;
                        if (response.getData() == null) {
                            Log.i("TAG", "空的.................");

                        } else {
                            Log.i("TAG", "不是空的................." + (response.getData().getName()));

                        }

                        data = response.getData();
                        //姓名
                        name = data.getName();
                        //电话
                        mobilePhone = data.getMobilePhone();
                        //职位
                        job = (String) data.getJob();
                        //公司
                        company = data.getCompany();
                        //地址
                        address = data.getAddress();
                        //邮箱
                        email = data.getEmail();
                        //微信
                        weXin = data.getWeXin();
                        //QQ
                        qq = data.getQQ();
                        //关键词列表\
                        keywords = data.getKeywords();
                        //名片ID
                        cardId = data.getCardId();

                        EventBus.getDefault().postSticky(new FirstEvent(data));

                        Log.e("TAG", mobilePhone + "ttttttttttttttttttttttttttttt " + name + address + "=========================" + company);
                    }
                } else {

                    Log.e("TAG", "tttttttttttttt成功ttttttttttttttt " + "===========obj是空的......" +
                            "==============");
                }
            }
        });
    }

    @Override
    public void onBizreaderFailed(String str) {
        Log.e("TAG", "onBizreaderFailed+6++++++++请求失败");
        isLoadMore = true;
    }

    @Override
    public void onBizreaderError(String str) {
        Log.e("TAG", "onBizreaderError 请求错误");
        isLoadMore = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
