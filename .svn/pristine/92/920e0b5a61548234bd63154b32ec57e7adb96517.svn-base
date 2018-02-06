package com.risfond.rnss.home.commonFuctions.myAttenDance.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.MyAttendance;
import com.risfond.rnss.entry.MyAttendanceResponse;
import com.risfond.rnss.home.commonFuctions.myAttenDance.Util.DataUtil.AttendanceDataUtils;
import com.risfond.rnss.home.commonFuctions.myAttenDance.adapter.MyWentOutAdapter;
import com.risfond.rnss.home.commonFuctions.myAttenDance.modelImpl.MyAttendanceImpl;
import com.risfond.rnss.home.commonFuctions.myAttenDance.modelInterface.IMyAttendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by vicky on 2017/8/8.
 */

public class MyWentOutFragment extends BaseFragment implements ResponseCallBack {

    @BindView(R.id.rv_invoice_list)
    RecyclerView rvResumeList;
    @BindView(R.id.ll_empty_search)
    LinearLayout llEmptySearch;

    private MyWentOutAdapter adapter;
    private Map<String, String> request = new HashMap<>();
    private IMyAttendance iCustomerSearch;
    private MyAttendanceResponse response;
    private List<MyAttendance> customerSearches = new ArrayList<>();
    private Context mContext;

    @BindView(R.id.iv_front_month)
    ImageView ivFrontMonth;
    @BindView(R.id.tv_curremt_month)
    TextView tvCurremtMonth;
    @BindView(R.id.tv_next_month)
    ImageView tvNextMonth;

    public MyWentOutFragment(Context context) {
        this.mContext = context;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvCurremtMonth.setText(AttendanceDataUtils.getCurrDate("yyyy年MM月"));
        iCustomerSearch = new MyAttendanceImpl();

        adapter = new MyWentOutAdapter(mContext, customerSearches);

        rvResumeList.setLayoutManager(new LinearLayoutManager(mContext));
        rvResumeList.setAdapter(adapter);

        onclick();
        lazyLoad();

    }

    private void onclick() {
        ivFrontMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCurremtMonth.setText(AttendanceDataUtils.getSomeMonthDay(tvCurremtMonth.getText().toString(), -1));
                if (rvResumeList != null) {
                    rvResumeList.setVisibility(View.GONE);
                }
                if (llEmptySearch != null) {
                    llEmptySearch.setVisibility(View.GONE);
                }
                lazyLoad();
            }
        });
        tvCurremtMonth.setText(tvCurremtMonth.getText());
        tvNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCurremtMonth.setText(AttendanceDataUtils.getSomeMonthDay(tvCurremtMonth.getText().toString(), 1));
                if (rvResumeList != null) {
                    rvResumeList.setVisibility(View.GONE);
                }
                if (llEmptySearch != null) {
                    llEmptySearch.setVisibility(View.GONE);
                }
                lazyLoad();
            }
        });
    }

    private void customerRequest() {
        DialogUtil.getInstance().closeLoadingDialog();
        DialogUtil.getInstance().showLoadingDialog(mContext, "加载中...");
        request.put("keyword", "");
        request.put("staffid", String.valueOf(SPUtil.loadId(mContext)));
        request.put("StartDate", AttendanceDataUtils.getSomeMonthDay2(tvCurremtMonth.getText().toString()));
        iCustomerSearch.positionSearchRequest(SPUtil.loadToken(mContext), request, URLConstant.URL_MY_WENTOUT, this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my_wentout;
    }

    @Override
    protected void lazyLoad() {
//        if (!isPrepared || !isVisible || mHasLoadedOnce) {
//            return;
//        }
        customerRequest();
    }


    @Override
    public void onSuccess(final Object obj) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogUtil.getInstance().closeLoadingDialog();
                    if (obj instanceof MyAttendanceResponse) {
                        response = (MyAttendanceResponse) obj;
                        if (customerSearches.size() > 0) {
                            customerSearches.clear();
                        }
                        customerSearches = response.getData();
                        if (rvResumeList != null && adapter != null) {
                            adapter.updateData(customerSearches);
                            rvResumeList.setAdapter(adapter);
                        }
                    }
                    if (customerSearches.size() > 0) {
                        if (llEmptySearch != null) {
                            llEmptySearch.setVisibility(View.GONE);
                        }
                        if (rvResumeList != null) {
                            rvResumeList.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (llEmptySearch != null) {
                            llEmptySearch.setVisibility(View.VISIBLE);
                        }
                        if (rvResumeList != null) {
                            rvResumeList.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onFailed(final String str) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        DialogUtil.getInstance().closeLoadingDialog();
                        ToastUtil.showShort(mContext, str);
//                    DialogUtil.getInstance().closeLoadingDialog();
                }
            });
        }
    }

    @Override
    public void onError(String str) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
            });
        }
    }
}
