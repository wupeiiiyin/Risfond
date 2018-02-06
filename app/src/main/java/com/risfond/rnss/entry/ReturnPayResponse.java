package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2018/2/5.
 */

public class ReturnPayResponse {


    /**
     * Code : 0
     * Total : 0
     * Status : true
     * Message : 成功！
     * Data : {"HuikuanCurrent":"0.00","RuzhiCurrent":0,"QianyueCurrent":0}
     */

    private int Code;
    private int Total;
    private boolean Status;
    private String Message;
    private DataBean Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * HuikuanCurrent : 0.00
         * RuzhiCurrent : 0
         * QianyueCurrent : 0
         */

        private String HuikuanCurrent;
        private int RuzhiCurrent;
        private int QianyueCurrent;

        public String getHuikuanCurrent() {
            return HuikuanCurrent;
        }

        public void setHuikuanCurrent(String HuikuanCurrent) {
            this.HuikuanCurrent = HuikuanCurrent;
        }

        public int getRuzhiCurrent() {
            return RuzhiCurrent;
        }

        public void setRuzhiCurrent(int RuzhiCurrent) {
            this.RuzhiCurrent = RuzhiCurrent;
        }

        public int getQianyueCurrent() {
            return QianyueCurrent;
        }

        public void setQianyueCurrent(int QianyueCurrent) {
            this.QianyueCurrent = QianyueCurrent;
        }
    }
}
