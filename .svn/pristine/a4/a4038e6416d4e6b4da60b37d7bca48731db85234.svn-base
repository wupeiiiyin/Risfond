package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.utils.GlideUtil;
import com.hyphenate.easeui.utils.JsonUtil;
import com.hyphenate.easeui.vo.ResumeSearch;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class EaseChatRowResumeText extends EaseChatRow {

    private TextView tv_name, iv_resume_num, tv_position, tv_company, tv_city, tv_education, tv_age, tv_experience;
    private ImageView iv_head, iv_sex;
    private ResumeSearch resumeSearch;

    public EaseChatRowResumeText(Context context, EMMessage message, int position, BaseAdapter adapter, List<EMMessage> emMessages) {
        super(context, message, position, adapter, emMessages);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_resume_message : R.layout.ease_row_sent_resume_message, this);
    }

    @Override
    protected void onFindViewById() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        iv_resume_num = (TextView) findViewById(R.id.iv_resume_num);
        tv_position = (TextView) findViewById(R.id.tv_position);
        tv_company = (TextView) findViewById(R.id.tv_company);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_education = (TextView) findViewById(R.id.tv_education);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_experience = (TextView) findViewById(R.id.tv_experience);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        iv_sex = (ImageView) findViewById(R.id.iv_sex);
    }

    @Override
    public void onSetUpView() {
        resumeSearch = JsonUtil.fromJson(message.getStringAttribute("resumeData", ""), ResumeSearch.class);
        tv_name.setText(resumeSearch.getName());
        iv_resume_num.setText(resumeSearch.getResumeCode());
        tv_position.setText(resumeSearch.getJobTitle());
        tv_company.setText(resumeSearch.getCompanyFullName());
        tv_city.setText(resumeSearch.getLiveLocationTxt());
        tv_education.setText(resumeSearch.getEducationLevelTxt());
        tv_age.setText(resumeSearch.getAge() + "岁");
        tv_experience.setText(resumeSearch.getWorkExperience() + "年经验");

        GlideUtil.loadResumeImage(context, resumeSearch.getPhoto(), iv_head, new CropCircleTransformation(context));
        iv_sex.setImageResource(sex(resumeSearch.getGenderId()));

        handleTextMessage();
    }

    private int sex(String sexCode) {
        if (sexCode.equals("1")) {
            return R.mipmap.man;
        } else {
            return R.mipmap.woman;
        }
    }

    protected void handleTextMessage() {
        if (message.direct() == EMMessage.Direct.SEND) {
            setMessageSendCallback();
            switch (message.status()) {
                case CREATE:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.GONE);
                    break;
                case FAIL:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    break;
                case INPROGRESS:
                    progressBar.setVisibility(View.VISIBLE);
                    statusView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } else {
            if (!message.isAcked() && message.getChatType() == ChatType.Chat) {
                try {
                    EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onBubbleClick() {
        System.out.println();
    }


}
