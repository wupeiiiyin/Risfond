package com.risfond.rnss.entry;

/**
 * 业绩完成率
 * Created by Abbott on 2018/2/5.
 */

public class AchievementResponse {


    /**
     * Code : 0
     * Status : true
     * Message : 成功！
     * Data : {"StaffId":9064,"StaffName":"袁青青","PerformanceDabiaoAmount":"5.13","PerformanceAmount":"0.00","Percent":"0.00%","UnfinishedPerformance":"5.13","QuarterStr":"Q4"}
     */

    private int Code;
    private boolean Status;
    private String Message;
    private DataBean Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
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
         * StaffId : 9064
         * StaffName : 袁青青
         * PerformanceDabiaoAmount : 5.13
         * PerformanceAmount : 0.00
         * Percent : 0.00%
         * UnfinishedPerformance : 5.13
         * QuarterStr : Q4
         */

        private int StaffId;
        private String StaffName;
        private String PerformanceDabiaoAmount;
        private String PerformanceAmount;
        private String Percent;
        private String UnfinishedPerformance;
        private String QuarterStr;

        public int getStaffId() {
            return StaffId;
        }

        public void setStaffId(int StaffId) {
            this.StaffId = StaffId;
        }

        public String getStaffName() {
            return StaffName;
        }

        public void setStaffName(String StaffName) {
            this.StaffName = StaffName;
        }

        public String getPerformanceDabiaoAmount() {
            return PerformanceDabiaoAmount;
        }

        public void setPerformanceDabiaoAmount(String PerformanceDabiaoAmount) {
            this.PerformanceDabiaoAmount = PerformanceDabiaoAmount;
        }

        public String getPerformanceAmount() {
            return PerformanceAmount;
        }

        public void setPerformanceAmount(String PerformanceAmount) {
            this.PerformanceAmount = PerformanceAmount;
        }

        public String getPercent() {
            return Percent;
        }

        public void setPercent(String Percent) {
            this.Percent = Percent;
        }

        public String getUnfinishedPerformance() {
            return UnfinishedPerformance;
        }

        public void setUnfinishedPerformance(String UnfinishedPerformance) {
            this.UnfinishedPerformance = UnfinishedPerformance;
        }

        public String getQuarterStr() {
            return QuarterStr;
        }

        public void setQuarterStr(String QuarterStr) {
            this.QuarterStr = QuarterStr;
        }
    }
}
