package com.risfond.rnss.home.window;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.common.manager.BasePopupWindow;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/4
 * @desc 选择
 */
public class MultiSelectPopupWindow extends BasePopupWindow implements View.OnClickListener {
    @BindView(R.id.id_whole_rootview)
    LinearLayout mWholeRootView;
    private  Map<String, String> mShowSelections;
    private  OnItemClickListener mOnItemClickListener;
    public MultiSelectPopupWindow(Context context, Activity activity, Map<String, String> keys,OnItemClickListener onItemClickListener) {
        super(context, activity, R.layout.layout_select_whole);
        this.mShowSelections = keys;
        this.mOnItemClickListener = onItemClickListener;
        getPopupWindow().setAnimationStyle(-1);
        getPopupWindow().setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        getPopupWindow().setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void initLayoutView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    private void setTextView(ViewGroup parent, String id,String text) {
        TextView textView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.item_whole, parent, false);
        textView.setId(Integer.parseInt(id));
        textView.setText(text);
        textView.setOnClickListener(this);
        parent.addView(textView);
    }

    public void showView(View view,int xoff,int yoff) {
        if (mShowSelections == null || mWholeRootView == null) {
            return;
        }
        mWholeRootView.removeAllViews();
        for (Map.Entry entry : mShowSelections.entrySet()) {
            setTextView(mWholeRootView,String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
        getPopupWindow().showAsDropDown(view,xoff,yoff);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener!=null) {
            mOnItemClickListener.onItemClickListener(v);
        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(View v);
    }
}
