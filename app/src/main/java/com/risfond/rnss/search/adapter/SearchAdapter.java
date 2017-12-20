package com.risfond.rnss.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.contacts.activity.ContactsInfoActivity;
import com.risfond.rnss.entry.CompanyList;
import com.risfond.rnss.entry.UserList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Abbott on 2017/5/15.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private final int TYPE_1 = 0;//title
    private final int TYPE_2 = 1;//person
    private final int TYPE_3 = 2;//company
    private List<Object> data;

    public SearchAdapter(Context context, List<Object> data) {
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
    public int getItemViewType(int position) {
        if (data.get(position) instanceof String) {
            return TYPE_1;
        } else if (data.get(position) instanceof UserList) {
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
        View titleView = LayoutInflater.from(context).inflate(R.layout.item_search_title, parent, false);
        View userView = LayoutInflater.from(context).inflate(R.layout.item_search_person, parent, false);
        View companyView = LayoutInflater.from(context).inflate(R.layout.item_search_company, parent, false);
        switch (viewType) {
            case TYPE_1:
                holder = new TitleViewHolder(titleView);
                break;
            case TYPE_2:
                holder = new UserViewHolder(userView);
                break;
            case TYPE_3:
                holder = new CompanyViewHolder(companyView);
                break;
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_1:
                ((TitleViewHolder) holder).typeTitle.setText((String) data.get(position));
                break;
            case TYPE_2:
//                ImageUtil.loadImage(context, ((UserList) data.get(position)).getHeadphoto(), ((UserViewHolder) holder).ivUserPhoto, new RoundTransformation(20));
                GlideUtil.downLoadHeadImage(context, ((UserList) data.get(position)).getHeadphoto(),
                        ((UserViewHolder) holder).ivUserPhoto, new RoundedCornersTransformation(context, 0, 0));
                ((UserViewHolder) holder).typePerson.setText(((UserList) data.get(position)).getCnname());
                ((UserViewHolder) holder).typePersonEn.setText(((UserList) data.get(position)).getEnname());
                ((UserViewHolder) holder).typePersonPosition.setText(((UserList) data.get(position)).getPositionname());
                OnItemClickListener(holder, position);
                ((UserViewHolder) holder).ivUserPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContactsInfoActivity.startAction(context,((UserList) data.get(position)).getEasemobaccount());
                    }
                });
                break;
            case TYPE_3:
                ((CompanyViewHolder) holder).typeCompany.setText(((CompanyList) data.get(position)).getName());
                ((CompanyViewHolder) holder).typeCompanyCount.setText(((CompanyList) data.get(position)).getCount() + "");
                OnItemClickListener(holder, position);
                break;
        }

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

    public void updateData(List<Object> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.type_title)
        TextView typeTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
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
