package com.risfond.rnss.entry;

import java.math.BigDecimal;

/**
 * Created by Abbott on 2017/7/7.
 */

public class CustomerSearch {
    /**
     * ClientId : 109881
     * Code : 109881
     * Name : 北京人众人科技发展有限公司
     * Level : 0
     * ClientImportStatus : 1
     * LastCommunicationTime : 12.23
     * JobCount : 1
     * MemoCount : 1
     * ClientSource : 广告呼入
     * ClientStatus : client-icon client-icon-7
     * OptionStatus  2：已申请  1：可申请
     */

    private int ClientId;
    private String Code;
    private String Name;
    private int Level;
    private String ClientImportStatus;
    private String LastCommunicationTime;
    private int JobCount;
    private int MemoCount;
    private String ClientSource;
    private String ClientStatus;
    private String PublicTime;
    private BigDecimal FirstAmount;
    private BigDecimal ServiceAmount;
    private String Reason;
    private String CompanyName;
    private String OptionStatus;

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getPublicTime() {
        return PublicTime;
    }

    public void setPublicTime(String publicTime) {
        PublicTime = publicTime;
    }

    public BigDecimal getFirstAmount() {
        return FirstAmount;
    }

    public void setFirstAmount(BigDecimal firstAmount) {
        FirstAmount = firstAmount;
    }

    public BigDecimal getServiceAmount() {
        return ServiceAmount;
    }

    public void setServiceAmount(BigDecimal serviceAmount) {
        ServiceAmount = serviceAmount;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public int getClientId() {
        return ClientId;
    }

    public void setClientId(int ClientId) {
        this.ClientId = ClientId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public String getClientImportStatus() {
        return ClientImportStatus;
    }

    public void setClientImportStatus(String ClientImportStatus) {
        this.ClientImportStatus = ClientImportStatus;
    }

    public String getLastCommunicationTime() {
        return LastCommunicationTime;
    }

    public void setLastCommunicationTime(String LastCommunicationTime) {
        this.LastCommunicationTime = LastCommunicationTime;
    }

    public int getJobCount() {
        return JobCount;
    }

    public void setJobCount(int JobCount) {
        this.JobCount = JobCount;
    }

    public int getMemoCount() {
        return MemoCount;
    }

    public void setMemoCount(int MemoCount) {
        this.MemoCount = MemoCount;
    }

    public String getClientSource() {
        return ClientSource;
    }

    public void setClientSource(String ClientSource) {
        this.ClientSource = ClientSource;
    }

    public String getClientStatus() {
        return ClientStatus;
    }

    public void setClientStatus(String ClientStatus) {
        this.ClientStatus = ClientStatus;
    }

    public String getOptionStatus() {
        return OptionStatus;
    }

    public void setOptionStatus(String optionStatus) {
        OptionStatus = optionStatus;
    }
}
