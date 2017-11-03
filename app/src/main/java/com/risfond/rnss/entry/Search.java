package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/5/19.
 */

public class Search {
    private List<UserList> stafflist;
    private List<CompanyList> companylist;

    public List<CompanyList> getCompanylist() {
        return companylist;
    }

    public void setCompanylist(List<CompanyList> companylist) {
        this.companylist = companylist;
    }

    public List<UserList> getStafflist() {

        return stafflist;
    }

    public void setStafflist(List<UserList> stafflist) {
        this.stafflist = stafflist;
    }
}
