package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.common.utils.ToastUtil;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class AddTheTransactionActivity extends BaseActivity implements View.OnClickListener {
    private  TransactiondatabaseSQL ttdbsqlite  ;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edit_addthetransaction_content)
    EditText editAddthetransactionContent;
    @BindView(R.id.ll_addthetransaction_time)
    LinearLayout llAddthetransactionTime;
    @BindView(R.id.ll_addthetransaction_reminding)
    LinearLayout llAddthetransactionReminding;
    @BindView(R.id.tv_addthetransaction_commit)
    TextView tvAddthetransactionCommit;
    private Button btn;
    private ListView llllll;
    private Cursor c;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_the_transaction;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ttdbsqlite = new TransactiondatabaseSQL(this.getApplication());
        tvTitle.setText("添加事务");
        ButterKnife.bind(this);
        c = ttdbsqlite.checktransaction();
        btn = (Button) findViewById(R.id.xxx);
        llllll = (ListView) findViewById(R.id.llllll);
        btn.setOnClickListener(this);
        String trim = "abcdiefjiijij";
        ContentValues cv = new ContentValues();
        cv.put("name",trim);
        ttdbsqlite.Addtransaction(cv);
        ToastUtil.showShort(getApplication(),"添加成功");
    }
    @OnClick({R.id.ll_addthetransaction_time, R.id.ll_addthetransaction_reminding, R.id.tv_addthetransaction_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_addthetransaction_time:
                startActivity(TransactiontimeActivity.class, false);
                break;
            case R.id.ll_addthetransaction_reminding:
                startActivity(RemindingTimeActivity.class, false);
                break;
//                Intent intent = new Intent(this,AgainRemindingActivity.class);
//                startActivity(intent);
//                String arr_list = editAddthetransactionContent.getText().toString();
//                if (arr_list == null || arr_list.equals("")) {
//                    Toast.makeText(getApplicationContext(), "添加的内容不能为空", Toast.LENGTH_SHORT).show();
//
//                }
//                else {
//                    Intent intent = new Intent(this,RemindingActivity.class);
//                    startActivity(intent.putExtra("arr_list",arr_list));
//                    finish();
//                    break;
//                }
            //添加
            case R.id.tv_addthetransaction_commit:
                String trim = "abcdiefjiijij";
                ContentValues cv = new ContentValues();
                cv.put("name",trim);
                ttdbsqlite.Addtransaction(cv);

                ToastUtil.showShort(getApplication(),"添加成功");
                break;
        }
    }

    //查找就
    @Override
    public void onClick(View v) {
        ArrayList<String> list = new ArrayList();
        View view2 = LayoutInflater.from(AddTheTransactionActivity.this).inflate(R.layout.activity_text, null);
        llllll = (ListView) view2.findViewById(R.id.llllll);
        c.moveToFirst();
        while(c.moveToNext()){
            String cursorString1 = c.getString(c.getColumnIndex("name"));
            list.add( "内容:"+cursorString1);

        }
        ArrayAdapter Adapter = new ArrayAdapter(AddTheTransactionActivity.this,android.R.layout.simple_expandable_list_item_1,list);
        llllll.setAdapter(Adapter);


        AlertDialog.Builder builder2 = new AlertDialog.Builder(AddTheTransactionActivity.this);
        builder2.setView(view2);
        builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder2.show();
    }
}
