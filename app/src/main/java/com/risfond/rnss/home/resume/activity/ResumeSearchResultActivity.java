package com.risfond.rnss.home.resume.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.CustomDialog;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.NetUtil;
import com.risfond.rnss.entry.ResumeSearch;
import com.risfond.rnss.entry.ResumeSearchResponse;
import com.risfond.rnss.home.commonFuctions.myAttenDance.activity.MyAttendanceActivity;
import com.risfond.rnss.home.resume.adapter.ResumeSearchAdapter;
import com.risfond.rnss.home.resume.adapter.ResumeSearchHistoryAdapter;
import com.risfond.rnss.home.resume.fragment.EducationFragment;
import com.risfond.rnss.home.resume.fragment.ExperienceFragment;
import com.risfond.rnss.home.resume.fragment.MoreFragment;
import com.risfond.rnss.home.resume.fragment.PositionFragment;
import com.risfond.rnss.home.resume.modleImpl.ResumeSearchImpl;
import com.risfond.rnss.home.resume.modleInterface.IResumeSearch;
import com.risfond.rnss.home.resume.modleInterface.SelectCallBack;
import com.risfond.rnss.widget.RecycleViewDivider;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 简历搜索结果页面
 */
public class ResumeSearchResultActivity extends BaseActivity implements ResponseCallBack, SelectCallBack {

    @BindView(R.id.et_resume_search)
    EditText etResumeSearch;
    @BindView(R.id.rv_resume_search_history)
    RecyclerView rvResumeSearchHistory;
    @BindView(R.id.ll_empty_history)
    LinearLayout llEmptyHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.tv_resume_total)
    TextView tvResumeTotal;
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;
    @BindView(R.id.rv_resume_list)
    RecyclerView rvResumeList;
    @BindView(R.id.ll_empty_search)
    LinearLayout llEmptySearch;
    @BindView(R.id.ll_resume)
    LinearLayout llResume;
    @BindView(R.id.activity_resume_search_result)
    LinearLayout activityResumeSearchResult;
    @BindView(R.id.cb_position)
    CheckBox cbPosition;
    @BindView(R.id.cb_experience)
    CheckBox cbExperience;
    @BindView(R.id.cb_education)
    CheckBox cbEducation;
    @BindView(R.id.cb_more)
    CheckBox cbMore;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.frame)
    View Frame;
    @BindView(R.id.tv_search_save)
    TextView tvSave;//保存搜索简历
    @BindView(R.id.cb_whole)
    CheckBox cbWhole;//搜索分类按钮

    private Context context;
    private ResumeSearchAdapter adapter;
    private ResumeSearchHistoryAdapter historyAdapter;
    private Map<String, String> request;
    private IResumeSearch iResumeSearch;
    private int pageindex = 1;
    private ResumeSearchResponse response;
    private List<ResumeSearch> searches = new ArrayList<>();
    private List<ResumeSearch> temp = new ArrayList<>();
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;
    private List<String> histories;
    private List<String> historiesAESC;

    private FragmentTransaction transaction;
    private ExperienceFragment experienceFragment;
    private PositionFragment positionFragment;
    private EducationFragment educationFragment;
    private MoreFragment moreFragment;

    private List<String> selectedIds = new ArrayList<>();//城市ID
    private List<String> selectedNames = new ArrayList<>();//城市名称
    private List<String> educations = new ArrayList<>();//学历ID
    private List<String> educationNames = new ArrayList<>();//学历名称
    private String experience_from = "";//经验from
    private String experience_to = "";//经验to
    private List<String> languages = new ArrayList<>();//语言ID
    private List<String> recommends = new ArrayList<>();//推荐状态ID
    private List<String> sexs = new ArrayList<>();//性别ID
    private String age_From = "";//年龄from
    private String age_To = "";//年龄to
    private String salary_From = "";//薪资from
    private String salary_To = "";//薪资to

    private String yearfrom = "";
    private String yearto = "";
    private String edufrom = "";
    private String eduto = "";
    private String agefrom = "";
    private String ageto = "";
    private String salaryfrom = "";
    private String salaryto = "";
    private boolean isHasData;

    private PopupWindow popupwindow;


    @Override
    public int getContentViewResId() {
        return R.layout.activity_resume_search_result;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = ResumeSearchResultActivity.this;
        histories = new ArrayList<>();
        historiesAESC = new ArrayList<>();
        iResumeSearch = new ResumeSearchImpl();
        cbWhole.setText("全部");//初始值
        rvResumeList.setLayoutManager(new LinearLayoutManager(context));
        rvResumeList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(context, R.color.color_home_back)));

        rvResumeSearchHistory.setLayoutManager(new LinearLayoutManager(context));
        rvResumeSearchHistory.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));

        adapter = new ResumeSearchAdapter(context, searches);
        historyAdapter = new ResumeSearchHistoryAdapter(context, historiesAESC);

        rvResumeList.setAdapter(adapter);

        checkSearchEditText();
        etResumeSearch.setFocusable(false);
        initHistoryData();
        itemClick();
        scroll();

    }

    /**
     * 请求简历列表
     *
     * @param contact
     */
    private void resumeRequest(String contact) {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        request = new HashMap<>();

        request.put("keyword", contact);
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("pageindex", String.valueOf(pageindex));

        for (int i = 0; i < selectedIds.size(); i++) {
            String key = "worklocation[" + i + "]";
            request.put(key, selectedIds.get(i));
        }

        request.put("yearfrom", yearfrom);
        request.put("yearto", yearto);

        for (int i = 0; i < educations.size(); i++) {
            String key = "edu[" + i + "]";
            request.put(key, educations.get(i));
        }

        request.put("agefrom", agefrom);
        request.put("ageto", ageto);

        if (sexs.size() > 0) {
            request.put("gender[0]", sexs.get(0));
        }

        request.put("salaryfrom", salaryfrom);
        request.put("salaryto", salaryto);

        if (recommends.size() > 0) {
            request.put("resumestatus[0]", recommends.get(0));
        }

        for (int i = 0; i < languages.size(); i++) {
            String key = "lang[" + i + "]";
            request.put(key, languages.get(i));
        }

        iResumeSearch.resumeRequest(SPUtil.loadToken(context), request, URLConstant.URL_RESUME_SEARCH, this);
    }

    @OnClick({R.id.tv_search_cancel,R.id.tv_search_save})//监听事件
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_cancel) {
            ImeUtil.hideSoftKeyboard(etResumeSearch);
            onFinish();
        }
        if (v.getId() == R.id.tv_search_save) {
            //弹框对话
            CustomDialog.Builder builder = new CustomDialog.Builder(context);
            builder.setMessage("您已保存成功，可在快捷搜索查看");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //设置你的操作事项
                }
            });

            builder.create().show();//显示
        }
    }

    private void checkSearchEditText() {
        etResumeSearch.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                etResumeSearch.setFocusableInTouchMode(true);
                etResumeSearch.setFocusable(true);
                etResumeSearch.requestFocus();
                initHistoryData();
                clearAllSelected();
                return false;
            }
        });

        etResumeSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//点击“搜索”软键盘
                    ImeUtil.hideSoftKeyboard(etResumeSearch);
                    pageindex = 1;
                    searches.clear();
                    resumeRequest(etResumeSearch.getText().toString().trim());
                    saveHistory(etResumeSearch.getText().toString().trim());
                }
                return false;
            }
        });

    }

    private void initResumeData() {
        if (llHistory != null) {
            llHistory.setVisibility(View.GONE);
        }
        if (llResume != null) {
            llResume.setVisibility(View.VISIBLE);
        }
        adapter.updateData(searches);
        hideResume();
    }

    /**
     * 获取并显示搜索历史
     */
    private void initHistoryData() {
        llHistory.setVisibility(View.VISIBLE);
        llResume.setVisibility(View.GONE);
        historiesAESC.clear();
        histories = SPUtil.loadSearchHistoryArray(context);
        for (int i = histories.size() - 1; i >= 0; i--) {
            historiesAESC.add(histories.get(i));
        }
        rvResumeSearchHistory.setAdapter(historyAdapter);
        historyAdapter.updateHistory(historiesAESC);
        hideHistory();
    }

    private void itemClick() {
        //搜索里的列表点击
        historyAdapter.setOnItemClickListener(new ResumeSearchHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //如果点击的是清除历史记录
                if (historyAdapter.isFooterView(position)) {
                    ImeUtil.hideSoftKeyboard(etResumeSearch);
                    histories.clear();
                    historiesAESC.clear();
                    historyAdapter.updateHistory(historiesAESC);
                    SPUtil.saveSearchHistoryArray(context, histories);
                    hideHistory();
                } else {
                    if (NetUtil.isConnected(context)) {
                        pageindex = 1;
                        searches.clear();
                        ImeUtil.hideSoftKeyboard(etResumeSearch);
                        resumeRequest(historiesAESC.get(position));
                        etResumeSearch.setText(historiesAESC.get(position));
                        saveHistory(historiesAESC.get(position));
                    } else {
                        ToastUtil.showImgMessage(context, "请检查网络连接");
                    }

                }
            }
        });

        historyAdapter.setOnDeleteClickListener(new ResumeSearchHistoryAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(View view, int position) {
                histories.remove(historiesAESC.get(position));
                SPUtil.saveSearchHistoryArray(context, histories);
                historiesAESC.remove(position);
                historyAdapter.updateHistory(historiesAESC);
            }
        });

        //简历列表点击
        adapter.setOnItemClickListener(new ResumeSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ResumeDetailActivity.startAction(context, String.valueOf(searches.get(position).getId()));
            }
        });
    }

    private void scroll() {
        rvResumeList.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                            resumeRequest(etResumeSearch.getText().toString().trim());
                        }
                    }
                }
            }
        });
    }

    private void updateHistory(String text) {
        for (int i = 0; i < histories.size(); i++) {
            if (histories.get(i).equals(text)) {
                histories.remove(i);
                break;
            }
        }
        if (histories.size() == 15) {
            histories.remove(0);
        }
        histories.add(text);
        SPUtil.saveSearchHistoryArray(context, histories);
    }

    /**
     * 搜素记录只保存15条
     */
    private void saveHistory(String text) {
        updateHistory(text);
    }

    private void hideHistory() {
        if (historiesAESC.size() > 0) {
            rvResumeSearchHistory.setVisibility(View.VISIBLE);
            llEmptyHistory.setVisibility(View.GONE);
        } else {
            rvResumeSearchHistory.setVisibility(View.GONE);
            llEmptyHistory.setVisibility(View.VISIBLE);
        }
    }

    private void hideResume() {
        if (searches.size() > 0) {
            if (rvResumeList != null) {
                rvResumeList.setVisibility(View.VISIBLE);
            }
            if (llEmptySearch != null) {
                llEmptySearch.setVisibility(View.GONE);
            }
        } else {
            if (rvResumeList != null) {
                rvResumeList.setVisibility(View.GONE);
            }
            if (llEmptySearch != null) {
                llEmptySearch.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, ResumeSearchResultActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                if (obj instanceof ResumeSearchResponse) {
                    response = (ResumeSearchResponse) obj;
                    if (tvResumeTotal != null) {
                        tvResumeTotal.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
                    }
                    if (response.getData().size() == 15) {
                        pageindex++;
                        isCanLoadMore = true;
                        if (temp.size() > 0) {
                            searches.removeAll(temp);
                            temp.clear();
                        }
                        searches.addAll(response.getData());
                    } else {
                        isCanLoadMore = false;
                        if (temp.size() > 0) {
                            searches.removeAll(temp);
                            temp.clear();
                        }
                        temp = response.getData();
                        searches.addAll(temp);
                    }
                    initResumeData();
                    adapter.updateData(searches);
                }
                if (isLoadMore) {
                    isLoadingMore = false;
                }
                isLoadMore = false;
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
                    initResumeData();
                    ToastUtil.showShort(context, "搜索失败");
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
                    initResumeData();
                }
            }
        });
    }

    @OnCheckedChanged({R.id.cb_position, R.id.cb_experience, R.id.cb_education, R.id.cb_more,R.id.cb_whole})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ImeUtil.hideSoftKeyboard(etResumeSearch);

        switch (buttonView.getId()) {
            case R.id.cb_position:
                if (isChecked) {
                    checkedPosition();
                    initPositionFragment();
                } else {
                    Frame.setVisibility(View.GONE);
                    clearPositionFragment();
                }
                break;
            case R.id.cb_experience:
                if (isChecked) {
                    checkedExperience();
                    initExperienceFragment();
                } else {
                    Frame.setVisibility(View.GONE);
                    clearExperienceFragment();
                }
                break;
            case R.id.cb_education:
                if (isChecked) {
                    checkedEducation();
                    initEducationFragment();
                } else {
                    Frame.setVisibility(View.GONE);
                    clearEducationFragment();
                }
                break;
            case R.id.cb_more:
                if (isChecked) {
                    checkedMore();
                    initMoreFragment();
                } else {
                    Frame.setVisibility(View.GONE);
                    clearMoreFragment();
                }
                break;
            case R.id.cb_whole://搜索分类
                if (isChecked) {

                    if (popupwindow != null && popupwindow.isShowing()) {
                        popupwindow.dismiss();
                        cbWhole.setChecked(false);
                        return;
                    } else {
                        initmPopupWindowView();
                        popupwindow.showAsDropDown(buttonView, 0, 5);
                    }
                }else if(!isChecked){
                    cbWhole.setChecked(false);
                }
                break;
        }
    }

    /**
     * popupwindow方法
     */
    public void initmPopupWindowView() {

        // // 获取自定义布局文件pop.xml的视图
        View customView = LayoutInflater.from(ResumeSearchResultActivity.this).inflate(R.layout.popview_item_whole, null);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        //        popupwindow.setAnimationStyle(R.style.AnimationFade);
        // 自定义view添加触摸事件
        //        popupwindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupwindow.setFocusable(false);
        popupwindow.setOutsideTouchable(false); // 设置是否允许在外点击使其消失，到底有用没？

        //        popupwindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //        int popupWidth = popupwindow.getContentView().getMeasuredWidth();
        //        int popupHeight = popupwindow.getContentView().getMeasuredHeight();
        //        // 设置好参数之后再show
        //        int[] location = new int[2];
        //        customView.getLocationOnScreen(location);
        //        popupwindow.showAtLocation(customView,  Gravity.CENTER_HORIZONTAL, (location[0]+customView.getWidth()/2)-popupWidth/2 , location[1]-popupHeight);


        customView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    popupwindow = null;
                    cbWhole.setChecked(false);
                }

                return false;
            }
        });

        /** 在这里可以实现自定义视图的功能 */
        final TextView whole = (TextView) customView.findViewById(R.id.tv_one);//全部
        final TextView positions = (TextView) customView.findViewById(R.id.tv_two);//职位
        final TextView company = (TextView) customView.findViewById(R.id.tv_three);//公司
        //监听事件
        whole.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          cbWhole.setText("全部");
                                              if (popupwindow != null && popupwindow.isShowing()) {
                                                  popupwindow.dismiss();
                                                  popupwindow = null;
                                                  cbWhole.setChecked(false);
                                          }
                                      }
                                  }

        );
        positions.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          cbWhole.setText("职位");
                                              if (popupwindow != null && popupwindow.isShowing()) {
                                                  popupwindow.dismiss();
                                                  popupwindow = null;
                                                  cbWhole.setChecked(false);//设置为默认状态
                                          }
                                      }
                                  }

        );
        company.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          cbWhole.setText("公司");
                                              if (popupwindow != null && popupwindow.isShowing()) {
                                                  popupwindow.dismiss();
                                                  popupwindow = null;
                                                  cbWhole.setChecked(false);
                                          }
                                      }
                                  }

        );

    }

    //填充职位页面
    private void initPositionFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        positionFragment = new PositionFragment(selectedIds, selectedNames, this);
        transaction.add(R.id.frame, positionFragment);
        transaction.commit();

        Frame.setVisibility(View.VISIBLE);

    }

    private void clearPositionFragment() {
        if (positionFragment != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(positionFragment);
            transaction.commit();
        }
    }

    //填充经验页面
    private void initExperienceFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        experienceFragment = new ExperienceFragment(experience_from, experience_to, this);
        transaction.add(R.id.frame, experienceFragment);
        transaction.commit();

        Frame.setVisibility(View.VISIBLE);

    }

    private void clearExperienceFragment() {
        if (experienceFragment != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(experienceFragment);
            transaction.commit();
        }
    }

    //填充学历页面
    private void initEducationFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        educationFragment = new EducationFragment(educations, educationNames, this);
        transaction.add(R.id.frame, educationFragment);
        transaction.commit();

        Frame.setVisibility(View.VISIBLE);
    }

    private void clearEducationFragment() {
        if (educationFragment != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(educationFragment);
            transaction.commit();
        }
    }

    //填充更多页面
    private void initMoreFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        if (searches.size() > 0) {
            isHasData = true;
        } else {
            isHasData = false;
        }
        moreFragment = new MoreFragment(recommends, age_From, age_To, sexs, salary_From,
                salary_To, languages, String.valueOf(pageindex), isHasData, this);
        transaction.add(R.id.frame, moreFragment);
        transaction.commit();

        Frame.setVisibility(View.VISIBLE);
    }

    private void clearMoreFragment() {
        if (moreFragment != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(moreFragment);
            transaction.commit();
        }
    }

    private void ClearAllFragment() {
        clearPositionFragment();
        clearExperienceFragment();
        clearEducationFragment();
        clearMoreFragment();
    }

    private void checkedPosition() {
        cbExperience.setChecked(false);
        cbEducation.setChecked(false);
        cbMore.setChecked(false);
    }

    private void checkedExperience() {
        cbPosition.setChecked(false);
        cbEducation.setChecked(false);
        cbMore.setChecked(false);
    }

    private void checkedEducation() {
        cbPosition.setChecked(false);
        cbExperience.setChecked(false);
        cbMore.setChecked(false);
    }

    private void checkedMore() {
        cbPosition.setChecked(false);
        cbExperience.setChecked(false);
        cbEducation.setChecked(false);
    }

    private void clearAll() {
        cbPosition.setChecked(false);
        cbExperience.setChecked(false);
        cbEducation.setChecked(false);
        cbMore.setChecked(false);
    }

    private void onFinish() {
        if (Frame.getVisibility() == View.VISIBLE) {
            Frame.setVisibility(View.GONE);
            clearAll();
            ClearAllFragment();
        } else {
            finish();
        }
    }

    private void clearAllSelected() {
        Frame.setVisibility(View.GONE);
        clearAll();
        ClearAllFragment();

        experience_from = "";
        experience_to = "";
        age_From = "";
        age_To = "";
        salary_From = "";
        salary_To = "";
        yearfrom = "";
        yearto = "";
        edufrom = "";
        eduto = "";
        agefrom = "";
        ageto = "";
        salaryfrom = "";
        salaryto = "";

        languages.clear();
        recommends.clear();
        selectedIds.clear();
        selectedNames.clear();
        educations.clear();
        educationNames.clear();
        languages.clear();
        recommends.clear();
        sexs.clear();

        setPositionValue();
        setExperienceValue();
        setCbEducationValue();
        setMoreValue();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onFinish();
        }
        return false;
    }

    private void setPositionValue() {
        String value = "";
        for (String str : selectedNames) {
            value += str + "+";
        }
        if (TextUtils.isEmpty(value)) {
            cbPosition.setText("地点");
        } else {
            cbPosition.setText(value.substring(0, value.length() - 1));
        }

    }

    private void setExperienceValue() {
        /*if (TextUtils.isEmpty(experience_from)) {
            yearfrom = "0";
        } else {
            yearfrom = experience_from;
        }
        if (TextUtils.isEmpty(experience_to)) {
            yearto = "0";
        } else {
            yearto = experience_to;
        }*/
        yearfrom = experience_from;
        yearto = experience_to;
        if (yearfrom.equals("") && yearto.equals("")) {
            cbExperience.setText("经验");
        } else if (yearfrom.equals("") && !yearto.equals("")) {
            cbExperience.setText("不限-" + yearto);
        } else if (!yearfrom.equals("") && yearto.equals("")) {
            cbExperience.setText(yearfrom + "-不限");
        } else {
            cbExperience.setText(yearfrom + "-" + yearto);
        }

    }

    private void setCbEducationValue() {
        String value = "";
        for (String str : educationNames) {
            value += str + "+";
        }
        if (TextUtils.isEmpty(value)) {
            cbEducation.setText("学历");
        } else {
            cbEducation.setText(value.substring(0, value.length() - 1));
        }

    }

    private void setMoreValue() {
        //年龄
        /*if (TextUtils.isEmpty(age_From)) {
            agefrom = "0";
        } else {
            agefrom = age_From;
        }
        if (TextUtils.isEmpty(age_To)) {
            ageto = "0";
        } else {
            ageto = age_To;
        }*/
        agefrom = age_From;
        ageto = age_To;
        //年薪
        /*if (TextUtils.isEmpty(salary_From)) {
            salaryfrom = "0";
        } else {
            salaryfrom = salary_From;
        }
        if (TextUtils.isEmpty(salary_To)) {
            salaryto = "0";
        } else {
            salaryto = salary_To;
        }*/
        salaryfrom = salary_From;
        salaryto = salary_To;
    }

    @Override
    public void onPositionConfirm(List<String> positions, List<String> names) {
        selectedIds = positions;
        selectedNames = names;
        setPositionValue();
        onFinish();
        searches.clear();
        pageindex = 1;
        resumeRequest(etResumeSearch.getText().toString().trim());
    }

    @Override
    public void onExperienceConfirm(String from, String to) {
        experience_from = from;
        experience_to = to;
        setExperienceValue();
        onFinish();
        searches.clear();
        pageindex = 1;
        resumeRequest(etResumeSearch.getText().toString().trim());
    }

    @Override
    public void onEducationConfirm(List<String> education, List<String> educationName) {
        educations = education;
        educationNames = educationName;
        setCbEducationValue();
        onFinish();
        searches.clear();
        pageindex = 1;
        resumeRequest(etResumeSearch.getText().toString().trim());
    }

    @Override
    public void onMoreConfirm(List<String> recommend, String from1, String to1, List<String> sex,
                              String from2, String to2, List<String> language, String page) {
        recommends = recommend;
        age_From = from1;
        age_To = to1;
        sexs = sex;
        salary_From = from2;
        salary_To = to2;
        languages = language;

        setMoreValue();
        onFinish();
        searches.clear();
        pageindex = Integer.parseInt(page);
        resumeRequest(etResumeSearch.getText().toString().trim());
    }

    @Override
    public void onOutside() {
        onFinish();
    }

}
