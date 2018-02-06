package com.risfond.rnss.contacts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.contacts.activity.CompanyData;
import com.risfond.rnss.contacts.activity.ContactsInfoActivity;
import com.risfond.rnss.entry.CompanyList;
import com.risfond.rnss.entry.DepartList;
import com.risfond.rnss.entry.UserList;


import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Abbott on 2017/5/15.
 */

public class AddressListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private final int TYPE_1 = 0;//company
    private final int TYPE_2 = 1;//depart
    private final int TYPE_3 = 2;//person
    private CompanyData data;
    private boolean isShowCheck;

    public AddressListAdapter(Context context, CompanyData data) {
        this.context = context;
        this.data = data;
    }

    public AddressListAdapter(Context context, boolean isShowCheck, CompanyData data) {
        this.context = context;
        this.isShowCheck = isShowCheck;
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.getCompanyLists().size() > 0) {
            return TYPE_1;
        }
        if (data.getDepartLists().size() > 0) {
            return TYPE_2;
        } else {
            return TYPE_3;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = getViewHolderByViewType(parent, viewType);
        return holder;
    }

    private RecyclerView.ViewHolder getViewHolderByViewType(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View companyView = LayoutInflater.from(context).inflate(R.layout.item_search_company, parent, false);
        View departView = LayoutInflater.from(context).inflate(R.layout.item_search_company, parent, false);
        View personView = LayoutInflater.from(context).inflate(R.layout.item_search_person, parent, false);

        switch (viewType) {
            case TYPE_1:
                holder = new CompanyViewHolder(companyView);
                break;
            case TYPE_2:
                holder = new CompanyViewHolder(departView);
                break;
            case TYPE_3:
                holder = new UserViewHolder(personView);
                break;
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_1:
                ((CompanyViewHolder) holder).typeCompany.setText(((CompanyList) data.getCompanyLists().get(position)).getName());
                ((CompanyViewHolder) holder).typeCompanyCount.setText(((CompanyList) data.getCompanyLists().get(position)).getStaffcount() + "");
                break;
            case TYPE_2:
                ((CompanyViewHolder) holder).typeCompany.setText(((DepartList) data.getDepartLists().get(position)).getName());
                ((CompanyViewHolder) holder).typeCompanyCount.setText(((DepartList) data.getDepartLists().get(position)).getStaffcount() + "");
                break;
            case TYPE_3:
                final UserList userList = data.getUserLists().get(position);
                if (isShowCheck) {
                    ((UserViewHolder) holder).cbPerson.setVisibility(View.VISIBLE);
                } else {
                    ((UserViewHolder) holder).cbPerson.setVisibility(View.GONE);
                }
                GlideUtil.downLoadHeadImage(context, userList.getHeadphoto(),
                        ((UserViewHolder) holder).ivUserPhoto, new RoundedCornersTransformation(context, 0, 0));
                ((UserViewHolder) holder).typePerson.setText(userList.getCnname());
                ((UserViewHolder) holder).typePersonEn.setText(userList.getEnname());
                ((UserViewHolder) holder).typePersonPosition.setText(userList.getPositionname());
                ((UserViewHolder) holder).cbPerson.setChecked(userList.isSelected());
                ((UserViewHolder) holder).ivUserPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContactsInfoActivity.startAction(context, userList.getEasemobaccount());
                    }
                });

                break;
        }
        OnItemClickListener(holder, position);

    }

    private void OnItemClickListener(RecyclerView.ViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (data.getCompanyLists().size() > 0) {
            count = data.getCompanyLists().size();
        } else if (data.getDepartLists().size() > 0) {
            count = data.getDepartLists().size();
        } else if (data.getUserLists().size() > 0) {
            count = data.getUserLists().size();
        }
        return count;
    }

    public void updateData(CompanyData data) {
        this.data = data;
        for (UserList userList : data.getUserLists()) {
            if (Constant.SELECEDIDS.contains(userList.getEasemobaccount())) {
                userList.setSelected(true);
            }else {
                userList.setSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_user_photo)
        ImageView ivUserPhoto;
        @BindView(R.id.type_person)
        TextView typePerson;
        @BindView(R.id.type_person_en)
        TextView typePersonEn;
        @BindView(R.id.type_person_position)
        TextView typePersonPosition;
        @BindView(R.id.cb_person)
        CheckBox cbPerson;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.type_company)
        TextView typeCompany;
        @BindView(R.id.type_company_count)
        TextView typeCompanyCount;

        public CompanyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
