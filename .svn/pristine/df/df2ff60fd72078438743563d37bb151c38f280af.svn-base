package com.risfond.rnss.group.adapter;

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
import com.risfond.rnss.contacts.activity.ContactsInfoActivity;
import com.risfond.rnss.contacts.adapter.AddressListAdapter;
import com.risfond.rnss.entry.CompanyList;
import com.risfond.rnss.entry.UserList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 添加群组（简历分享）搜索
 * Created by Abbott on 2017/5/15.
 */

public class AddGroupSearchAdapter extends RecyclerView.Adapter<AddGroupSearchAdapter.SearchViewHolder> {
    private Context context;
    private List<UserList> data;
    private boolean isHideRadio;

    public AddGroupSearchAdapter(Context context, List<UserList> data) {
        this.context = context;
        this.data = data;
    }

    public AddGroupSearchAdapter(Context context, List<UserList> data, boolean isHideRadio) {
        this.context = context;
        this.data = data;
        this.isHideRadio = isHideRadio;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public AddGroupSearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_person, parent, false);
        SearchViewHolder holder = new SearchViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final AddGroupSearchAdapter.SearchViewHolder holder, final int position) {
        final UserList userList = data.get(position);
        GlideUtil.downLoadHeadImage(context, userList.getHeadphoto(),
                holder.ivUserPhoto, new RoundedCornersTransformation(context, 0, 0));
        holder.typePerson.setText(userList.getCnname());
        holder.typePersonEn.setText(userList.getEnname());
        holder.typePersonPosition.setText(userList.getPositionname());
        if (isHideRadio) {
            holder.cbPerson.setVisibility(View.GONE);
        } else {
            holder.cbPerson.setVisibility(View.VISIBLE);
        }
        holder.cbPerson.setChecked(userList.isSelected());
        holder.ivUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactsInfoActivity.startAction(context, userList.getEasemobaccount());
            }
        });

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

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
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

        public SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
