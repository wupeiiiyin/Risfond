package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/5/17.
 */

public class Login {

    private int Id;
    private String Name = "";
    private String EaseMobAccount = "";
    private String EaseMobPwd = "";
    private String Token = "";
    private String CompanyName = "";
    private String HeadPhoto = "";
    private String CharId = "";
    private int CompanyId;
    private String TelNumber = "";
    private String MobileNumber = "";

    public String getTelNumber() {
        return TelNumber;
    }

    public void setTelNumber(String telNumber) {
        TelNumber = telNumber;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getCharId() {
        return CharId;
    }

    public void setCharId(String charId) {
        CharId = charId;
    }

    public String getHeadPhoto() {
        return HeadPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        HeadPhoto = headPhoto;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEaseMobAccount() {
        return EaseMobAccount;
    }

    public void setEaseMobAccount(String easeMobAccount) {
        EaseMobAccount = easeMobAccount;
    }

    public String getEaseMobPwd() {
        return EaseMobPwd;
    }

    public void setEaseMobPwd(String easeMobPwd) {
        EaseMobPwd = easeMobPwd;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int companyId) {
        CompanyId = companyId;
    }
}
