package com.risfond.rnss.home.signature;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个性签名编辑界面
 * Created by Abbott on 2018/2/5.
 */

public class SignatureActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_personal_sign)
    EditText et_personal_sign;
    @BindView(R.id.btn_change_signature)
    Button btn_change_signature;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_personal_signature;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvTitle.setText("编辑签名");
    }

    @OnClick({R.id.btn_change_signature})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_signature:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("edit_signature",et_personal_sign.getText().toString());
                intent.putExtras(bundle);
                setResult(0x02,intent);
                finish();
                break;
            default:
                break;
        }
    }

}
