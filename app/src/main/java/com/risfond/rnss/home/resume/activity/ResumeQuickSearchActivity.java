package com.risfond.rnss.home.resume.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.AppDeleteQuery;
import com.risfond.rnss.entry.AppSelectQuery;
import com.risfond.rnss.entry.PositionSearch;
import com.risfond.rnss.entry.PositionSearchResponse;
import com.risfond.rnss.entry.ResumeSearch;
import com.risfond.rnss.entry.ResumeSearchDeleteResponse;
import com.risfond.rnss.entry.ResumeSearchHight;
import com.risfond.rnss.entry.ResumeSearchResponse;
import com.risfond.rnss.entry.ResumeSearchSelectResponse;
import com.risfond.rnss.home.commonFuctions.myAttenDance.Util.SystemBarTintManager;
import com.risfond.rnss.home.position.adapter.PositionSearchAdapter;
import com.risfond.rnss.home.position.modelImpl.PositionSearchImpl;
import com.risfond.rnss.home.position.modelInterface.IPositionSearch;
import com.risfond.rnss.home.resume.adapter.ResumeQuickSearchAdapter;
import com.risfond.rnss.home.resume.adapter.ResumeSearchAdapter;
import com.risfond.rnss.home.resume.modleImpl.ResumeSearchDeleteImpl;
import com.risfond.rnss.home.resume.modleImpl.ResumeSearchImpl;
import com.risfond.rnss.home.resume.modleImpl.ResumeSearchSelectImpl;
import com.risfond.rnss.home.resume.modleInterface.IResumeSearch;
import com.risfond.rnss.widget.CustomDecoration;
import com.risfond.rnss.widget.DividerItemDecoration;
import com.risfond.rnss.widget.EmptyRecyclerView;
import com.risfond.rnss.widget.RecycleViewDivider;
import com.risfond.rnss.widget.SelectPicPopupWindow;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 快捷搜索界面
 */
public class ResumeQuickSearchActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;//返回按钮
    @BindView(R.id.ll_empty_quicksearch)
    LinearLayout ll_empty_quicksearch;//暂无职位
    @BindView(R.id.tv_resume_quick_position)
    TextView tv_ResumeQuickPosition;//快捷搜索职位数量
    @BindView(R.id.rv_quick_resume_list)
    RecyclerView recruitmentQuick;
    @BindView(R.id.ll_resume_quick)
    LinearLayout llResumeQuick;//按已有位置搜索控件
    @BindView(R.id.tv_resume_quick_num)
    TextView tvResumeQuickNum;//已有职位数量
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private Context context;
    private List<ResumeSearch> searches = new ArrayList<>();

    private boolean isLoadMore;
    private PositionSearchResponse response;
    private ResumeSearchSelectResponse selectResponse;
    private ResumeSearchDeleteResponse deleteResponse;
    private Map<String, String> request = new HashMap<>();
    private List<PositionSearch> positionSearches = new ArrayList<>();
    private int pageindex = 1;
    private IPositionSearch iPositionSearch;
    private ResumeQuickSearchAdapter adapter;

    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> lists = new ArrayList<>();
    private List<AppSelectQuery> quick = new ArrayList<>();//查询
    private List<AppSelectQuery> quicks_temp = new ArrayList<>();

    private List<ResumeSearchHight> quicks = new ArrayList<>();//查询
    private List<ResumeSearchHight> quicks_temps = new ArrayList<>();//查询

//    private List<AppSelectQuery> quicks = new ArrayList<>();//查询
//    private List<AppSelectQuery> quicks_temps = new ArrayList<>();
    private List<AppDeleteQuery> delete = new ArrayList<>();//删除
    private PopupWindow popupwindow;
    private RecyclerView rvResumePop;
    //自定义的弹出框类
//    SelectPicPopupWindow menuWindow;
    private PositionSearchAdapter padapter;
    private IResumeSearch iResumeSearchDelece;
    private IResumeSearch iResumeSearchSelect;

private List<PositionSearch> temp = new ArrayList<>();
    private LinearLayout llEmptySearchPoP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_resume_quick_search;
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        EmptyRecyclerView emptyRecyclerView = new EmptyRecyclerView(context);
//        recruitmentQuick = (EmptyRecyclerView) findViewById(R.id.rv_quick_resume_list);

//        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);

//        tv_ResumeQuickPosition = (TextView) findViewById(R.id.tv_resume_quick_position);
        context = ResumeQuickSearchActivity.this;
        iPositionSearch = new PositionSearchImpl();
        padapter = new PositionSearchAdapter(context, positionSearches);//弹框的适配器
//        adapter = new ResumeSearchAdapter(context, searches);
        //模拟数据
        iResumeSearchDelece = new ResumeSearchDeleteImpl();//删除
        iResumeSearchSelect = new ResumeSearchSelectImpl();//查询

        adapter = new ResumeQuickSearchAdapter(context, quicks);

        recruitmentQuick.setLayoutManager(new LinearLayoutManager(context));
        //控制分割线的宽度 参数1：上下文，参数2：方向，参数3：分割线高度，参数4：颜色
        recruitmentQuick.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(context, R.color.color_home_stoke_small)));
//        recruitmentQuick.addItemDecoration(new CustomDecoration(this, CustomDecoration.VERTICAL_LIST, R.drawable.list_item_divider));
//        recruitmentQuick.addItemDecoration(new DividerItemDecoration(R.color.color_home_stoke_small,1,LinearLayoutManager.HORIZONTAL,15f,15f));

        recruitmentQuick.setAdapter(adapter);

        if(temp.size()==0){
            tvResumeQuickNum.setText("加载中...");//职位搜索数量
        }

        recruitmentQuick.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int last = manager.findLastCompletelyVisibleItemPosition();
                int totalCount = manager.getItemCount();
                // last >= totalCount - x表示剩余x个item是自动加载，可自己设置
                // dy>0表示向下滑动
                if (isCanLoadMore) {
                    if (last >= totalCount - 5 && dy > 0) {
                        if (!isLoadingMore) {
                            isLoadMore = true;
                            isLoadingMore = true;
                            resumeRequest();
                        }
                    }
                }
            }
        });
        onItemClick();//监听
        resumeRequest();

        llResumeQuick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initmPopupWindowViews();
                popupwindow.showAsDropDown(tvTitle);
                padapter.setOnItemClickListener(new PositionSearchAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ResumeSearchActivity.StartAction(context);//跳转到简历搜索界面
                    }
                });

            }
        });
    }

    private void resumeRequest() {//快速搜索请求
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        request.put("keyword", "");
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("pageindex", String.valueOf(pageindex));//当前页数
        request.put("pageSize", String.valueOf(100));//每页条数
        iResumeSearchSelect.resumeRequest(SPUtil.loadToken(context), request, URLConstant.URL_RESUME_SELECTRESUMEQUERY, this);
    }


    //按已有职位搜索popupWindow弹框
    private void initmPopupWindowViews() {
        // // 获取自定义布局文件pop.xml的视图
        View customView = LayoutInflater.from(ResumeQuickSearchActivity.this).inflate(R.layout.popview_item_search, null);
        rvResumePop = (RecyclerView)customView.findViewById(R.id.rv_resume_pop_list);
        llEmptySearchPoP = (LinearLayout)customView.findViewById(R.id.ll_empty_search_pop);//暂无职位


        rvResumePop.setLayoutManager(new LinearLayoutManager(context));
//        rvResumePop.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(context, R.color.color_home_back)));
        //控制分割线的宽度 参数1：上下文，参数2：方向，参数3：分割线高度，参数4：颜色
        rvResumePop.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_stoke_small)));
        rvResumePop.setAdapter(padapter);

        rvResumePop.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int last = manager.findLastCompletelyVisibleItemPosition();
                int totalCount = manager.getItemCount();
                // last >= totalCount - x表示剩余x个item是自动加载，可自己设置
                // dy>0表示向下滑动
                if (isCanLoadMore) {
                    if (last >= totalCount - 5 && dy > 0) {
                        if (!isLoadingMore) {
                            isLoadMore = true;
                            isLoadingMore = true;
                            positionRequest();
                        }
                    }
                }
            }
        });
//        onItemClick();//两处使用此方法
        positionRequest();

//        //实例化SelectPicPopupWindow
//        menuWindow = new SelectPicPopupWindow(ResumeQuickSearchActivity.this);
//        //显示窗口
//        menuWindow.showAtLocation(tv_resume_quick_total, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置

        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        //        popupwindow.setAnimationStyle(R.style.AnimationFade);
        // 自定义view添加触摸事件
        //        popupwindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupwindow.setFocusable(false);
        popupwindow.setOutsideTouchable(false); // 设置是否允许在外点击使其消失，到底有用没？
//        backgroundAlpha(0.5f);//设置半透明
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupwindow.setBackgroundDrawable(dw);
        popupwindow.setWidth(RecyclerView.LayoutParams.MATCH_PARENT);
        popupwindow.setHeight(RecyclerView.LayoutParams.MATCH_PARENT);
        popupwindow.setAnimationStyle(R.style.mypopwindow_anim_style);
//                popupwindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//                int popupWidth = popupwindow.getContentView().getMeasuredWidth();
//                int popupHeight = popupwindow.getContentView().getMeasuredHeight();
//                // 设置好参数之后再show
//                int[] location = new int[2];
//                customView.getLocationOnScreen(location);
//        popupwindow.showAsDropDown(tvTitle);
//                popupwindow.showAtLocation(ResumeQuickSearchActivity.this.findViewById(R.id.tv_title),  Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, (location[0]+customView.getWidth()/2)-popupWidth/2 , location[1]-popupHeight);
        //ResumeQuickSearchActivity.this.findViewById(R.id.tv_title)


        customView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    popupwindow = null;
                }

                return false;
            }
        });

        /** 在这里可以实现自定义视图的功能 */

    }

    private void onItemClick() {
        //简历列表点击
        adapter.setOnItemClickListener(new ResumeQuickSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                ResumeDetailActivity.startAction(context, String.valueOf(searches.get(position).getId()));
                ResumeSearchActivity.StartAction(context);//跳转到简历搜索界面
            }
        });
        //删除
        adapter.setOnDeClickListener(new ResumeQuickSearchAdapter.OnDeClickListener() {
            @Override
            public void onDeClick(View view, final int position) {
                DialogUtil.getInstance().showConfigDialog(context, "是否删除？", "是", "否", new DialogUtil.PressCallBack() {
                                            @Override
                                            public void onPressButton(int buttonIndex) {
                                                if (buttonIndex == DialogUtil.BUTTON_OK) {
                                                    quicks.remove(quicks.get(position));
                                                    adapter.notifyDataSetChanged();
                                                    tv_ResumeQuickPosition.setText(quicks.size()+"");

                                                    if(quicks.size()==0){
                                                        ll_empty_quicksearch.setVisibility(View.VISIBLE);
                                                        recruitmentQuick.setVisibility(View.GONE);
                                                    }
                                                    AppSelectQuery appSelectQuery = new AppSelectQuery();
                                                    int resumequeryid = appSelectQuery.getResumequeryid();
                                                    int staffid = appSelectQuery.getStaffId();
                                                    AppDeleteQuery appDeleteQuery = new AppDeleteQuery();
                                                    int id = appDeleteQuery.getId();
                                                    int staffId = appDeleteQuery.getStaffId();
                                                    //request.put("keyword", "");
//                                                    request.put("staffid", String.valueOf(SPUtil.loadId(context)));
//                                                    request.put("pageindex", String.valueOf(pageindex));
                                                    request.put("id", String.valueOf(id));
                                                    request.put("staffId", String.valueOf(staffId));
                                                    iResumeSearchDelece.resumeRequest(SPUtil.loadToken(context), request, URLConstant.URL_RESUME_DELETERESUMEQUERY, ResumeQuickSearchActivity.this);

                                                }
                                            }
                                        });
            }
        });
    }


    //跳转界面的方法
    public static void StartAction(Context context) {
        Intent intent = new Intent(context, ResumeQuickSearchActivity.class);
        context.startActivity(intent);
    }
    private void positionRequest() {//popupwindow中请求数据
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        request.put("keyword", "");
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("pageindex", String.valueOf(pageindex));
        iPositionSearch.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_JOB_SEARCH, this);
    }

    @Override
    public void onSuccess(final Object obj) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                //popup弹框的操作
                if (obj instanceof PositionSearchResponse) {
                    response = (PositionSearchResponse) obj;
                    if(tvResumeQuickNum != null){//查询数量
                        tvResumeQuickNum.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
                    }
                    if (response.getData().size() == 15) {
                        pageindex++;
                        isCanLoadMore = true;
                        if (temp.size() > 0) {
                            positionSearches.removeAll(temp);
                            temp.clear();
                        }
                        positionSearches.addAll(response.getData());
                    } else {
                        isCanLoadMore = false;
                        if (temp.size() > 0) {
                            positionSearches.removeAll(temp);
                            temp.clear();
                        }
                        temp = response.getData();
                        positionSearches.addAll(temp);
                    }
                    padapter.updateData(positionSearches);
                }
                if (isLoadMore) {
                    isLoadingMore = false;
                }
                if (positionSearches.size() > 0) {
                    if(llEmptySearchPoP!=null){
                        llEmptySearchPoP.setVisibility(View.GONE);
//                        rvResumePop.setVisibility(View.VISIBLE);
                    }
                    if (rvResumePop != null) {
                        rvResumePop.setVisibility(View.VISIBLE);//显示RecyclerView控件
                    }

                } else {
//                    llEmptySearchPoP.setVisibility(View.VISIBLE);
//                    rvResumePop.setVisibility(View.GONE);
                    if (llEmptySearchPoP != null) {
                        llEmptySearchPoP.setVisibility(View.VISIBLE);//无数据时的布局显示
                    }
                    if (rvResumePop != null) {
                        rvResumePop.setVisibility(View.GONE);//隐藏RecyclerView控件
                    }
                }

//                查询的操作
                if (obj instanceof ResumeSearchSelectResponse) {
                    selectResponse = (ResumeSearchSelectResponse) obj;
                    tv_ResumeQuickPosition.setText(quicks.size()+"");
                    if(tv_ResumeQuickPosition != null){//查询数量
                        tv_ResumeQuickPosition.setText(NumberUtil.formatString(new BigDecimal(selectResponse.getTotal())));
                    }
                    if (selectResponse.getData().size() == 15) {
                        pageindex++;
                        isCanLoadMore = true;
                        if (quicks_temps.size() > 0) {
                            quicks.removeAll(quicks_temps);
                            quicks_temps.clear();
                        }
                        quicks.addAll(selectResponse.getData());
                    } else {
                        isCanLoadMore = false;
                        if (quicks_temps.size() > 0) {
                            quicks.removeAll(quicks_temps);
                            quicks_temps.clear();
                        }
                        quicks_temps = selectResponse.getData();
                        quicks.addAll(quicks_temps);
                    }

                    adapter.updateData(quicks);
                    tv_ResumeQuickPosition.setText(quicks.size()+"");
                }
                if (isLoadMore) {
                    isLoadingMore = false;
                }
                if (quicks.size() > 0) {
                    ll_empty_quicksearch.setVisibility(View.GONE);
                    recruitmentQuick.setVisibility(View.VISIBLE);
                } else {
                    ll_empty_quicksearch.setVisibility(View.VISIBLE);
                    recruitmentQuick.setVisibility(View.GONE);
                }

                //删除
                if (obj instanceof ResumeSearchDeleteResponse) {
                    deleteResponse = (ResumeSearchDeleteResponse) obj;
                    Log.i("TAGs","-86---------------"+quick.toString()+"delete======");
                }

            }
        });

    }

    @Override
    public void onFailed(String str) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
            }
        });
    }

    @Override
    public void onError(String str) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
            }
        });
    }
}
