package com.risfond.rnss.home.commonFuctions.myAttenDance.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.MyAttendance;
import com.risfond.rnss.home.commonFuctions.myAttenDance.Util.DataUtil.AttendanceDataUtils;
import com.risfond.rnss.widget.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/7/5.
 */

public class MyWentOutAdapter extends RecyclerView.Adapter implements ExpandableTextView.OnExpandListener {

    private Context context;
    private List<MyAttendance> data = new ArrayList<>();
    private final int TITLE = 0;
    private final int CONTENT = 1;
    private final int TAIL = 2;
    //只要在getview时为其赋值为准确的宽度值即可，无论采用何种方法
    private int etvWidth;
    private SparseArray<Integer> mPositionsAndStates = new SparseArray<>();

    private Map<String, Integer> mTextStateList = new ArrayMap<>();
    private final int STATE_COLLAPSED = 0;//文本折叠
    private final int STATE_EXPANDED = 1;//文本展开

    public MyWentOutAdapter(Context context, List<MyAttendance> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TITLE:
                view = LayoutInflater.from(context).inflate(R.layout.my_wentout_header, parent, false);
                holder = new HeaderHolder(view);
                break;
            case CONTENT:
                view = LayoutInflater.from(context).inflate(R.layout.item_wentout_detail, parent, false);
                holder = new ContentHolder(view);
                break;
            case TAIL:
                view = LayoutInflater.from(context).inflate(R.layout.item_askleave_tail, parent, false);
                holder = new TailHolder(view);
                break;
        }
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TITLE:
                final HeaderHolder headerHolder = (HeaderHolder) holder;
                headerHolder.ivFrontMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        headerHolder.tvCurremtMonth.setText(AttendanceDataUtils.getSomeMonthDay(((HeaderHolder) holder).tvCurremtMonth.getText().toString(), -1));
                    }
                });
                headerHolder.tvCurremtMonth.setText(((HeaderHolder) holder).tvCurremtMonth.getText());
                headerHolder.tvNextMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        headerHolder.tvCurremtMonth.setText(AttendanceDataUtils.getSomeMonthDay(((HeaderHolder) holder).tvCurremtMonth.getText().toString(), 1));
                    }
                });
                break;
            case CONTENT:
                final ContentHolder contentHolder = (ContentHolder) holder;
                final MyAttendance record = data.get(position);
                int state = 0;
                if (mTextStateList.get(data.get(position).getStartMonth() + data.get(position).getStartTime() + position) != null) {
                    state = mTextStateList.get(data.get(position).getStartMonth() + data.get(position).getStartTime() + position);
                }
                if (state == STATE_EXPANDED) {
                    contentHolder.llWentoutDetail.setVisibility(View.VISIBLE);
                    contentHolder.llWentoutList.setBackgroundColor(ContextCompat.getColor(context,R.color.item_clicked_gray));
                    mTextStateList.put(data.get(position).getStartMonth() + data.get(position).getStartTime() + position, STATE_EXPANDED);
                } else {
                    contentHolder.llWentoutDetail.setVisibility(View.GONE);
                    contentHolder.llWentoutList.setBackgroundColor(ContextCompat.getColor(context,R.color.color_white));
                    mTextStateList.put(data.get(position).getStartMonth() + data.get(position).getStartTime() + position, STATE_COLLAPSED);
                }

                contentHolder.llWentoutList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mTextStateList.get(data.get(position).getStartMonth() + data.get(position).getStartTime() + position) != null && mTextStateList.get(data.get(position).getStartMonth() + data.get(position).getStartTime() + position) == STATE_EXPANDED) {
                            contentHolder.llWentoutDetail.setVisibility(View.GONE);
                            contentHolder.llWentoutList.setBackgroundColor(ContextCompat.getColor(context,R.color.color_white));
                            mTextStateList.put(data.get(position).getStartMonth() + data.get(position).getStartTime() + position, STATE_COLLAPSED);
                        } else {
                            contentHolder.llWentoutDetail.setVisibility(View.VISIBLE);
                            contentHolder.llWentoutList.setBackgroundColor(ContextCompat.getColor(context,R.color.item_clicked_gray));
                            mTextStateList.put(data.get(position).getStartMonth() + data.get(position).getStartTime() + position, STATE_EXPANDED);
                        }
                    }
                });

                contentHolder.tv_wentout_goouttype.setText(record.getGoOutType());
                contentHolder.tv_wentout_weekformat.setText(record.getWeekFormat());
                contentHolder.tv_wentout_dateformat.setText(record.getDateFormat());
                contentHolder.tv_wentout_startmonth.setText(record.getStartMonth());
                contentHolder.tv_wentout_starttime.setText(record.getStartTime());
                contentHolder.tv_wentout_endmonth.setText(record.getEndMonth());
                contentHolder.tv_wentout_endtime.setText(record.getEndTime());


                contentHolder.tv_wentout_clientname.setText(setColorText("办事单位: ", record.getClientName()));
                contentHolder.tv_wentout_address.setText(setColorText("办事地址: ", record.getAddress()));
                String memmbers = "";
                for (int i = 0; i < record.getMembers().length; i++) {
                    memmbers += record.getMembers()[i] + " ";
                }
                contentHolder.tv_wentout_members.setText(setColorText("外出成员: ", memmbers));
                contentHolder.tv_wentout_contact.setText(setColorText("联系人: ", record.getContact()));
                contentHolder.tv_wentout_contactnumber.setText(setColorText("联系电话: ", record.getContactNumber()));
                contentHolder.tv_wentout_reason.setText(setColorText("外出事由: ", record.getReason()));
                break;

            case TAIL:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private SpannableStringBuilder setColorText(String s, String s1) {
        String text = s + s1;
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.text_dimgray)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //设置指定位置文字的颜色
        return style;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == data.size()){
            return TAIL;
        }else {
            return CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
//            return data.size() > 0 ? data.size() + 1 : data.size();
            return data.size() == 0 ? data.size() : data.size() + 1;
        } else {
            return 0;
        }
    }

    public void updateData(List<MyAttendance> data) {
        mTextStateList = new ArrayMap<>();
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onExpand(ExpandableTextView view) {
        Object obj = view.getTag();
        if (obj != null && obj instanceof Integer) {
            mPositionsAndStates.put((Integer) obj, view.getExpandState());
        }
    }

    @Override
    public void onShrink(ExpandableTextView view) {
        Object obj = view.getTag();
        if (obj != null && obj instanceof Integer) {
            mPositionsAndStates.put((Integer) obj, view.getExpandState());
        }
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_front_month)
        ImageView ivFrontMonth;
        @BindView(R.id.tv_curremt_month)
        TextView tvCurremtMonth;
        @BindView(R.id.tv_next_month)
        ImageView tvNextMonth;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ContentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_wentout_list)
        LinearLayout llWentoutList;
        @BindView(R.id.ll_wentout_detail)
        LinearLayout llWentoutDetail;

        @BindView(R.id.tv_wentout_goouttype)
        TextView tv_wentout_goouttype;
        @BindView(R.id.tv_wentout_weekformat)
        TextView tv_wentout_weekformat;
        @BindView(R.id.tv_wentout_dateformat)
        TextView tv_wentout_dateformat;
        @BindView(R.id.tv_wentout_startmonth)
        TextView tv_wentout_startmonth;
        @BindView(R.id.tv_wentout_starttime)
        TextView tv_wentout_starttime;
        @BindView(R.id.tv_wentout_endmonth)
        TextView tv_wentout_endmonth;
        @BindView(R.id.tv_wentout_endtime)
        TextView tv_wentout_endtime;


        @BindView(R.id.tv_wentout_clientname)
        TextView tv_wentout_clientname;
        @BindView(R.id.tv_wentout_address)
        TextView tv_wentout_address;
        @BindView(R.id.tv_wentout_members)
        TextView tv_wentout_members;
        @BindView(R.id.tv_wentout_contact)
        TextView tv_wentout_contact;
        @BindView(R.id.tv_wentout_contactnumber)
        TextView tv_wentout_contactnumber;
        @BindView(R.id.tv_wentout_reason)
        TextView tv_wentout_reason;

        public ContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TailHolder extends RecyclerView.ViewHolder {

        public TailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
