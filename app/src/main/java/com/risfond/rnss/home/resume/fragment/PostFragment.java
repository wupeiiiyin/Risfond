package com.risfond.rnss.home.resume.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.CommonUtil;
import com.risfond.rnss.entry.BaseWhole;
import com.risfond.rnss.entry.PositionInfo;
import com.risfond.rnss.entry.ResumeWhole;
import com.risfond.rnss.home.commonFuctions.successCase.fragment.BaseSuccessCaseWholeFragment;
import com.risfond.rnss.home.resume.adapter.PostLeftAdapter;
import com.risfond.rnss.home.resume.adapter.PostRightAdapter;

import java.util.List;


/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/5
 * @desc 职位选择
 */
public class PostFragment extends BaseSuccessCaseWholeFragment {

    private RecyclerView mLeftRecyclerView;
    private RecyclerView mRightRecyclerView;
    private PostLeftAdapter mPostLeftAdapter;
    private PostRightAdapter mPostRightAdapter;

    public PostFragment(BaseWhole baseWhole, OnSelectListener onSelectListener) {
        super(baseWhole, onSelectListener);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.include_base_whole3;
    }

    @Override
    protected void initView(ViewGroup rootView) {
        View view = getLayoutInflater().inflate(R.layout.fragment_post_select, rootView, false);
        mLeftRecyclerView = ((RecyclerView) view.findViewById(R.id.id_position_left_rv));
        mRightRecyclerView = ((RecyclerView) view.findViewById(R.id.id_position_right_rv));
        rootView.addView(view);

        initAdapter();
        initData();
    }

    private void initData() {
        List<PositionInfo> positions = CommonUtil.createPositions(getContext());
        int initSelectPosition = 0;
        for (int i = 0; i < positions.size(); i++) {
            PositionInfo position = positions.get(i);
            for (PositionInfo.Data data : position.getDatas()) {
                if (((ResumeWhole) getBaseWhole()).getDesiredoccupations().contains(data.getCode())) {
                    position.setCheck(true);
                    data.setCheck(true);
                    initSelectPosition = i;
                }
            }
        }

        positions.get(initSelectPosition).setCheck(true);
        mPostLeftAdapter.setNewData(positions);
        mPostRightAdapter.setNewData(mPostLeftAdapter.getData().get(initSelectPosition).getDatas());
    }

    private void initAdapter() {
        mPostLeftAdapter = new PostLeftAdapter();
        mPostLeftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (PositionInfo positionInfo : mPostLeftAdapter.getData()) {
                    positionInfo.setCheck(false);
                }
                mPostLeftAdapter.getData().get(position).setCheck(true);
                mPostLeftAdapter.notifyDataSetChanged();
                mPostRightAdapter.setNewData(mPostLeftAdapter.getData().get(position).getDatas());
            }
        });
        mPostRightAdapter = new PostRightAdapter();
        mPostRightAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPostRightAdapter.getData().get(position).setCheck(!mPostRightAdapter.getData().get(position).isCheck());
                mPostRightAdapter.notifyItemChanged(position);
                mPostLeftAdapter.notifyDataSetChanged();
            }
        });

        mLeftRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRightRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mLeftRecyclerView.setAdapter(mPostLeftAdapter);
        mRightRecyclerView.setAdapter(mPostRightAdapter);

    }

    @Override
    protected void onResetClick(View view) {
        List<PositionInfo> positions = CommonUtil.createPositions(getContext());
        positions.get(0).setCheck(true);
        mPostLeftAdapter.setNewData(positions);
        mPostRightAdapter.setNewData(mPostLeftAdapter.getData().get(0).getDatas());
    }

    @Override
    protected BaseWhole onConfirmClick(View view, BaseWhole whole) {
        ResumeWhole resumeWhole = (ResumeWhole) whole;
        List<String> desiredoccupations = resumeWhole.getDesiredoccupations();
        List<String> desiredoccupationsTip = resumeWhole.getDesiredoccupationsTip();
        for (PositionInfo positionInfo : mPostLeftAdapter.getData()) {
            for (PositionInfo.Data data : positionInfo.getDatas()) {
                if (desiredoccupations.contains(data.getCode())) {
                    if (!data.isCheck()) {
                        desiredoccupations.remove(data.getCode());
                        desiredoccupationsTip.remove(data.getContent());
                    }
                } else {
                    if (data.isCheck()) {
                        desiredoccupations.add(data.getCode());
                        desiredoccupationsTip.add(data.getContent());
                    }
                }
            }
        }
        resumeWhole.setDesiredoccupations(desiredoccupations);
        resumeWhole.setDesiredoccupationsTip(desiredoccupationsTip);
        return resumeWhole;
    }

}
