package com.risfond.rnss.contacts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.contacts.activity.ContactsInfoActivity;
import com.risfond.rnss.entry.UserList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Abbott on 2017/5/15.
 */

public class MyCustomerHRAdapter extends RecyclerView.Adapter<MyCustomerHRAdapter.MyCustomerViewHolder> {


    private Context context;
    private List<UserList> data;
    private boolean isShow;


    public MyCustomerHRAdapter(Context context, List<UserList> data) {
        this.context = context;
        this.data = data;
    }

    public MyCustomerHRAdapter(Context context, List<UserList> data, boolean isShow) {
        this.context = context;
        this.data = data;
        this.isShow = isShow;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyCustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_person, parent, false);
        MyCustomerViewHolder holder = new MyCustomerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyCustomerViewHolder holder, int position) {
        holder.typePerson.setText(data.get(position).getCnname());
        GlideUtil.downLoadHeadImage(context, (data.get(position)).getHeadphoto(),
                holder.ivUserPhoto, new RoundedCornersTransformation(context, 0, 0));
        if (isShow) {
            holder.cbPerson.setVisibility(View.VISIBLE);
            holder.cbPerson.setChecked(data.get(position).isSelected());
        } else {
            holder.cbPerson.setVisibility(View.GONE);
        }
        if (data.get(position).getEnname() != null) {
            holder.typePersonEn.setText(data.get(position).getEnname());
            holder.llEnName.setVisibility(View.VISIBLE);
        } else {
            holder.llEnName.setVisibility(View.GONE);
        }
        if (data.get(position).getPositionname() != null) {
            holder.typePersonPosition.setText(data.get(position).getEnname());
            holder.typePersonPosition.setVisibility(View.VISIBLE);
        } else {
            holder.typePersonPosition.setVisibility(View.GONE);
        }

        OnItemClickListener(holder, position);
    }

    private void OnItemClickListener(MyCustomerViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });

            holder.ivUserPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContactsInfoActivity.startAction(context,(data.get(position)).getEasemobaccount());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<UserList> data) {
        this.data = data;
        for (UserList userList : data) {
            if (Constant.SELECEDIDS.contains(userList.getEasemobaccount())) {
                userList.setSelected(true);
            } else {
                userList.setSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    public static class MyCustomerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_person)
        CheckBox cbPerson;
        @BindView(R.id.iv_user_photo)
        ImageView ivUserPhoto;
        @BindView(R.id.type_person)
        TextView typePerson;
        @BindView(R.id.type_person_en)
        TextView typePersonEn;
        @BindView(R.id.type_person_position)
        TextView typePersonPosition;
        @BindView(R.id.ll_en_name)
        LinearLayout llEnName;

        public MyCustomerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
