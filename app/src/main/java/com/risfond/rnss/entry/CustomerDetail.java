package com.risfond.rnss.entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbott on 2017/7/7.
 */

public class CustomerDetail {
    /**
     * ClientId : 109881
     * Code : 109881
     * Name : 北京人众人科技发展有限公司
     * Industry : 房地产开发/建筑与工程
     * Address : f'd's
     * CertificationStatus : 0
     * FHlist : [{"HFDate":"2016.12.23 15:03","HFSafffName":"黄小平","HFContent":"f的是非得失"}]
     */

    private int ClientId;
    private String Code;
    private String Name;
    private String Industry;
    private String Address;
    private int CertificationStatus;
    private ArrayList<CustomerRecord> FHlist;

    public int getCertificationStatus() {
        return CertificationStatus;
    }

    public void setCertificationStatus(int certificationStatus) {
        CertificationStatus = certificationStatus;
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

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String Industry) {
        this.Industry = Industry;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public ArrayList<CustomerRecord> getFHlist() {
        return FHlist;
    }

    public void setFHlist(ArrayList<CustomerRecord> FHlist) {
        this.FHlist = FHlist;
    }
}
