package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/9/15.
 */

public class News {
    /**
     * ID : 7107
     * Title : 锐仕方达2016春节全国放假公告
     * CreatedTime : 2016-02-04
     * Category : 企业公告
     * Content : 锐仕方达2016春节全国放假时间是：2016年02月04日-02月14号，放假10天。正月初七正式上班。
     */

    private String ID;
    private String Title;
    private String CreatedTime;
    private String Category;
    private String Content;
    private String Img;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String CreatedTime) {
        this.CreatedTime = CreatedTime;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }
}
