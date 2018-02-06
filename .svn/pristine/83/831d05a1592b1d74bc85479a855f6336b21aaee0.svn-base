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
import com.risfond.rnss.entry.ResumeSearchHight;
import com.risfond.rnss.entry.ResumeWhole;
import com.risfond.rnss.home.commonFuctions.successCase.fragment.BaseSuccessCaseWholeFragment;
import com.risfond.rnss.home.resume.adapter.IndustrieAdapter;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import static com.risfond.rnss.home.resume.fragment.IndustrieFragment.INDUSTRIE;
import static com.risfond.rnss.home.resume.fragment.IndustrieFragment.INDUSTRIE_TYPE;
import static com.risfond.rnss.home.resume.fragment.IndustrieFragment.getIndustrieInfos;


/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/6
 * @desc   语言选择
 */
public class LanguageFragment extends BaseSuccessCaseWholeFragment {
    private RecyclerView mRecyclerview;
    private IndustrieAdapter mIndustrieAdapter;

    public static List<IndustrieInfo> getLanguageInfos() {
        List<IndustrieInfo> languages = new ArrayList<>();
        String[] datas = new String[]{
                "01@汉语",
                "02@英语",
                "03@日语",
                "04@法语",
                "05@德语",
                "06@韩语、朝鲜语",
                "07@俄语",
                "09@西班牙语",
                "12@阿拉伯语",
                "13@意大利语",
                "14@葡萄牙语"
        };
        for (int i = 0; i < datas.length; i++) {
            IndustrieInfo industrieInfo = new IndustrieInfo();
            String item = datas[i];
            industrieInfo.setCode(item.split("@")[0]);
            industrieInfo.setContent(item.split("@")[1]);
            languages.add(industrieInfo);
        }
        return languages;

    }

    public LanguageFragment(ResumeWhole baseWhole, OnSelectListener onSelectListener) {
        super(baseWhole, onSelectListener);
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
        initAdapter();
        initData();
    }

    private void initData() {
        List<IndustrieInfo> languageInfos = getLanguageInfos();
        for (IndustrieInfo language : languageInfos) {
            language.setCheck(((ResumeWhole) getBaseWhole()).getLang().contains(language.getCode()));
        }
        mIndustrieAdapter.setNewData(languageInfos);
    }

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
    protected void onResetClick(View view) {
        mIndustrieAdapter.setNewData(getLanguageInfos());
    }

    @Override
    protected BaseWhole onConfirmClick(View view, BaseWhole whole) {
        ResumeWhole resumeWhole = (ResumeWhole) whole;
        List<String> lang = resumeWhole.getLang();
        List<String> langs = resumeWhole.getLangs();
        for (IndustrieInfo industrieInfo : mIndustrieAdapter.getData()) {
            if (lang.contains(industrieInfo.getCode())) {
                if (!industrieInfo.isCheck()) {
                    lang.remove(industrieInfo.getCode());
                    langs.remove(industrieInfo.getContent());
                }
            } else {
                if (industrieInfo.isCheck()) {
                    lang.add(industrieInfo.getCode());
                    langs.add(industrieInfo.getContent());
                }
            }

        }

        resumeWhole.setLang(lang);
        resumeWhole.setLangs(langs);
        return resumeWhole;
    }
}
