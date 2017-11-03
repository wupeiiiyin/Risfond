package com.risfond.rnss.entry;

import java.math.BigDecimal;

/**
 * Created by vicky on 2017/8/3.
 */

public class InvoiceDetail {

    /**
    * 发票类型: int Type   1 普通发票 2专用发票
    * 开票金额: decimal Amount
    * 公司名称: string ClientName
    * 申请时间： string ApplicationDate
    * 发票状态： int Status   1 申请中   2 已开出 3 已作废
    * 回款状态： string IncomeStatus
    * 申请用户： int StaffId
    * 申请用户名称： string StaffName
    * 归属公司： CompanyName
    * 到账周期： string AccountDay
    * 编号： int Id
    * 开票人： string DrawerStaffName
    * 其他说明： string Memo
    * 税务识别号： string TaxIdentityNumber
    * 联系电话： string CallNumber
    * 公司地址： string ClientAddress
    * 开户名称： string BankAccountName
    * 开户行： string BankName
    * 银行账户： string BankAccount
    */

    private int Type;
    private BigDecimal Amount;
    private String ClientName;
    private String ApplicationDate;
    private int Status;
    private String IncomeStatus;
    private int StaffId;
    private String StaffName;
    private String CompanyName;
    private String AccountDay;
    private int Id;
    private String DrawerStaffName;
    private String Memo;
    private String TaxIdentityNumber;
    private String CallNumber;
    private String ClientAddress;
    private String BankAccountName;
    private String BankName;
    private String BankAccount;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal amount) {
        Amount = amount;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getApplicationDate() {
        return ApplicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        ApplicationDate = applicationDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getIncomeStatus() {
        return IncomeStatus;
    }

    public void setIncomeStatus(String incomeStatus) {
        IncomeStatus = incomeStatus;
    }

    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(int staffId) {
        StaffId = staffId;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getAccountDay() {
        return AccountDay;
    }

    public void setAccountDay(String accountDay) {
        AccountDay = accountDay;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDrawerStaffName() {
        return DrawerStaffName;
    }

    public void setDrawerStaffName(String drawerStaffName) {
        DrawerStaffName = drawerStaffName;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public String getTaxIdentityNumber() {
        return TaxIdentityNumber;
    }

    public void setTaxIdentityNumber(String taxIdentityNumber) {
        TaxIdentityNumber = taxIdentityNumber;
    }

    public String getCallNumber() {
        return CallNumber;
    }

    public void setCallNumber(String callNumber) {
        CallNumber = callNumber;
    }

    public String getClientAddress() {
        return ClientAddress;
    }

    public void setClientAddress(String clientAddress) {
        ClientAddress = clientAddress;
    }

    public String getBankAccountName() {
        return BankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        BankAccountName = bankAccountName;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }
}
