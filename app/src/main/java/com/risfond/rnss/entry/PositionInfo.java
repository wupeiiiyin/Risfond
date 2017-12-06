package com.risfond.rnss.entry;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  @zhangchuan622@gmail.com
 * @version 1.0
 * @create  2017/12/5
 * @desc
 */
public class PositionInfo implements Parcelable {
    private String title;
    private String code;
    private boolean isCheck;
    private List<Data> datas = new ArrayList<>();
    public static class Data{
        private String content;
        private String code;
        private boolean isCheck;
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PositionInfo() {
    }

    @Override
    public String toString() {
        return "PositionInfo{" +
                "title='" + title + '\'' +
                ", datas=" + datas +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.code);
        dest.writeList(this.datas);
    }

    protected PositionInfo(Parcel in) {
        this.title = in.readString();
        this.code = in.readString();
        this.datas = new ArrayList<Data>();
        in.readList(this.datas, Data.class.getClassLoader());
    }

    public static final Creator<PositionInfo> CREATOR = new Creator<PositionInfo>() {
        @Override
        public PositionInfo createFromParcel(Parcel source) {
            return new PositionInfo(source);
        }

        @Override
        public PositionInfo[] newArray(int size) {
            return new PositionInfo[size];
        }
    };
}
