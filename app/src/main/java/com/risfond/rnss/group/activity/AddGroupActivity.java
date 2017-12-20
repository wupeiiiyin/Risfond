package com.risfond.rnss.group.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.em.EMUtil;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.contacts.fragment.ContactsFragment;
import com.risfond.rnss.entry.AddGroupSearchResponse;
import com.risfond.rnss.entry.ResumeSearch;
import com.risfond.rnss.entry.UserList;
import com.risfond.rnss.group.AddGroupEventBus;
import com.risfond.rnss.group.GroupListUpdateEventBus;
import com.risfond.rnss.group.adapter.AddGroupSearchAdapter;
import com.risfond.rnss.group.adapter.GroupPhotoListAdapter;
import com.risfond.rnss.group.fragment.AddCustomerCompanyFragment;
import com.risfond.rnss.group.fragment.AddCustomerHRFragment;
import com.risfond.rnss.group.fragment.AddGroupFragment;
import com.risfond.rnss.group.modleImpl.AddGroupSearchImpl;
import com.risfond.rnss.group.modleInterface.IAddGroupSearch;
import com.risfond.rnss.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class AddGroupActivity extends BaseActivity implements ResponseCallBack {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_add_group_title)
    LinearLayout llAddGroupTitle;
    @BindView(R.id.tv_company_list)
    TextView tvCompanyList;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_depart)
    TextView tvDepart;
    @BindView(R.id.fl_show_group)
    FrameLayout flShowGroup;
    @BindView(R.id.rv_group_photo)
    RecyclerView rvGroupPhoto;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.ll_list)
    LinearLayout llList;
    @BindView(R.id.ll_search_list)
    LinearLayout llSearchList;
    @BindView(R.id.et_resume_search)
    ClearEditText etResumeSearch;
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;

    private Context context;
    private FragmentTransaction transaction;
    private ContactsFragment contactsFragment;
    private String companyId = "";
    private String departId = "";
    private String type = "";
    private String name = "";
    private boolean isLocalCompany;
    private String contactsType = "99";
    private Stack<Fragment> fragmentStack;
    private Fragment tempFragment;
    private List<AddGroupEventBus> selectDatas = new ArrayList<>();

    private GroupPhotoListAdapter photoListAdapter;
    private IAddGroupSearch iSearch;
    private Map<String, String> request = new HashMap<>();
    private List<UserList> data = new ArrayList<>();
    private AddGroupSearchAdapter addGroupSearchAdapter;
    private ResumeSearch resumeSearch;
    private String resumeName;
    private String resumeId;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_group;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = AddGroupActivity.this;
        fragmentStack = new Stack<>();
        iSearch = new AddGroupSearchImpl();
        tvTitle.setText("分享");

        resumeSearch = getIntent().getParcelableExtra("resumeData");
        resumeName = resumeSearch.getName();
        resumeId = resumeSearch.getResumeCode();

        type = contactsType;
        initContactsFragment();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        photoListAdapter = new GroupPhotoListAdapter(context, selectDatas);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvGroupPhoto.setLayoutManager(manager);
        rvGroupPhoto.setAdapter(photoListAdapter);
        onPhotoListItemClick();

        addGroupSearchAdapter = new AddGroupSearchAdapter(context, data);
        rvSearch.setLayoutManager(new LinearLayoutManager(context));
        rvSearch.setAdapter(addGroupSearchAdapter);

        onSearchListItemClick();
    }

    /**
     * 群组员工搜索
     */
    private void requestSearch() {
        request.clear();
        String name = etResumeSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(name)) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
            request.put("name", name);
            iSearch.searchRequest(SPUtil.loadToken(context), request, URLConstant.URL_SEARCH_BY_STAFF, this);
        }
    }

    @OnEditorAction({R.id.et_resume_search})
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            requestSearch();
        }
        return false;
    }

    private void initContactsFragment() {
        showView();

        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        //第一种方式，初始化fragment并添加到事务中，如果为null就new一个
        contactsFragment = new ContactsFragment();
        bundle.putBoolean("isAddGroup", true);
        contactsFragment.setArguments(bundle);
        transaction.add(R.id.fl_show_group, contactsFragment);
        //提交事务
        transaction.commit();
        fragmentStack.add(contactsFragment);
    }

    private void initCompanyFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(contactsFragment);

        Bundle bundle = new Bundle();
        //第一种方式，初始化fragment并添加到事务中，如果为null就new一个
        AddGroupFragment addGroupFragment = new AddGroupFragment();
        bundle.putString("type", type);
        bundle.putString("companyId", companyId);
        bundle.putString("departId", departId);
        addGroupFragment.setArguments(bundle);
        transaction.add(R.id.fl_show_group, addGroupFragment);
        //提交事务
        hideFragment(transaction);
        transaction.commit();
        showView();
        llAddGroupTitle.setVisibility(View.VISIBLE);
        fragmentStack.add(addGroupFragment);

    }

    private void initCustomerCompanyFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(contactsFragment);

        //第一种方式，初始化fragment并添加到事务中，如果为null就new一个
        AddCustomerCompanyFragment customerCompanyFragment = new AddCustomerCompanyFragment();
        transaction.add(R.id.fl_show_group, customerCompanyFragment);
        //提交事务
        hideFragment(transaction);
        transaction.commit();
        showView();
        llAddGroupTitle.setVisibility(View.VISIBLE);
        fragmentStack.add(customerCompanyFragment);

    }

    private void initCustomerHRFragment(ArrayList<UserList> userLists) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(contactsFragment);

        //第一种方式，初始化fragment并添加到事务中，如果为null就new一个
        AddCustomerHRFragment customerHRFragment = new AddCustomerHRFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", userLists);
        customerHRFragment.setArguments(bundle);
        transaction.add(R.id.fl_show_group, customerHRFragment);
        //提交事务
        hideFragment(transaction);
        transaction.commit();
        showView();
        llAddGroupTitle.setVisibility(View.VISIBLE);
        fragmentStack.add(customerHRFragment);

    }

    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction) {
        for (Fragment fragment : fragmentStack) {
            transaction.hide(fragment);
        }
    }

    @Subscribe
    public void onEventBus(AddGroupEventBus eventBus) {
        isLocalCompany = eventBus.isLocalCompany();
        type = eventBus.getType();
        name = eventBus.getName();
        companyId = eventBus.getCompanyId();
        departId = eventBus.getDepartId();
        if (type.equals("addTo")) {
            checkContactsSelected(eventBus);
            photoListAdapter.updateData(selectDatas);
            rvGroupPhoto.smoothScrollToPosition(selectDatas.size() - 1);
            tvSend.setText("确定(" + selectDatas.size() + ")");
        } else if (type.equals(Constant.LIST_CUSTOMER_COMPANY)) {
            initCustomerCompanyFragment();
        } else if (type.equals(Constant.LIST_CUSTOMER_HR)) {
            initCustomerHRFragment(eventBus.getUserLists());
        } else {
            initTabName();
            initCompanyFragment();
        }
    }

    /**
     * 通讯录员工点击
     *
     * @param eventBus
     */
    private void checkContactsSelected(AddGroupEventBus eventBus) {
        if (selectDatas.size() > 0) {
            if (Constant.SELECEDIDS.contains(eventBus.getEaseId())) {
                int position = Constant.SELECEDIDS.indexOf(eventBus.getEaseId());
                if (position != -1) {
                    selectDatas.remove(position);
                }
                Constant.SELECEDIDS.remove(eventBus.getEaseId());
            } else {
                Constant.SELECEDIDS.add(eventBus.getEaseId());
                selectDatas.add(eventBus);
            }
        } else {
            Constant.SELECEDIDS.add(eventBus.getEaseId());
            selectDatas.add(eventBus);
        }
    }

    /**
     * 搜索员工点击
     *
     * @param userList
     */
    private void checkSearchSelected(UserList userList) {
        AddGroupEventBus eventBus = new AddGroupEventBus("", userList.getEasemobaccount(), userList.getCnname(), userList.getHeadphoto());

        if (selectDatas.size() > 0) {
            if (Constant.SELECEDIDS.contains(userList.getEasemobaccount())) {
                int position = Constant.SELECEDIDS.indexOf(userList.getEasemobaccount());
                if (position != -1) {
                    selectDatas.remove(position);
                }
                Constant.SELECEDIDS.remove(userList.getEasemobaccount());
            } else {
                Constant.SELECEDIDS.add(userList.getEasemobaccount());
                selectDatas.add(eventBus);
            }
        } else {
            Constant.SELECEDIDS.add(userList.getEasemobaccount());
            selectDatas.add(eventBus);
        }
    }

    private void onPhotoListItemClick() {
        photoListAdapter.setOnItemClickListener(new GroupPhotoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AddGroupEventBus eventBus = selectDatas.get(position);
                checkContactsSelected(eventBus);
                tvSend.setText("确定(" + selectDatas.size() + ")");
                updateSearchData(eventBus.getEaseId());
                photoListAdapter.updateData(selectDatas);
                EventBus.getDefault().post(new GroupListUpdateEventBus());
            }
        });
    }

    private void updateSearchData(String id) {
        for (UserList userList : data) {
            if (userList.getEasemobaccount().equals(id)) {
                userList.setSelected(false);
            }
        }
        addGroupSearchAdapter.updateData(data);
    }

    private void onSearchListItemClick() {
        rvSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImeUtil.hideSoftKeyboard(etResumeSearch);
                return false;
            }
        });
        addGroupSearchAdapter.setOnItemClickListener(new AddGroupSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImeUtil.hideSoftKeyboard(etResumeSearch);
                checkSearchSelected(data.get(position));
                tvSend.setText("确定(" + selectDatas.size() + ")");
                data.get(position).setSelected(!data.get(position).isSelected());
                addGroupSearchAdapter.updateData(data);

                photoListAdapter.updateData(selectDatas);
                if (selectDatas.size() > 0) {
                    rvGroupPhoto.smoothScrollToPosition(selectDatas.size() - 1);
                }
            }
        });
    }

    /**
     * 显示导航名
     */
    private void initTabName() {
        if (type.equals(Constant.LIST_COMPANY)) {
            tvCompanyList.setText(name);
        } else if (type.equals(Constant.LIST_DEPART)) {
            tvCompany.setText(name);
        } else if (type.equals(Constant.LIST_USER)) {
            tvDepart.setText(name);
        }
    }

    @OnClick({R.id.ll_back, R.id.tv_contacts, R.id.tv_company_list, R.id.tv_company,
            R.id.tv_send, R.id.ll_search, R.id.tv_search_cancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search:
                llList.setVisibility(View.GONE);
                llSearchList.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_send:
                if (selectDatas.size() > 0) {
                    sendMessages();
                }
                break;
            case R.id.tv_search_cancel:
                ImeUtil.hideSoftKeyboard(etResumeSearch);
                etResumeSearch.setText("");
                llSearchList.setVisibility(View.GONE);
                llList.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.tv_contacts://联系人
                transaction = getSupportFragmentManager().beginTransaction();
                for (int i = 1; i < fragmentStack.size(); i++) {
                    transaction.remove(fragmentStack.get(i));
                }

                tempFragment = fragmentStack.get(0);
                fragmentStack.clear();
                fragmentStack.add(tempFragment);

                transaction.show(fragmentStack.lastElement());
                transaction.commit();

                tvCompanyList.setTextColor(ContextCompat.getColor(context, R.color.color_mine_gray_text));
                tvCompany.setTextColor(ContextCompat.getColor(context, R.color.color_mine_gray_text));
                llAddGroupTitle.setVisibility(View.GONE);
                break;
            case R.id.tv_company_list://企业通讯录
                transaction = getSupportFragmentManager().beginTransaction();
                if (tvDepart.getVisibility() == View.VISIBLE) {//公司，部门都显示的情况
                    transaction.remove(fragmentStack.get(3));
                    fragmentStack.remove(3);
                    transaction.remove(fragmentStack.get(2));
                    fragmentStack.remove(2);
                } else {
                    transaction.remove(fragmentStack.lastElement());
                    fragmentStack.remove(fragmentStack.lastElement());
                }
                transaction.show(fragmentStack.lastElement());
                transaction.commit();

                tvCompanyList.setTextColor(ContextCompat.getColor(context, R.color.color_mine_gray_text));
                tvCompanyList.setClickable(false);
                tvCompany.setTextColor(ContextCompat.getColor(context, R.color.color_mine_gray_text));
                tvDepart.setVisibility(View.GONE);
                tvCompany.setVisibility(View.GONE);
                type = contactsType;

                break;
            case R.id.tv_company://公司
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.remove(fragmentStack.lastElement());
                fragmentStack.remove(fragmentStack.lastElement());
                transaction.show(fragmentStack.lastElement());
                transaction.commit();

                if (tvCompanyList.getVisibility() == View.VISIBLE) {
                    type = Constant.LIST_DEPART;
                } else {
                    type = contactsType;
                }

                tvCompany.setTextColor(ContextCompat.getColor(context, R.color.color_mine_gray_text));
                tvCompany.setClickable(false);
                tvDepart.setVisibility(View.GONE);
                break;
        }
    }

    private void showView() {
        if (isLocalCompany || (tvCompanyList.getVisibility() == View.GONE && type.equals(Constant.LIST_USER))) {
            tvCompanyList.setVisibility(View.GONE);
        } else {
            tvCompanyList.setVisibility(View.VISIBLE);
        }
        if (type.equals(Constant.LIST_DEPART)) {
            tvCompany.setVisibility(View.VISIBLE);
            tvDepart.setVisibility(View.GONE);
            if (!isLocalCompany) {
                tvCompanyList.setTextColor(ContextCompat.getColor(context, R.color.color_title_in));
            }
            tvCompanyList.setClickable(true);
        } else if (type.equals(Constant.LIST_USER)) {
            tvDepart.setVisibility(View.VISIBLE);
            tvCompany.setTextColor(ContextCompat.getColor(context, R.color.color_title_in));
            tvCompany.setClickable(true);
        } else if (type.equals(Constant.LIST_COMPANY)) {
            tvCompany.setVisibility(View.GONE);
            tvDepart.setVisibility(View.GONE);
            tvCompanyList.setClickable(false);
            tvCompany.setClickable(false);
        } else {
            llAddGroupTitle.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Constant.SELECEDIDS.clear();
        HttpUtil.getInstance().cancelRequest(URLConstant.URL_SEARCH_BY_STAFF);
    }

    @Override
    public void onBackPressed() {

        if (llList.getVisibility() == View.VISIBLE) {
            transaction = getSupportFragmentManager().beginTransaction();
            if (fragmentStack.size() > 1) {
                transaction.remove(fragmentStack.lastElement());
                fragmentStack.remove(fragmentStack.lastElement());
                transaction.show(fragmentStack.lastElement());
                transaction.commit();
                if (type.equals(Constant.LIST_DEPART)) {
                    if (tvCompanyList.getVisibility() == View.GONE) {
                        llAddGroupTitle.setVisibility(View.GONE);
                    } else {
                        tvCompany.setVisibility(View.GONE);
                        tvCompanyList.setTextColor(ContextCompat.getColor(context, R.color.color_mine_gray_text));
                        tvCompanyList.setClickable(false);
                        type = contactsType;
                    }

                } else if (type.equals(Constant.LIST_USER) || type.equals("addTo")) {
                    if ((tvCompanyList.getVisibility() == View.GONE && type.equals(Constant.LIST_USER))) {
                        type = contactsType;
                    } else {
                        type = Constant.LIST_DEPART;
                    }
                    tvDepart.setVisibility(View.GONE);
                    tvCompany.setTextColor(ContextCompat.getColor(context, R.color.color_mine_gray_text));
                    tvCompany.setClickable(false);
                } else {
                    llAddGroupTitle.setVisibility(View.GONE);
                }

            } else {
                finish();
            }
        } else {
            llSearchList.setVisibility(View.GONE);
            llList.setVisibility(View.VISIBLE);
            etResumeSearch.setText("");
        }
    }

    private void sendMessages() {
        String content = "简历分享【" + resumeName + "】：" + "\n" + "http://www.resume.com/" + resumeId;
        String toChatUsername = "";
        String toNickName = "";
        String toNickUrl = "";
        for (AddGroupEventBus data : selectDatas) {
            toChatUsername = data.getEaseId();
            toNickName = data.getName();
            toNickUrl = data.getFrendPhoto();

            EMUtil.sendResumeMessage(context, EaseConstant.CHATTYPE_SINGLE, toChatUsername, toNickName, toNickUrl, content, resumeSearch);

            /*EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername,
                    EaseCommonUtils.getConversationType(EaseConstant.CHATTYPE_SINGLE), true);
            // 增加自己特定的属性
            message.setAttribute("nickName", SPUtil.loadName(context));//自己的昵称
            message.setAttribute("nickUrl", SPUtil.loadHeadPhoto(context));//自己的头像

            conversation.setExtField(toNickName + " " + toNickUrl);//好友的昵称,好友的头像

            //send message
            EMClient.getInstance().chatManager().sendMessage(message);*/

            //环信客服建议延迟
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ToastUtil.showShort(context, "已发送");
        finish();

    }

    /**
     * 启动员工列表页面
     *
     * @param context
     */
    public static void startAction(Context context, ResumeSearch resumeSearch) {
        Intent intent = new Intent(context, AddGroupActivity.class);
        intent.putExtra("resumeData", resumeSearch);
        context.startActivity(intent);
    }

    private void updateUI(final Object o) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImeUtil.hideSoftKeyboard(etResumeSearch);
                DialogUtil.getInstance().closeLoadingDialog();
                if (o instanceof AddGroupSearchResponse) {
                    data.clear();
                    data = ((AddGroupSearchResponse) o).getData();
                    if (data.size() > 0) {
                        addGroupSearchAdapter.updateData(data);
                    } else {
                        ToastUtil.showShort(context, "没有符合的结果");
                    }
                } else {
                    ToastUtil.showShort(context, o.toString());
                }
            }
        });
    }

    @Override
    public void onSuccess(Object obj) {
        updateUI(obj);
    }

    @Override
    public void onFailed(String str) {
        updateUI(str);
    }

    @Override
    public void onError(String str) {
        updateUI(str);
    }
}
