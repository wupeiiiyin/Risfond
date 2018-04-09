package com.risfond.rnss.message.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.utils.GlideUtil;
import com.hyphenate.util.NetUtils;
import com.mob.MobSDK;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.callback.ScanCallBack;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.em.EMHelper;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.contacts.fragment.ContactsFragment;
import com.risfond.rnss.entry.UnReadMessageCountEventBus;
import com.risfond.rnss.entry.UserInfo;
import com.risfond.rnss.home.fragment.HomeFragment;
import com.risfond.rnss.home.homesearch.activity.HomeSearchActivity;
import com.risfond.rnss.login.activity.LoginActivity;
import com.risfond.rnss.message.fragment.MessageFragment;
import com.risfond.rnss.mine.fragment.MineFragment;
import com.risfond.rnss.mine.modleImpl.UserInfoImpl;
import com.risfond.rnss.mine.modleInterface.IUserInfo;
import com.risfond.rnss.scan.IScan;
import com.risfond.rnss.scan.ScanActivity;
import com.risfond.rnss.scan.ScanImpl;
import com.risfond.rnss.update.UpdateManager;
import com.risfond.rnss.widget.ScrollInterceptScrollView;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 主页面
 */
public class MainActivity extends BaseActivity implements ResponseCallBack, ScanCallBack {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_home_search)
    TextView tvHomeSearch;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.fl_chat)
    FrameLayout flChat;
    @BindView(R.id.tv_unread_number)
    TextView tvUnreadNumber;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_message)
    RadioButton rbMessage;
    @BindView(R.id.rb_contacts)
    RadioButton rbContacts;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;
    @BindView(R.id.home_scan)
    ImageView homeScan;
    @BindView(R.id.home_more)
    ImageView homeMore;
    @BindView(R.id.ll_home_header)
    LinearLayout llHomeHeader;
    @BindView(R.id.id_title_rootview)
    RelativeLayout titlerootview;
    @BindView(R.id.tv_title_img)
    TextView tvTitleImg;
    @BindView(R.id.ll_tv_img)
    LinearLayout llTvImg;
    @BindView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @BindView(R.id.ll_title_search)
    LinearLayout llTitleSearch;
    @BindView(R.id.ll_title_share)
    LinearLayout llTitleShare;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.ll_right_text)
    LinearLayout llRightText;
    @BindView(R.id.id_title_right)
    LinearLayout idTitleRight;
    @BindView(R.id.tv_home_search_scroll)
    TextView tvHomeSearchScroll;
    @BindView(R.id.home_scan_scroll)
    ImageView homeScanScroll;
    @BindView(R.id.home_more_scroll)
    ImageView homeMoreScroll;
    @BindView(R.id.ll_home_heade_scroll)
    LinearLayout llHomeHeadeScroll;
    @BindView(R.id.lin_main)
    LinearLayout linMain;
    private Context context;
    private FragmentTransaction transaction;
    private MessageFragment messageFragment;
    private ContactsFragment contactsFragment;
    private MineFragment mineFragment;
    private HomeFragment homeFragment;
    private int allUnReadCount;

    private IUserInfo iUserInfo;
    private Map<String, String> request = new HashMap<>();
    private UserInfo userInfo;
    private UpdateManager manager;
    private List<EMConversation> emConversations = new ArrayList<>();
    private int tip;
    private EMMessageListener msgListener;
    private Window window;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;

    private IScan iScan;
    private boolean translucentStatus;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = MainActivity.this;
        llBack.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);
        llHomeHeader.setVisibility(View.VISIBLE);
        iScan = new ScanImpl();

        initHomeFragment();
        showEMLoginInfo(getIntent());
        iUserInfo = new UserInfoImpl();
        requestService();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        manager = new UpdateManager(context, false);

        initMessageListener();

        //提前初始化shareSDK
        MobSDK.init(context);

    }

    /**
     * 加载所有会话列表
     */
    private void initAllConversations() {
        emConversations.clear();
        tip = 0;
        EMHelper.getInstance().updateChatInfo();
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        if (conversations.size() > 0) {
            for (String key : conversations.keySet()) {
                int unRead = conversations.get(key).getUnreadMsgCount();
                tip += unRead;
            }
        }
        setTip(tip);
    }

    private void initMessageListener() {
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                EMClient.getInstance().chatManager().importMessages(messages);
                for (EMMessage emMessage : messages) {
                    if (emMessage.getChatType() == EMMessage.ChatType.GroupChat) {
                        if (SPUtil.loadCloseMsg(context)) {
                            //屏蔽了消息
                        } else {
                            if (EaseUI.getInstance().chattingId.equals(emMessage.getTo())) {//正在聊天的人屏蔽消息

                            } else {
                                EMHelper.getInstance().getNotifier().onNewMsg(emMessage);

                            }
                        }
                    } else {
                        if (EaseUI.getInstance().chattingId.equals(emMessage.getFrom())) {//正在聊天的人屏蔽消息

                        } else {
                            EMHelper.getInstance().getNotifier().onNewMsg(emMessage);
                        }
                    }
                    //更新会话列表
                    initAllConversations();
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    //scroolView滑动监听
    @Override
    protected void onResume() {

        super.onResume();
        //        homeFragment.mScrollView.setOnScrollistener(new CustomScroller.OnScrollistener() {
        //            @Override
        //            public void onScroll(int startY, int endY) {
        //                //根据scrollview滑动更改标题栏透明度
        //                changeAphla(startY, endY);
        //            }
        //
        //        });
        //    }
        //        homeFragment.mScrollView.setOnScrollistener(new ScrollInterceptScrollView.OnScrollistener() {
        //            @Override
        //            public void onScroll(int startY, int endY) {
        //                //根据scrollview滑动更改标题栏透明度
        //                changeAphla(startY, endY);
        //            }
        //        });
        //    }
        //        /**
        //         * 根据内容窗体的移动改变标题栏背景透明度
        //         *
        //         * @param startY scrollview开始滑动的y坐标（相对值）
        //         * @param endY   scrollview结束滑动的y坐标（相对值）
        //         */
        //    private void changeAphla(int startY, int endY) {
        //        //获取标题高度
        //        int titleHeight = llHomeHeader.getMeasuredHeight();
        //        //获取背景高度
        //        int backHeight = llHomeHeadeScroll.getMeasuredHeight();
        //
        //        //获取控件的绝对位置坐标
        //        int[] location = new int[2];
        //        llHomeHeadeScroll.getLocationInWindow(location);
        //        //从屏幕顶部到控件顶部的坐标位置Y
        //        int currentY = location[1];
        //        //表示回到原位（滑动到顶部）
        //        if (currentY >= 0) {
        ////            title.getBackground().mutate().setAlpha(0);
        //            llHomeHeader.setBackgroundColor(Color.WHITE);
        //        }
        homeFragment.mScrollView.setbanlaceInterface(new ScrollInterceptScrollView.isbanlanceInterface() {


            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void isbanlce(float length) {
                Log.d("滑动距离", length + "");
                if (length >= 100) {
                    llHomeHeadeScroll.setVisibility(View.VISIBLE);
                    window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.color_white));
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                    return;
                } else {
                    llHomeHeadeScroll.setVisibility(View.GONE);
                    getWindow().setStatusBarColor(getResources().getColor(R.color.color_blue)); // 蓝色
//                    getWindow().setStatusBarColor(ContextCompat.getDrawable(context,)));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                }

            }
        });
        if (EMClient.getInstance().isLoggedInBefore()) {
            if (NetUtils.hasNetwork(context)) {
                if (manager.dialog == null) {
                    manager.checkUpdate();
                } else if (!manager.dialog.isShowing()) {
                    manager.checkUpdate();
                } else {
                    if (manager.progress == 100) {
                        manager.installApk();
                    }
                }
            }
            initAllConversations();
        }
    }

    //    //状态栏颜色设置
    //    public void changeSystemBarColor() {
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    //            setTranslucentStatus(true);
    //            SystemBarTintManager tintManager = new SystemBarTintManager(this);
    //            tintManager.setStatusBarTintEnabled(true);
    //            tintManager.setStatusBarTintResource(R.color.colorAccent);//通知栏所需颜色
    //        }
    //
    //    }

    private void requestService() {
        request.put("id", String.valueOf(SPUtil.loadId(context)));
        iUserInfo.userInfoRequest(request, SPUtil.loadToken(context), URLConstant.URL_USER_INFO, this);
    }

    @Subscribe
    public void onEventBus(UnReadMessageCountEventBus eventBus) {
        if ("unReadCount".equals(eventBus.getType())) {
            allUnReadCount = eventBus.getTip();
            setTip(allUnReadCount);
        }
    }

    private void setTip(final int tip) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tvUnreadNumber == null) {
                    tvUnreadNumber = (TextView) findViewById(R.id.tv_unread_number);
                }
                if (tip > 0) {
                    if (tip < 100) {
                        tvUnreadNumber.setText(String.valueOf(tip));
                    } else {
                        tvUnreadNumber.setText("...");
                    }
                    tvUnreadNumber.setVisibility(View.VISIBLE);
                } else {
                    tvUnreadNumber.setVisibility(View.INVISIBLE);
                }
            }
        });

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showEMLoginInfo(intent);
    }

    /**
     * 设置title名称
     *
     * @param str
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void titleName(String str) {
        llHomeHeader.setVisibility(View.GONE);
        llHomeHeadeScroll.setVisibility(View.GONE);
        getWindow().setStatusBarColor(getResources().getColor(R.color.color_blue)); // 蓝色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(str);
    }

    @OnClick({R.id.tv_home_search, R.id.home_scan})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home_search:
                HomeSearchActivity.startAction(context);
                break;
            case R.id.home_scan:
                Intent intent = new Intent(context, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (result.startsWith("http://staff.risfond.com")) {
                        ScanLoginRequestService(result.split("=")[1]);
                    } else {
                        Toast.makeText(MainActivity.this, "请扫描RNSS登录二维码", Toast.LENGTH_LONG).show();
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnCheckedChanged({R.id.rb_home, R.id.rb_message, R.id.rb_contacts, R.id.rb_mine})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb_home:

                if (isChecked) {
//                    llHomeHeader.setVisibility(View.VISIBLE);
//                    llHomeHeadeScroll.setVisibility(View.VISIBLE);
//                    llBack.setVisibility(View.GONE);
//                    tvTitle.setVisibility(View.GONE);
                    showHomeSearch();
                    initHomeFragment();
                    homeChecked();

                }
                break;
            case R.id.rb_message:

                if (isChecked) {
                    llHomeHeader.setVisibility(View.GONE);
                    llHomeHeadeScroll.setVisibility(View.GONE);
                    llBack.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    titleName("消息");
                    initMessageFragment();
                    messageChecked();

                }
                break;
            case R.id.rb_contacts:


                if (isChecked) {
                    llHomeHeader.setVisibility(View.GONE);
                    llHomeHeadeScroll.setVisibility(View.GONE);
                    llBack.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    titleName("联系人");

                    initContactsFragment();
                    contactsChecked();

                }
                break;
            case R.id.rb_mine:

                if (isChecked) {
                    llHomeHeader.setVisibility(View.GONE);
                    llHomeHeadeScroll.setVisibility(View.GONE);
                    llBack.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    titleName("");
                    initMineFragment();
                    mineChecked();
                }
                break;
            default:
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initTitle(){
        llHomeHeadeScroll.setVisibility(View.VISIBLE);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_white));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
    //显示主页
    private void initHomeFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            homeFragment.setArguments(bundle);
            transaction.add(R.id.fl_chat, homeFragment);
        }
        hideFragment(transaction);
        transaction.show(homeFragment);
        //提交事务
        transaction.commit();

    }

    //显示消息
    private void initMessageFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (messageFragment == null) {
            messageFragment = new MessageFragment();
            messageFragment.setArguments(bundle);
            transaction.add(R.id.fl_chat, messageFragment);
            llHomeHeader.setVisibility(View.GONE);
        }
        hideFragment(transaction);
        transaction.show(messageFragment);
        //提交事务
        transaction.commit();
    }

    //显示联系人
    private void initContactsFragment() {

        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (contactsFragment == null) {
            contactsFragment = new ContactsFragment();
            contactsFragment.setArguments(bundle);
            transaction.add(R.id.fl_chat, contactsFragment);
        }
        hideFragment(transaction);
        transaction.show(contactsFragment);
        //提交事务
        transaction.commit();
    }

    //显示我的
    private void initMineFragment() {

        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (mineFragment == null) {
            mineFragment = new MineFragment();
            bundle.putParcelable("UserInfo", userInfo);
            mineFragment.setArguments(bundle);
            transaction.add(R.id.fl_chat, mineFragment);
        }
        hideFragment(transaction);
        transaction.show(mineFragment);

        //提交事务
        transaction.commit();
    }

    /**
     * 显示主页搜索框，隐藏title
     */
    private void showHomeSearch() {
        tvTitle.setVisibility(View.GONE);
        llHomeHeader.setVisibility(View.VISIBLE);
    }

    private void homeChecked() {
        rbMessage.setChecked(false);
        rbContacts.setChecked(false);
        rbMine.setChecked(false);
    }

    private void messageChecked() {
        rbHome.setChecked(false);
        rbContacts.setChecked(false);
        rbMine.setChecked(false);
    }

    private void contactsChecked() {
        rbHome.setChecked(false);
        rbMessage.setChecked(false);
        rbMine.setChecked(false);
    }

    private void mineChecked() {
        rbHome.setChecked(false);
        rbMessage.setChecked(false);
        rbContacts.setChecked(false);
    }

    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (contactsFragment != null) {
            transaction.hide(contactsFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return false;
    }

    /**
     * 提示IM登录状态
     *
     * @param intent
     */
    private void showEMLoginInfo(Intent intent) {
        if (intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showIMLoginStateDialog(R.string.connect_conflict);
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
            showIMLoginStateDialog(R.string.connect_removed);
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
            showIMLoginStateDialog(R.string.connect_forbidden);
        }
    }

    private void showIMLoginStateDialog(int msgId) {
        DialogUtil.getInstance().showConfigMsgDialog(MainActivity.this, R.string.connect_exit, msgId, new DialogUtil.PressCallBack() {
            @Override
            public void onPressButton(int buttonIndex) {
                LoginActivity.startAction(context);
                //                SPUtil.saveCloseMsg(context, false);
                finish();
            }
        });

    }

    private void ScanLoginRequestService(String token) {
        iScan.scanRequest(SPUtil.loadToken(context), null, URLConstant.URL_SCAN_LOGIN + "?token=" + token + "&staffid=" + SPUtil.loadId(context), this);
    }

    /**
     * 启动主页面
     *
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void updateUI(final Object obj) {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (obj != null) {
                    if (obj instanceof UserInfo) {
                        userInfo = (UserInfo) obj;
                        GlideUtil.beforeDownLoadImage(context, userInfo.getHeadphoto());
                    } else if (obj instanceof String) {
                        String msg = obj.toString();
                        if (msg.equals(PropertiesUtil.getMessageTextByCode("201"))) {
                            DialogUtil.getInstance().showToLoginDialog(context);
                        }
                    }
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

    @Override
    public void onScanSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShort(context, "登录成功");
            }
        });
    }

    @Override
    public void onScanFailed(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShort(context, "登录失败");
            }
        });
    }

    @Override
    public void onScanError(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShort(context, "登录失败");
            }
        });
    }

    public void setTranslucentStatus(boolean translucentStatus) {
        this.translucentStatus = translucentStatus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class GameThread implements Runnable {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }   // 使用postInvalidate可以直接在线程中更新界面
                linMain.postInvalidate();
            }
        }
    }
}
