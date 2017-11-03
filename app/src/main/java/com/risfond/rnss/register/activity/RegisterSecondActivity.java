package com.risfond.rnss.register.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.CommonUtil;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.IdentityUtils;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.WheelDialog;
import com.risfond.rnss.entry.City;
import com.risfond.rnss.entry.Province;
import com.risfond.rnss.entry.RegisterCompany;
import com.risfond.rnss.entry.RegisterCompanyResponse;
import com.risfond.rnss.entry.RegisterCreateData;
import com.risfond.rnss.entry.RegisterResponse;
import com.risfond.rnss.register.modelimpl.RegisterCompanyImpl;
import com.risfond.rnss.register.modelimpl.RegisterImpl;
import com.risfond.rnss.register.modelinterface.IRegister;
import com.risfond.rnss.register.modelinterface.IRegisterCompany;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterSecondActivity extends BaseActivity implements WheelDialog.TimeSelected,
        WheelDialog.DataSelected, ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.ll_title)
    RelativeLayout llTitle;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.et_name_cn)
    EditText etNameCn;
    @BindView(R.id.et_name_en)
    EditText etNameEn;
    @BindView(R.id.et_idcard)
    EditText etIdcard;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_constellation)
    TextView tvConstellation;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_nation)
    TextView tvNation;
    @BindView(R.id.tv_marriage)
    TextView tvMarriage;
    @BindView(R.id.tv_bear)
    TextView tvBear;
    @BindView(R.id.tv_property)
    TextView tvProperty;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    private Context context;
    private int type;//1:公司，2:星座，3:性别，4:民族，5:婚姻，6:生育，7:户口性质，8:户籍地址
    private List<RegisterCompany> companyList = new ArrayList<>();
    private List<RegisterCompany> constellationList = new ArrayList<>();
    private List<RegisterCompany> sexList = new ArrayList<>();
    private List<RegisterCompany> nationList = new ArrayList<>();
    private List<RegisterCompany> marriageList = new ArrayList<>();
    private List<RegisterCompany> bearList = new ArrayList<>();
    private List<RegisterCompany> propertyList = new ArrayList<>();
    private List<Province> provinces = new ArrayList<>();
    private List<List<City>> cityies = new ArrayList<>();
    private String company, birthday, constellation, sex, nation, marriage, bear, property, address;
    private String Id, companyCode, constellationCode, sexCode, nationCode, marriageCode, bearCode, propertyCode, addressCode;
    private String cnName, enName, idCard;
    private int[] arrs = {R.array.constellation, R.array.sex, R.array.nation, R.array.marriage, R.array.bear, R.array.property};
    private List<List<RegisterCompany>> lists = new ArrayList<>();

    private Map<String, String> request = new HashMap<>();
    private IRegister iRegister;
    private IRegisterCompany iRegisterCompany;
    private int operateType = 0;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_register_sencond;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = RegisterSecondActivity.this;
        tvTitle.setText(R.string.regist_title2);
        tvNext.setText(R.string.register_next);

        lists.add(constellationList);
        lists.add(sexList);
        lists.add(nationList);
        lists.add(marriageList);
        lists.add(bearList);
        lists.add(propertyList);
        createData();

        iRegister = new RegisterImpl();
        iRegisterCompany = new RegisterCompanyImpl();
        Id = getIntent().getStringExtra("Id");
        requestCompanyData();
    }

    private void requestService() {
        operateType = 1;
        DialogUtil.getInstance().showLoadingDialog(context, "上传中");
        request.put("Id", Id);
        request.put("CompanyName", company);
        request.put("CompanyId", companyCode);
        request.put("Cname", cnName);
        request.put("Ename", enName);
        request.put("IdentityId", idCard);
        request.put("Birthday", birthday);
        request.put("Constellation", constellationCode);
        request.put("Gender", sexCode);
        request.put("Nation", nationCode);
        request.put("Marriage", marriageCode);
        request.put("Children", bearCode);
        request.put("RegisteredResidence", propertyCode);
        request.put("RegisteredAddress", addressCode);

        iRegister.registerRequest("", request, URLConstant.URL_ADD_PERSON_INFO, this);
    }

    private void requestCompanyData() {
        operateType = 0;
        DialogUtil.getInstance().showLoadingDialog(context, "获取公司列表中...");
        iRegisterCompany.registerCompanyRequest("", null, URLConstant.URL_GET_COMPANY, this);
    }

    /**
     * 创建数据
     */
    private void createData() {
        for (int i = 0; i < lists.size(); i++) {
            String[] data = getResources().getStringArray(arrs[i]);
            for (String str : data) {
                RegisterCompany data1 = new RegisterCompany();
                data1.setCompanyName(str.split(" ")[0]);
                data1.setCompanyId(Integer.parseInt(str.split(" ")[1]));

                lists.get(i).add(data1);
            }
        }

        provinces = CommonUtil.createRegisterProvince(context);
        for (Province province : provinces) {
            cityies.add(province.getCities());
        }
        WheelDialog.getInstance().createProvinceSelectDialog(context, "户籍地址", provinces, cityies, this);
    }

    @OnClick({R.id.tv_next, R.id.tv_company, R.id.tv_birthday, R.id.tv_constellation, R.id.tv_sex,
            R.id.tv_nation, R.id.tv_marriage, R.id.tv_bear, R.id.tv_property, R.id.tv_address})
    public void onClick(View view) {
        ImeUtil.hideSoftKeyboard(etNameCn);
        switch (view.getId()) {
            case R.id.tv_next:
                submit();
                break;
            case R.id.tv_company://公司
                type = 1;
                if (companyList.size() == 0) {
                    requestCompanyData();
                } else {
                    company = tvCompany.getText().toString().trim();
                    WheelDialog.getInstance().showDataDialog(context, "公司", company, companyList, this);
                }
                break;
            case R.id.tv_birthday://生日
                birthday = tvBirthday.getText().toString().trim();
                WheelDialog.getInstance().showDateSelectDialog(context, "出生年月",
                        WheelDialog.TYPE_FORMAT_yyyyMMdd, WheelDialog.TYPE_YEAR_MONTH_DATE, birthday, this);
                break;
            case R.id.tv_constellation://星座
                type = 2;
                constellation = tvConstellation.getText().toString().trim();
                WheelDialog.getInstance().showDataDialog(context, "星座", constellation, constellationList, this);
                break;
            case R.id.tv_sex://性别
                type = 3;
                sex = tvSex.getText().toString().trim();
                WheelDialog.getInstance().showDataDialog(context, "性别", sex, sexList, this);
                break;
            case R.id.tv_nation://民族
                type = 4;
                nation = tvNation.getText().toString().trim();
                WheelDialog.getInstance().showDataDialog(context, "民族", nation, nationList, this);
                break;
            case R.id.tv_marriage://婚姻
                type = 5;
                marriage = tvMarriage.getText().toString().trim();
                WheelDialog.getInstance().showDataDialog(context, "婚姻", marriage, marriageList, this);
                break;
            case R.id.tv_bear://生育
                type = 6;
                bear = tvBear.getText().toString().trim();
                WheelDialog.getInstance().showDataDialog(context, "生育", bear, bearList, this);
                break;
            case R.id.tv_property://户口性质
                type = 7;
                property = tvProperty.getText().toString().trim();
                WheelDialog.getInstance().showDataDialog(context, "户口性质", property, propertyList, this);
                break;
            case R.id.tv_address://户籍地址
                type = 8;
                WheelDialog.getInstance().ShowProvinceSelectDialog();
                break;

        }

    }

    private void submit() {
        company = tvCompany.getText().toString().trim();
        cnName = etNameCn.getText().toString().trim();
        enName = etNameEn.getText().toString().trim();
        idCard = etIdcard.getText().toString().trim();
        birthday = tvBirthday.getText().toString().trim();
        constellation = tvConstellation.getText().toString().trim();
        sex = tvSex.getText().toString().trim();
        nation = tvNation.getText().toString().trim();
        marriage = tvMarriage.getText().toString().trim();
        bear = tvBear.getText().toString().trim();
        property = tvProperty.getText().toString().trim();
        address = tvAddress.getText().toString().trim();

        if (TextUtils.isEmpty(company)) {
            ToastUtil.showShort(context, "请选择公司");
        } else if (TextUtils.isEmpty(cnName)) {
            ToastUtil.showShort(context, "请输入中文姓名");
        } else if (TextUtils.isEmpty(enName)) {
            ToastUtil.showShort(context, "请输入英文姓名");
        } else if (TextUtils.isEmpty(idCard)) {
            ToastUtil.showShort(context, "请输入身份证号");
        } else if (TextUtils.isEmpty(birthday) || TextUtils.isEmpty(constellation) || TextUtils.isEmpty(sex)) {
            ToastUtil.showShort(context, "请输入正确的身份证号");
        } else if (TextUtils.isEmpty(nation)) {
            ToastUtil.showShort(context, "请选择民族");
        } else if (TextUtils.isEmpty(marriage)) {
            ToastUtil.showShort(context, "请选择婚姻状态");
        } else if (TextUtils.isEmpty(bear)) {
            ToastUtil.showShort(context, "请选择生育状态");
        } else if (TextUtils.isEmpty(property)) {
            ToastUtil.showShort(context, "请选择户口性质");
        } else if (TextUtils.isEmpty(address)) {
            ToastUtil.showShort(context, "请输入户籍地址");
        } else {
            requestService();
        }
    }


    @OnTextChanged({R.id.et_idcard})
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean checkFlag = IdentityUtils.checkIDCard(s.toString());
        if (checkFlag) {
            birthday = IdentityUtils.getBirthday(s.toString());
            constellation = IdentityUtils.getConstellations(birthday);
            sex = IdentityUtils.getSex(s.toString());

            for (RegisterCompany data : constellationList) {
                if (data.getCompanyName().equals(constellation)) {
                    constellationCode = String.valueOf(data.getCompanyId());
                }
            }

            for (RegisterCompany data : sexList) {
                if (data.getCompanyName().equals(sex)) {
                    sexCode = String.valueOf(data.getCompanyId());
                }
            }

            tvBirthday.setText(birthday);
            tvConstellation.setText(constellation);
            tvSex.setText(sex);
        } else {
            birthday = "";
            constellation = "";
            sex = "";

            tvBirthday.setText(birthday);
            tvConstellation.setText(constellation);
            tvSex.setText(sex);
        }
    }

    private void setSelectData(String value, String code) {
        switch (type) {
            case 1:
                tvCompany.setText(value);
                companyCode = code;
                break;
            case 2:
                tvConstellation.setText(value);
                constellationCode = code;
                break;
            case 3:
                tvSex.setText(value);
                sexCode = code;
                break;
            case 4:
                tvNation.setText(value);
                nationCode = code;
                break;
            case 5:
                tvMarriage.setText(value);
                marriageCode = code;
                break;
            case 6:
                tvBear.setText(value);
                bearCode = code;
                break;
            case 7:
                tvProperty.setText(value);
                propertyCode = code;
                break;
            case 8:
                tvAddress.setText(value);
                addressCode = code;
                break;
        }
    }

    public static void startAction(Context context, String Id) {
        Intent intent = new Intent(context, RegisterSecondActivity.class);
        intent.putExtra("Id", Id);
        context.startActivity(intent);
    }

    @Override
    public void onTimeSelected(String time) {
        tvBirthday.setText(time);
    }

    @Override
    public void onDataSelected(String value, String code) {
        setSelectData(value, code);
    }

    private void updateUI(final Object o) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (operateType == 0) {
                    if (o instanceof RegisterCompanyResponse) {
                        companyList = ((RegisterCompanyResponse) o).getData();
                    } else {
                        ToastUtil.showShort(context, o.toString());
                    }
                } else {
                    if (o instanceof RegisterResponse) {
                        RegisterThirdActivity.startAction(context, ((RegisterResponse) o).getData());
                    } else {
                        ToastUtil.showShort(context, o.toString());
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
}
