package com.risfond.rnss.home.resume.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.common.utils.CommonUtil;
import com.risfond.rnss.entry.City;
import com.risfond.rnss.entry.Province;
import com.risfond.rnss.home.resume.adapter.CityAdapter;
import com.risfond.rnss.home.resume.adapter.ProvinceAdapter;
import com.risfond.rnss.home.resume.modleInterface.SelectCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Abbott on 2017/3/27.
 * 职位页面
 */

@SuppressLint("ValidFragment")
public class PositionFragment extends BaseFragment {

    @BindView(R.id.rv_province)
    RecyclerView rvProvince;
    @BindView(R.id.rv_city)
    RecyclerView rvCity;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private Context context;
    private SelectCallBack callBack;

    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private Province province;
    private List<Province> provinces = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    private List<String> selectedIds = new ArrayList<>();
    private List<String> selectedNames = new ArrayList<>();
    private SharedPreferences mySharedPreferences;

    public PositionFragment(List<String> selectedId, List<String> selectedName, SelectCallBack callBack) {
        selectedIds.addAll(selectedId);
        selectedNames.addAll(selectedName);
        this.callBack = callBack;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_position_select;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getActivity();

        mySharedPreferences = context.getSharedPreferences("test", context.MODE_PRIVATE);
        provinces = CommonUtil.createProvince(context);
        province = provinces.get(0);
        province.setSelect(true);
        provinceAdapter = new ProvinceAdapter(context, provinces);
        rvProvince.setLayoutManager(new LinearLayoutManager(context));
        rvProvince.setAdapter(provinceAdapter);

        cities = province.getCities();
        cityAdapter = new CityAdapter(context, cities, selectedIds);
        rvCity.setLayoutManager(new LinearLayoutManager(context));
        rvCity.setAdapter(cityAdapter);

        updateProvinceList();
        updateProvinceView();

        onClick();
        onItemClick();
    }

    private void onClick() {
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIds.clear();
                selectedNames.clear();
                updateProvinceList();
                updateProvinceView();
                provinceAdapter.update(provinces);
                cityAdapter.update(cities, selectedIds);
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onPositionConfirm(selectedIds, selectedNames);
            }
        });
    }

    private void onItemClick() {
        provinceAdapter.setOnItemClickListener(new ProvinceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                province = provinces.get(position);
                cities = province.getCities();
                cityAdapter.update(cities, selectedIds, 0, province.getId());
                for (int i = 0; i < provinces.size(); i++) {
                    if (i == position) {
                        provinces.get(i).setSelect(true);
                    } else {
                        provinces.get(i).setSelect(false);
                    }
                }
                provinceAdapter.update(provinces);
            }
        });


        cityAdapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                City city = cities.get(position);
                city.setChecked(!city.isChecked());
                if (city.isChecked()) {
                    selectedIds.add(city.getId());
                    selectedNames.add(city.getName());
                } else {
                    selectedIds.remove(city.getId());
                    selectedNames.remove(city.getName());
                }

                cityAdapter.update(cities, selectedIds, position, province.getId());
                updateProvinceList();
                updateProvinceView();
            }
        });
    }

    /**
     * 热点城市选择后，更新省中的信息
     */
    private void updateProvinceList() {
        for (int i = 0; i < provinces.size(); i++) {
            for (int j = 0; j < provinces.get(i).getCities().size(); j++) {
                if (selectedIds.contains(provinces.get(i).getCities().get(j).getId())) {
                    provinces.get(i).getCities().get(j).setChecked(true);
                } else {
                    provinces.get(i).getCities().get(j).setChecked(false);
                }
            }
        }
        provinceAdapter.update(provinces);
    }

    /**
     * 热点城市选择后，更新省中的信息
     */
    private void updateProvinceView() {
        for (int i = 0; i < provinces.size(); i++) {
            for (int j = 0; j < provinces.get(i).getCities().size(); j++) {
                if (provinces.get(i).getCities().get(j).isChecked()) {
                    provinces.get(i).setTip(true);
                    break;
                } else {
                    provinces.get(i).setTip(false);
                }
            }
        }
    }

    @Override
    protected void lazyLoad() {

    }

}
