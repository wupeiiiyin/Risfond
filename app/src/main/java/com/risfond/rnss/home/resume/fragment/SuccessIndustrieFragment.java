package com.risfond.rnss.home.resume.fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.BaseWhole;
import com.risfond.rnss.entry.IndustrieInfo;
import com.risfond.rnss.entry.ResumeWhole;
import com.risfond.rnss.home.commonFuctions.successCase.fragment.BaseSuccessCaseWholeFragment;
import com.risfond.rnss.home.resume.adapter.IndustrieAdapter;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/5
 * @desc 成功案例行业选择
 */
public class SuccessIndustrieFragment extends BaseSuccessCaseWholeFragment {
    public static String INDUSTRIE_TYPE = "INDUSTRIE_TYPE";
    //当前行业
    public static String INDUSTRIE = "INDUSTRIE";
    //期望行业
    public static String DESIRED_INDUSTRIES = "DESIRED_INDUSTRIES";

    private String mCurrentIndustrieType = INDUSTRIE;

    RecyclerView mRecyclerview;
    private IndustrieAdapter mIndustrieAdapter;

    public SuccessIndustrieFragment(ResumeWhole baseWhole, OnSelectListener onSelectListener) {
        super(baseWhole, onSelectListener);
    }

    public static List<IndustrieInfo> getIndustrieInfos() {
        String[] strings = new String[]{
                "1@IT.互联网"
                ,"2@金融"
                ,"3@地产.建筑.物业"
                ,"4@电子.通信"
                ,"5@消费品"
                ,"6@制造"
                ,"7@服务.外包.中介"
                ,"8@教育"
                ,"9@媒体.广告"
                ,"10@贸易.物流"
                ,"11@制药.医疗"
                ,"12@能源.环保"
                ,"13@政府.农林牧渔"
                ,"14@其他"
        };
        List<IndustrieInfo> industrieInfos = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            IndustrieInfo industrieInfo = new IndustrieInfo();
            String item = strings[i];
            industrieInfo.setCode(item.split("@")[0]);
            industrieInfo.setContent(item.split("@")[1]);
            industrieInfos.add(industrieInfo);
        }
        return industrieInfos;
    }

    private void initData() {
        List<IndustrieInfo> industrieInfos = getIndustrieInfos();
        for (IndustrieInfo industrieInfo : industrieInfos) {
            if (mCurrentIndustrieType.equals(INDUSTRIE)) {
                //当前行业
                industrieInfo.setCheck(((ResumeWhole) getBaseWhole()).getIndustrys().contains(industrieInfo.getCode()));
            } else {
                industrieInfo.setCheck(((ResumeWhole) getBaseWhole()).getDesiredIndustries().contains(industrieInfo.getCode()));
            }
        }
        mIndustrieAdapter.setNewData(industrieInfos);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mIndustrieAdapter = new IndustrieAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getContext(), R.color.color_home_back)));
        mRecyclerview.setAdapter(mIndustrieAdapter);

        mIndustrieAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mIndustrieAdapter.getData().get(position).setCheck(!mIndustrieAdapter.getData().get(position).isCheck());
                mIndustrieAdapter.notifyItemChanged(position);
            }
        });
    }
    @Override
    public int getLayoutResId() {
        return R.layout.include_base_whole2;
    }

    @Override
    protected void initView(ViewGroup rootView) {
        View view = getLayoutInflater().inflate(R.layout.include_recyclerview, rootView, false);
        rootView.addView(view);
        mRecyclerview = (RecyclerView) view.findViewById(R.id.id_recyclerview);

        mCurrentIndustrieType = getArguments().getString(INDUSTRIE_TYPE);
        initAdapter();
        initData();
    }

    @Override
    protected void onResetClick(View view) {
        for (IndustrieInfo industrieInfo : mIndustrieAdapter.getData()) {
            industrieInfo.setCheck(false);
        }
        mIndustrieAdapter.notifyDataSetChanged();
    }


    @Override
    protected ResumeWhole onConfirmClick(View view, BaseWhole whole) {
        ResumeWhole resumeWhole = (ResumeWhole) whole;
        //当前行业
        List<String> industrys = resumeWhole.getIndustrys();
        List<String> industrysTip = resumeWhole.getIndustrysTip();
        //期望行业
        List<String> desiredIndustries = resumeWhole.getDesiredIndustries();
        List<String> desiredIndustriesTip = resumeWhole.getDesiredIndustriesTip();
        for (IndustrieInfo industrieInfo : mIndustrieAdapter.getData()) {
            if (mCurrentIndustrieType.equals(INDUSTRIE)) {
                //当前行业
                if (industrys.contains(industrieInfo.getCode())) {
                    if (!industrieInfo.isCheck()) {
                        industrys.remove(industrieInfo.getCode());
                        industrysTip.remove(industrieInfo.getContent());
                    }
                } else {
                    if (industrieInfo.isCheck()) {
                        industrys.add(industrieInfo.getCode());
                        industrysTip.add(industrieInfo.getContent());
                    }
                }
            } else {
                if (desiredIndustries.contains(industrieInfo.getCode())) {
                    if (!industrieInfo.isCheck()) {
                        desiredIndustries.remove(industrieInfo.getCode());
                        desiredIndustriesTip.remove(industrieInfo.getContent());
                    }
                } else {
                    if (industrieInfo.isCheck()) {
                        desiredIndustries.add(industrieInfo.getCode());
                        desiredIndustriesTip.add(industrieInfo.getContent());
                    }
                }
            }
        }
        resumeWhole.setIndustrys(industrys);
        resumeWhole.setIndustrysTip(industrysTip);
        resumeWhole.setDesiredIndustries(desiredIndustries);
        resumeWhole.setDesiredIndustriesTip(desiredIndustriesTip);
        return resumeWhole;
    }

}
