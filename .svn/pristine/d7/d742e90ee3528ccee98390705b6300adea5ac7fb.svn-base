package com.risfond.rnss.common.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.City;
import com.risfond.rnss.entry.Province;
import com.risfond.rnss.entry.RegisterCompany;
import com.risfond.rnss.entry.RegisterCreateData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Abbott on 2017/9/26.
 */

public class WheelDialog {
    private static WheelDialog mInstance;
    public static final String TYPE_FORMAT_yyyyMMdd = "yyyy-MM-dd";
    public static final String TYPE_FORMAT_yyyyMM = "yyyy-MM";
    public static boolean[] TYPE_YEAR_MONTH_DATE = {true, true, true, false, false, false};
    public static boolean[] TYPE_YEAR_MONTH = {true, true, false, false, false, false};

    private int color = 0xFF057dff;
    private TimePickerView pvTime;
    private OptionsPickerView onePicker, twoPicker;
    private SimpleDateFormat sdf;
    private Calendar startDate, endDate, calendar;
    private List<String> value = new ArrayList<>();
    private List<String> code = new ArrayList<>();

    private WheelDialog() {
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
    }

    public void showDataDialog(Context context, String title, String current, List<RegisterCompany> data, WheelDialog.DataSelected dataSelected) {
        int position = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getCompanyName().equals(current)) {
                position = i;
            }
        }
        WheelDialog.getInstance().showDataSelectDialog(context, title, position, data, dataSelected);
    }

    /**
     * 时间选择器
     *
     * @param context
     * @param title
     * @param formatType
     * @param showType
     * @param timeSelected
     */
    public void showDateSelectDialog(Context context, String title, String formatType, boolean[] showType,
                                     String currentTime, final TimeSelected timeSelected) {
        sdf = new SimpleDateFormat(formatType, Locale.CHINA);
        startDate.set(1900, 0, 1);
        endDate.set(2100, 11, 31);
        //时间选择器
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                timeSelected.onTimeSelected(sdf.format(date));
            }
        })
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isCenterLabel(false)
                .setType(showType)
                .isCyclic(false)
                .setTitleText(title)
                .setTitleColor(color)
                .setTextColorCenter(color)
                .build();
        calendar = strTime2calender(currentTime);
        pvTime.setDate(calendar);//默认显示时间
        pvTime.show();

    }

    /**
     * 一级选择器
     *
     * @param context
     * @param title
     * @param value
     * @param dataSelected
     */
    public void showDataSelectDialog(Context context, String title, final int position, final List<String> value,
                                     final List<String> code, final DataSelected dataSelected) {
        //一级选择器
        onePicker = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                dataSelected.onDataSelected(value.get(options1), code.get(options1));
            }
        })
                .setTitleText(title)
                .setSelectOptions(position)
                .setTitleColor(color)
                .setTextColorCenter(color)
                .build();

        onePicker.setPicker(value);//一级选择器
        onePicker.show();
    }

    /**
     * 一级选择器
     *
     * @param context
     * @param title
     * @param data
     * @param dataSelected
     */
    public void showDataSelectDialog(Context context, String title, final int position,
                                     final List<RegisterCompany> data, final DataSelected dataSelected) {
        //一级选择器
        onePicker = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                dataSelected.onDataSelected(data.get(options1).getCompanyName(), data.get(options1).getCompanyId() + "");
            }
        })
                .setTitleText(title)
                .setSelectOptions(position)
                .setTitleColor(color)
                .setTextColorCenter(color)
                .build();

        onePicker.setPicker(data);//一级选择器
        onePicker.show();
    }

    public void createProvinceSelectDialog(Context context, String title, final List<Province> provinces,
                                           final List<List<City>> cities, final DataSelected dataSelected) {
        //二级级选择器
        twoPicker = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                dataSelected.onDataSelected(provinces.get(options1).getName() + cities.get(options1).get(options2).getName(),
                        cities.get(options1).get(options2).getId());
            }
        })
                .setTitleText(title)
                .setSelectOptions(0, 0)
                .setTitleColor(color)
                .setTextColorCenter(color)
                .build();

        twoPicker.setPicker(provinces, cities);//一级选择器
    }

    public void ShowProvinceSelectDialog() {
        //二级级选择器
        twoPicker.show();
    }

    /**
     * 将string时间转为calender
     * f
     *
     * @param strTime
     * @return
     */
    public Calendar strTime2calender(String strTime) {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = sdf.parse(strTime);
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return calendar;
    }

    public interface TimeSelected {
        void onTimeSelected(String time);
    }

    public interface DataSelected {
        void onDataSelected(String value, String code);
    }

    /**
     * 单一实例
     *
     * @return
     */
    public static WheelDialog getInstance() {
        if (mInstance == null) {
            synchronized (DialogUtil.class) {
                if (mInstance == null) {
                    mInstance = new WheelDialog();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

}
