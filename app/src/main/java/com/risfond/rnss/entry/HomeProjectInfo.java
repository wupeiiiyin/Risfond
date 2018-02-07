package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/4/20.
 */

public class HomeProjectInfo {
    private String title;
    private int imgId;
    private int itemId;
    private int number;

    public HomeProjectInfo(String title, int imgId, int itemId, int number) {
        this.title = title;
        this.imgId = imgId;
        this.itemId = itemId;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
