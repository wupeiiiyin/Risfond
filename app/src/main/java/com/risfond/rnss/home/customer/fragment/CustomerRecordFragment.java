package com.risfond.rnss.home.customer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.entry.CustomerRecord;
import com.risfond.rnss.home.customer.adapter.CustomerDetailAdapter2;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Abbott on 2017/7/13.
 * 客户回访记录页面
 */

public class CustomerRecordFragment extends BaseFragment {

    @BindView(R.id.rv_customer_list)
    RecyclerView rvCustomerList;

    private Context context;
    private CustomerDetailAdapter2 customerAdapter;
    private List<CustomerRecord> data;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_customer_record;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getContext();

//        if (getArguments() != null) {
//            data = getArguments().getParcelableArrayList("data");
//        }
        rvCustomerList.setLayoutManager(new LinearLayoutManager(context));
        customerAdapter = new CustomerDetailAdapter2(context, data);
        rvCustomerList.setAdapter(customerAdapter);

    }

    public void setData(List<CustomerRecord> data) {
        this.data = data;
//        if (customerAdapter != null) {
//            customerAdapter.notifyDataSetChanged();
//        } else {
//            rvCustomerList.setLayoutManager(new LinearLayoutManager(context));
//            customerAdapter = new CustomerDetailAdapter2(context, data);
//            rvCustomerList.setAdapter(customerAdapter);
//        }
    }

    @Override
    protected void lazyLoad() {
        System.out.println();
    }

}
