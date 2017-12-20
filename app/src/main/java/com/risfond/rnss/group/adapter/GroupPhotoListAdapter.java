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
import com.risfond.rnss.contacts.activity.ContactsInfoActivity;
import com.risfond.rnss.entry.CompanyList;
import com.risfond.rnss.entry.DepartList;
import com.risfond.rnss.entry.UserList;
import com.risfond.rnss.group.AddGroupEventBus;
import com.risfond.rnss.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Abbott on 2017/5/15.
 */

public class GroupPhotoListAdapter extends RecyclerView.Adapter<GroupPhotoListAdapter.PhotoList> {

    private Context context;
    private List<AddGroupEventBus> data;

    public GroupPhotoListAdapter(Context context, List<AddGroupEventBus> data) {
        this.context = context;
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
    public GroupPhotoListAdapter.PhotoList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group_photo_list, parent, false);
        PhotoList holder = new PhotoList(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final GroupPhotoListAdapter.PhotoList holder, final int position) {

        GlideUtil.downLoadHeadImage(context, data.get(position).getFrendPhoto(), holder.headPortrait, new CropCircleTransformation(context));
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

    public void updateData(List<AddGroupEventBus> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class PhotoList extends RecyclerView.ViewHolder {
        @BindView(R.id.head_portrait)
        CircleImageView headPortrait;

        public PhotoList(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
