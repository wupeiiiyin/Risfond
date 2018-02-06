package com.risfond.rnss.home.resume.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.ResumeSearchHight;
import com.risfond.rnss.entry.ResumeSearchSelectResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/11/29
 * @desc 快捷搜索 搜索历史适配器
 */
public class ResumeQuickSearchV2Adapter extends BaseQuickAdapter<ResumeSearchSelectResponse.DataBean, BaseViewHolder> {
    private String split = " + ";

    public ResumeQuickSearchV2Adapter() {
        super(R.layout.item_resume_quick_list);
    }

    @Override
    protected void convert(BaseViewHolder holder, ResumeSearchSelectResponse.DataBean item) {
        holder.setText(R.id.tv_quick, getQuickSearchContent(item));
        holder.setText(R.id.tv_quick_time, item.getCreatTime());
        holder.addOnClickListener(R.id.image_quick_search_deletes);
    }

    /**
     * 把历史记录格式化成相关格式
     * 地点1.地点2+工作经验+学历+年龄+性别+语言
     *
     * @param item
     * @return
     */
    private String getQuickSearchContent(ResumeSearchSelectResponse.DataBean item) {
        StringBuffer sb = new StringBuffer();
        if (item.getKeyword() != null) {
            sb.append(item.getKeyword());//键入的信息
            append(sb);
        }

        sb.append(joinInfos(item.getWorklocations())); //工作地点

        if ((item.getYearfrom() != 0 && item.getYearto() != 0)
                || (item.getYearfrom() == 0 && item.getYearto() > 0)) {
            sb.append(item.getYearfrom() + "-" + item.getYearto() + "年经验");
        } else if (item.getYearfrom() > 0 && item.getYearto() == 0) {
            sb.append(item.getYearfrom() + "年经验");
        }
        if (!isLastIndexOf(sb)) {
            append(sb);
        }

        sb.append(joinInfos(item.getEdus())); //学历

        if ((item.getAgefrom() != 0 && item.getAgeto() != 0)
                || (item.getAgefrom() == 0 && item.getAgeto() > 0)) {
            sb.append(item.getAgefrom() + "-" + item.getAgeto() + "岁");
        } else if (item.getAgefrom() > 0 && item.getAgeto() == 0) {
            //没有指定结束年龄  但是指定了开始年龄
            sb.append(item.getAgefrom() + "岁");
        }
        if (!isLastIndexOf(sb)) {
            append(sb);
        }

        sb.append(joinInfos(item.getGenders())); //性别

        sb.append(joinInfos(item.getLangs())); //语言

        reduceStr(sb);
        return sb.toString();
    }

    private void reduceStr(StringBuffer sb) {
        if (sb.indexOf(split) == 0) {
            String news = sb.substring(split.length(), sb.length());
            sb.setLength(0);
            sb.append(news);
        }
        if (isLastIndexOf(sb)) {
            sb.setLength(sb.length() - split.length());
        }
    }

    private boolean isLastIndexOf(StringBuffer sb) {
        return sb.lastIndexOf(split) != -1 && sb.lastIndexOf(split) == sb.length() - split.length();
    }

    /**
     * 多个选项使用符号“·”拼接
     *
     * @param infos
     * @return
     */
    private String joinInfos(List<String> infos) {
        StringBuffer sb = new StringBuffer();
        if (infos == null) {
            return sb.toString();
        }
        for (String info : infos) {
            sb.append(info + "·");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        sb.append(split);
        return sb.toString();
    }

    private void append(StringBuffer sb) {
        sb.append(split);
    }

}
