package com.risfond.rnss.entry;

import java.io.Serializable;
import java.util.ArrayList;

import static android.R.attr.type;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/4
 * @desc
 */
public class SuccessCaseWhole extends BaseWhole implements Serializable {
    public String type = "";//类型  默认不用传值
    public String keyWords = "";//搜索关键字
    public int pageIndex = 1;//页数
    public int pageSize = 15;//每页条数
    public String startTime = "";//开始时间 ,默认不传
    public String endTime = "";//结束时间，不传默认当前时间
    public ArrayList<String> workLocation;//工作地点 id
    public ArrayList<String> workIndusty;//工作行业 id
    public ArrayList<String> workLocations;//工作地点 中文
    public ArrayList<String> workIndustys;//工作行业 中文
    public double startYearlySalary;//开始年薪
    public double endYearlySalary;//结束年薪
    public int orderType;//排序类型1：薪资，2：时间

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ArrayList<String> getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(ArrayList<String> workLocation) {
        this.workLocation = workLocation;
    }

    public ArrayList<String> getWorkIndusty() {
        return workIndusty;
    }

    public void setWorkIndusty(ArrayList<String> workIndusty) {
        this.workIndusty = workIndusty;
    }

    public ArrayList<String> getWorkLocations() {
        return workLocations;
    }

    public void setWorkLocations(ArrayList<String> workLocations) {
        this.workLocations = workLocations;
    }

    public ArrayList<String> getWorkIndustys() {
        return workIndustys;
    }

    public void setWorkIndustys(ArrayList<String> workIndustys) {
        this.workIndustys = workIndustys;
    }

    public double getStartYearlySalary() {
        return startYearlySalary;
    }

    public void setStartYearlySalary(double startYearlySalary) {
        this.startYearlySalary = startYearlySalary;
    }

    public double getEndYearlySalary() {
        return endYearlySalary;
    }

    public void setEndYearlySalary(double endYearlySalary) {
        this.endYearlySalary = endYearlySalary;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "SuccessCaseWhole{" +
                "type='" + type + '\'' +
                ", keyWords='" + keyWords + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", workLocation=" + workLocation +
                ", workIndusty=" + workIndusty +
                ", workLocations=" + workLocations +
                ", workIndustys=" + workIndustys +
                ", startYearlySalary=" + startYearlySalary +
                ", endYearlySalary=" + endYearlySalary +
                ", orderType=" + orderType +
                '}';
    }
}
