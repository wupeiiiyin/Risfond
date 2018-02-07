package com.risfond.rnss.contacts.activity;

import com.risfond.rnss.entry.CompanyList;
import com.risfond.rnss.entry.DepartList;
import com.risfond.rnss.entry.UserList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbott on 2017/9/5.
 */

public class CompanyData {

    private List<CompanyList> companyLists = new ArrayList<>();
    private List<DepartList> departLists = new ArrayList<>();
    private List<UserList> userLists = new ArrayList<>();

    public List<CompanyList> getCompanyLists() {
        return companyLists;
    }

    public void setCompanyLists(List<CompanyList> companyLists) {
        this.companyLists = companyLists;
    }

    public List<DepartList> getDepartLists() {
        return departLists;
    }

    public void setDepartLists(List<DepartList> departLists) {
        this.departLists = departLists;
    }

    public List<UserList> getUserLists() {
        return userLists;
    }

    public void setUserLists(List<UserList> userLists) {
        this.userLists = userLists;
    }
}
