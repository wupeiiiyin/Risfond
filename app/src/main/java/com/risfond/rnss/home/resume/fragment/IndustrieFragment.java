package com.risfond.rnss.home.resume.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
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
 * @desc 行业选择
 */
public class IndustrieFragment extends BaseSuccessCaseWholeFragment {
    public static String INDUSTRIE_TYPE = "INDUSTRIE_TYPE";
    //当前行业
    public static String INDUSTRIE = "INDUSTRIE";
    //期望行业
    public static String DESIRED_INDUSTRIES = "DESIRED_INDUSTRIES";

    private String mCurrentIndustrieType = INDUSTRIE;

    RecyclerView mRecyclerview;
    private IndustrieAdapter mIndustrieAdapter;

    public IndustrieFragment(ResumeWhole baseWhole, OnSelectListener onSelectListener) {
        super(baseWhole, onSelectListener);
    }

    public static List<IndustrieInfo> getIndustrieInfos() {
        String[] strings = new String[]{"04@房地产开发/建筑与工程",
                "16@金融业(投资/保险/证券/银行/基金)",
                "07@互联网/电子商务",
                "08@环保",
                "09@机械制造/机电/重工",
                "10@计算机",
                "05@广告/会展/公关",
                "30@网络游戏",
                "21@耐用消费品(服饰/纺织/皮革/家具)",
                "28@通信(设备/运营/增值服务)",
                "39@制药/生物工程",
                "03@电子/微电子",
                "02@船舶制造",
                "01@办公设备/用品",
                "43@餐饮服务",
                "11@家电业",
                "06@航空/航天研究与制造",
                "15@教育/培训/科研/院校",
                "14@交通/运输/物流",
                "12@家居/室内设计/装潢",
                "17@快速消费品(食品/饮料/日化/烟酒等)",
                "18@旅游/酒店",
                "19@贸易/进出口",
                "20@媒体/出版/文化传播",
                "22@能源(电力/石油)/水利",
                "26@汽车/摩托车(制造/维护/配件/销售/服务)",
                "31@物业管理/商业中心",
                "27@石油/化工/矿产/采掘/冶炼/原材料",
                "42@保险",
                "29@玩具/工艺品/收藏品/奢侈品",
                "33@医疗设备/器械",
                "32@医疗/保健/美容/卫生服务",
                "34@仪器/仪表/工业自动化/电气",
                "46@新能源",
                "49@生活服务",
                "23@农/林/牧/渔",
                "24@批发/零售",
                "37@原材料及加工(金属/木材/橡胶/塑料/玻璃/陶瓷/建材)",
                "35@印刷/包装/造纸",
                "36@娱乐/运动/休闲",
                "40@中介服务",
                "44@外包服务",
                "45@影视/媒体/艺术/文化传播",
                "47@电子技术/半导体/集成电路",
                "48@多元化业务集团公司",
                "13@检验/检测/认证",
                "41@专业服务(咨询/人力资源/财会/法律等)",
                "38@政府/非营利机构",
                "25@其他行业"};
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
