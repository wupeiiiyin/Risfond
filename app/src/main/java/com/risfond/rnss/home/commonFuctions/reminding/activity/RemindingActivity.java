package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.home.commonFuctions.reminding.adapter.HomePageAdapter;
import com.risfond.rnss.home.commonFuctions.reminding.calendar.CaledarAdapter;
import com.risfond.rnss.home.commonFuctions.reminding.calendar.CalendarBean;
import com.risfond.rnss.home.commonFuctions.reminding.calendar.CalendarDateView;
import com.risfond.rnss.home.commonFuctions.reminding.calendar.CalendarUtil;
import com.risfond.rnss.home.commonFuctions.reminding.calendar.CalendarView;
import com.risfond.rnss.home.commonFuctions.reminding.utils.CommonAdapter;
import com.risfond.rnss.home.commonFuctions.reminding.utils.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.risfond.rnss.home.commonFuctions.reminding.activity.Utils.px;

public class RemindingActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_reminding_context)
    TextView tvRemindingContext;

    @BindView(R.id.tv_reminding_addaffairs)
    TextView tvRemindingAddaffairs;

    @BindView(R.id.ll_reminding_reference)
    RelativeLayout mLlRemindingReference;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.list_reminding_item)
    ListView listRemindingItem;

    @BindView(R.id.ll_reming_affairs)
    LinearLayout llRemingAffairs;

    @BindView(R.id.tv_affairsleft)
    TextView tvAffairsleft;
    @BindView(R.id.tv_itemnumber)
    TextView tvItemnumber;
    @BindView(R.id.tv_affairsright)
    TextView tvAffairsright;

    private HomePageAdapter Adapter;
    private boolean isHasNum = true;//记录是否加载有数据
    private List<String> list_positionSearches = new ArrayList();  //内容
    private List<String> list_positionSearches_time = new ArrayList(); //时间

    private TransactiondatabaseSQL ttdbsqlite;

    private TextView view;
    private ImageView img_point,img_line;
    boolean flag=false;
    List<String> times = new ArrayList<>();
    List<String> descs = new ArrayList<>();

    private String according;

    private CommonAdapter<Data> commonAdapter;
    private Map<String,Object> map;
    private Cursor c;
    private List<Integer> ids = new ArrayList<>();
    private TextView tv;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reminding;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        ttdbsqlite = new TransactiondatabaseSQL(this.getApplication());
        c = ttdbsqlite.checktransaction();
        c.moveToFirst();

        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("_id"));
            String cursorString1 = c.getString(c.getColumnIndex("name"));//内容
            String time = c.getString(c.getColumnIndex("time"));//时间 年月日时分

//            Log.e("ccccc",time);
            list_positionSearches_time.add(time);
            list_positionSearches.add(cursorString1);
            ids.add(id);
        }
        map =new HashMap<>();
        map.put("list_positionSearches_time",list_positionSearches_time);
        map.put("list_positionSearches",list_positionSearches);
        map.put("id",ids);




//        int size = list_positionSearches.size();
//        tvItemnumber.setText(size + "");

        if (list_positionSearches.size() > 0) {//-1
            listRemindingItem.setVisibility(View.VISIBLE);      //ListView显示
            llRemingAffairs.setVisibility(View.VISIBLE);
            tvAffairsleft.setVisibility(View.VISIBLE);          //我的事务( 显示
            tvItemnumber.setVisibility(View.VISIBLE);           //数量  显示
            tvAffairsright.setVisibility(View.VISIBLE);         // ) 显示
            tvRemindingAddaffairs.setVisibility(View.GONE);     //占位图片隐藏
            tvRemindingContext.setVisibility(View.GONE);        //文字隐藏

            Adapter = new HomePageAdapter(list_positionSearches, this,list_positionSearches_time,ids,ttdbsqlite);
            listRemindingItem.setAdapter(Adapter);
        } else if (list_positionSearches.size() < 0) {
            listRemindingItem.setVisibility(View.GONE);         //ListView隐藏
            llRemingAffairs.setVisibility(View.GONE);
            tvAffairsleft.setVisibility(View.GONE);             //我的事务( 隐藏
            tvItemnumber.setVisibility(View.GONE);              //数量  隐藏
            tvAffairsright.setVisibility(View.GONE);            // ) 隐藏
            tvRemindingAddaffairs.setVisibility(View.VISIBLE);  //占位图片显示
            tvRemindingContext.setVisibility(View.VISIBLE);     //文字显示
        }


        tvTitle.setText("事务提醒");

        //日历适配器
        CaledarAdapter adapter = new CaledarAdapter() {

            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
                ViewHolder vh;
                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_calendar, null);
                    vh=new ViewHolder();
                    vh.tv= (TextView) convertView.findViewById(R.id.text);
                    vh.dian= (ImageView) convertView.findViewById(R.id.img_point);
                    vh.xian= (ImageView) convertView.findViewById(R.id.img_line);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px(60), px(48));
                    convertView.setLayoutParams(params);
                    convertView.setTag(vh);
                }else {
                    vh= (ViewHolder) convertView.getTag();
                }
                vh.tv = (TextView) convertView.findViewById(R.id.text);
                String s = bean.moth + "-" + bean.day;

                for (int i = 0; i < list_positionSearches_time.size(); i++) {
                    String t = list_positionSearches_time.get(i);
                    if (t.contains(s)){
                        Log.e("HB",s);
                        vh.dian.setVisibility(View.VISIBLE);
                        break;
                    }else {
                        vh.dian.setVisibility(View.GONE);
                    }
                }
                vh.tv.setText("" + bean.day);
                if (bean.mothFlag != 0) {
                    vh.tv.setTextColor(0xff999999);
                } else {
                    vh.tv.setTextColor(0xff333333);
                }
                return convertView;
            }

            class ViewHolder{
                TextView tv;
                ImageView dian,xian;
            }
        };
        mCalendarDateView.setAdapter(adapter);

        //日历点击事件
        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                RelativeLayout linearLayout = (RelativeLayout) view;

                if (tv!=null){
                    tv.setTextColor(Color.BLACK);
                }
                    String s = bean.moth + "-" + bean.day;

                    for (int i = 0; i < list_positionSearches_time.size(); i++) {
                        String t = list_positionSearches_time.get(i);
                        if (t.contains(s)) {
                            if (img_line != null) {
                                img_point.setVisibility(View.VISIBLE);
                                img_line.setVisibility(View.GONE);
                                if (img_line == null){
                                    img_point.setVisibility(View.GONE);
                                    img_line.setVisibility(View.GONE);
                                }
                            }
                            if (img_point != null) {
                                img_line.setVisibility(View.GONE);
                            }
                            img_point = (ImageView) view.findViewById(R.id.img_point);
                            img_line = (ImageView) view.findViewById(R.id.img_line);

                            img_line.setVisibility(View.VISIBLE);
                            img_point.setVisibility(View.VISIBLE);
                            break;
                        }
                }

                //日历上方显示的时间
                mTitle.setText(bean.year + "-" + getDisPlayNumber(bean.moth) + "-" + getDisPlayNumber(bean.day));
                tv = (TextView) linearLayout.getChildAt(0);
                img_point = (ImageView) linearLayout.getChildAt(1);
                img_line = (ImageView) linearLayout.getChildAt(2);

                tv.setTextColor(Color.WHITE);
                if (img_point == null){
                    img_point.setVisibility(View.GONE);
                    img_line.setVisibility(View.GONE);
                }
                if (img_line == null){
                    img_point.setVisibility(View.GONE);
                    img_line.setVisibility(View.GONE);
                }
                img_line.setVisibility(View.VISIBLE);
                img_point.setVisibility(View.GONE);

                String time = mTitle.getText().toString();
                notifyAdapter(time);
            }
        });
        int[] data = CalendarUtil.getYMD(new Date());
        mTitle.setText(data[0] + "-" + data[1] + "-" + data[2]);
        String time = mTitle.getText().toString();
        notifyAdapter(time);
    }

    public void notifyAdapter(String time){

        times.clear();
        descs.clear();
        for (int i = 0; i < list_positionSearches_time.size(); i++) {
            String t = list_positionSearches_time.get(i);
            if (t.contains(time)){
                String desc = list_positionSearches.get(i);
                times.add(t);
                descs.add(desc);
                if (!flag) flag=true;
            }
        }

        if (flag){
            flag=false;
            Adapter.addAll(descs,times);
//            img_line.setVisibility(View.VISIBLE);
//            img_point.setVisibility(View.GONE);

            listRemindingItem.setVisibility(View.VISIBLE);
            llRemingAffairs.setVisibility(View.VISIBLE);
            tvAffairsleft.setVisibility(View.VISIBLE);          //我的事务( 显示
            tvItemnumber.setVisibility(View.VISIBLE);           //数量  显示
            tvAffairsright.setVisibility(View.VISIBLE);         // ) 显示
            tvRemindingAddaffairs.setVisibility(View.GONE);
            tvRemindingContext.setVisibility(View.GONE);

            int count = Adapter.getCount();
            tvItemnumber.setText(count + "");

            according = tvItemnumber.getText().toString();

        }else {
//            img_line.setVisibility(View.GONE);
//            img_point.setVisibility(View.VISIBLE);

            listRemindingItem.setVisibility(View.GONE);
            llRemingAffairs.setVisibility(View.GONE);
            tvAffairsleft.setVisibility(View.GONE);             //我的事务( 隐藏
            tvItemnumber.setVisibility(View.GONE);              //数量  隐藏
            tvAffairsright.setVisibility(View.GONE);            // ) 隐藏
            tvRemindingAddaffairs.setVisibility(View.VISIBLE);
            tvRemindingContext.setVisibility(View.VISIBLE);

        }
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, RemindingActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.calendarDateView)
    CalendarDateView mCalendarDateView;
    @BindView(R.id.title)
    TextView mTitle;

    private String getDisPlayNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_reminding_addaffairs, R.id.imageView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_reminding_addaffairs:
                startActivity(AddTheTransactionActivity.class, true);
                break;
            case R.id.imageView:
                startActivity(AddTheTransactionActivity.class, true);
                break;
        }
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        list_positionSearches.clear();
//        //list_positionSearches.addAll(ttdbsqlite.quereData());
//        Adapter.notifyDataSetChanged();
//    }
}

