package com.risfond.rnss.group;

import com.risfond.rnss.entry.UserList;

import java.util.ArrayList;

/**
 * Created by Abbott on 2017/6/22.
 */

public class AddGroupEventBus {
    private boolean isLocalCompany;
    private String type;
    private String companyId;
    private String departId;

    private String name;
    private String easeId;
    private String frendPhoto;

    private ArrayList<UserList> userLists;

    public AddGroupEventBus(String type, String name, String companyId, String departId, boolean isLocalCompany) {
        this.isLocalCompany = isLocalCompany;
        this.type = type;
        this.companyId = companyId;
        this.departId = departId;
        this.name = name;
    }

    public AddGroupEventBus(String type, String name, ArrayList<UserList> userLists) {
        this.userLists = userLists;
        this.type = type;
        this.name = name;
    }

    public AddGroupEventBus(String type, String easeId, String name, String frendPhoto) {
        this.type = type;
        this.easeId = easeId;
        this.name = name;
        this.frendPhoto = frendPhoto;
    }

    public String getEaseId() {
        return easeId;
    }

    public String getFrendPhoto() {
        return frendPhoto;
    }

    public String getName() {
        return name;
    }

    public boolean isLocalCompany() {
        return isLocalCompany;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getDepartId() {
        return departId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<UserList> getUserLists() {
        return userLists;
    }

    public void setUserLists(ArrayList<UserList> userLists) {
        this.userLists = userLists;
    }
}
