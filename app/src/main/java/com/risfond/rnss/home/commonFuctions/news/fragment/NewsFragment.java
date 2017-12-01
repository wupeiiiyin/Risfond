package com.risfond.rnss.home.commonFuctions.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.IHttpRequest;
import com.risfond.rnss.entry.CustomerRecord;
import com.risfond.rnss.entry.News;
import com.risfond.rnss.entry.NewsResponse;
import com.risfond.rnss.home.commonFuctions.news.activity.NewsDetailActivity;
import com.risfond.rnss.home.commonFuctions.news.activity.NewsSearchActivity;
import com.risfond.rnss.home.commonFuctions.news.adapter.NewsAdapter;
import com.risfond.rnss.home.commonFuctions.news.modelimpl.NewsImpl;
import com.risfond.rnss.widget.RecycleViewDivider;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsFragment extends BaseFragment implements ResponseCallBack, PullLoadMoreRecyclerView.PullLoadMoreListener {

    @BindView(R.id.tv_total_number)
    TextView tvTotalNumber;
    @BindView(R.id.refresh_news)
    PullLoadMoreRecyclerView refreshNews;

    private Context context;
    private NewsAdapter adapter;
    private List<News> newsList = new ArrayList<>();
    private List<News> data = new ArrayList<>();
    private List<News> tempData = new ArrayList<>();
    private IHttpRequest iNews;
    private int PageIndex = 1;
    private Map<String, String> request = new HashMap<>();
    private String Category;
    private boolean isHasRequest, isPrepare;
    private NewsResponse newsResponse;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_news;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getContext();
        iNews = new NewsImpl();
        Category = getArguments().getString("Category");

        adapter = new NewsAdapter(context, newsList);
        refreshNews.setLinearLayout();
        refreshNews.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL,
                2, ContextCompat.getColor(context, R.color.color_home_back)));
        refreshNews.setColorSchemeResources(R.color.color_blue);
        refreshNews.setOnPullLoadMoreListener(this);
        refreshNews.setAdapter(adapter);

        if (newsResponse != null && tvTotalNumber != null) {
            tvTotalNumber.setText(NumberUtil.formatString(new BigDecimal(newsResponse.getTotal())));
        }

        isPrepare = true;
        onItemClick();
        lazyLoad();

    }

    /**
     * 请求新闻
     *
     * @param content
     */
    private void newsRequest(String content) {
        if (!refreshNews.isRefresh() && !refreshNews.isLoadMore()) {
            DialogUtil.getInstance().showLoadingDialog(context, "请求中...");
        }
        request.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        request.put("PageIndex", String.valueOf(PageIndex));
        request.put("PageSize", "15");
        request.put("Category", Category);
        request.put("keyword", content);
        iNews.requestService(SPUtil.loadToken(context), request, URLConstant.URL_NEWS_LIST2, this);
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsDetailActivity.startAction(context, String.valueOf(newsList.get(position).getID()));
            }
        });
    }


    @Override
    protected void lazyLoad() {
        if (!isHasRequest && isPrepare && isVisible) {
            newsRequest("");
        }
    }

    private void updateUI(final Object obj) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (refreshNews.isRefresh()) {
                    refreshNews.setPullLoadMoreCompleted();
                } else if (refreshNews.isLoadMore()) {
                    refreshNews.setPullLoadMoreCompleted();
                } else {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                if (obj instanceof NewsResponse) {
                    newsResponse = (NewsResponse) obj;
                    isHasRequest = true;
                    data = newsResponse.getData();
                    tvTotalNumber.setText(String.valueOf(newsResponse.getTotal()));
                    if (data.size() == 15) {
                        PageIndex++;
                        if (tempData.size() > 0) {
                            newsList.removeAll(tempData);
                            tempData.clear();
                        }
                        newsList.addAll(data);
                    } else {
                        if (tempData.size() > 0) {
                            newsList.removeAll(tempData);
                            tempData.clear();
                        }
                        tempData = data;
                        newsList.addAll(tempData);
                    }
                    adapter.updateData(newsList);
                } else {
                    ToastUtil.showShort(context, obj.toString());
                }
            }
        });
    }

    @Override
    public void onSuccess(Object obj) {
        updateUI(obj);
    }

    @Override
    public void onFailed(String str) {
        updateUI(str);
    }

    @Override
    public void onError(String str) {
        updateUI(str);
    }

    @Override
    public void onRefresh() {
        PageIndex = 1;
        newsList.clear();
        data.clear();
        tempData.clear();
        newsRequest("");
    }

    @Override
    public void onLoadMore() {
        newsRequest("");
    }

}
