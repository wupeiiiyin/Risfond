package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/7/7.
 */

public class MyAttendance {
    public MyAttendance(int staffId, String staffName, String reason, String leaveStatus, String leaveNum, String timeFormat, String dayFormat, String leaveType) {
        StaffId = staffId;
        StaffName = staffName;
        Reason = reason;
        LeaveStatus = leaveStatus;
        LeaveNum = leaveNum;
        TimeFormat = timeFormat;
        DayFormat = dayFormat;
        LeaveType = leaveType;
    }

    /**
     * 考勤列表
     * int StaffId { get; set; }
     * string StaffName { get; set; }//员工姓名
     * string DateFormat { get; set; }//日期格式
     * string WeekFormat { get; set; }//周格式
     * string ShangBanTime { get; set; }//上班时间
     * string XiaBanTime { get; set; }//下班时间}
     */

    /*
    请假列表
    *int StaffId { get; set; }
    string StaffName { get; set; }
    string LeaveType { get; set; }//请假类型
    string DayFormat { get; set; }//日期格式
    string TimeFormat { get; set; }//时间格式
    string LeaveNum { get; set; }//数量
    string LeaveStatus { get; set; }//请假状态
    string Reason { get; set; }//原因}
    * */


   /*
   外出列表
   *    int StaffId { get; set; }
    string StaffName { get; set; }
    string GoOutType { get; set; }//外出类型
    string DateFormat { get; set; }//日期格式
    string WeekFormat { get; set; }//周格式
    string StartMonth { get; set; }//外出日期格式
    string EndMonth { get; set; }//返回日期格式
    string StartTime { get; set; }//外出时间格式
    string EndTime { get; set; }//返回时间格式
    string ClientName { get; set; }//客户名
    string Address { get; set; }//地址
    string[] Members { get; set; }//成员
    string Contact { get; set; }//联系人
    string ContactNumber { get; set; }//电话
    string Reason { get; set; }//内容
   * */



    private int StaffId;
    private String StaffName;
    private String DateFormat;
    private String WeekFormat;
    private String ShangBanTime;
    private String XiaBanTime;

    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(int staffId) {
        StaffId = staffId;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public String getDateFormat() {
        return DateFormat;
    }

    public void setDateFormat(String dateFormat) {
        DateFormat = dateFormat;
    }

    public String getWeekFormat() {
        return WeekFormat;
    }

    public void setWeekFormat(String weekFormat) {
        WeekFormat = weekFormat;
    }

    public String getShangBanTime() {
        return ShangBanTime;
    }

    public void setShangBanTime(String shangBanTime) {
        ShangBanTime = shangBanTime;
    }

    public String getXiaBanTime() {
        return XiaBanTime;
    }

    public void setXiaBanTime(String xiaBanTime) {
        XiaBanTime = xiaBanTime;
    }


    private String LeaveType;
    private String DayFormat;
    private String TimeFormat;
    private String LeaveNum;
    private String LeaveStatus;
    private String Reason;

    public String getLeaveType() {
        return LeaveType;
    }

    public void setLeaveType(String leaveType) {
        LeaveType = leaveType;
    }

    public String getDayFormat() {
        return DayFormat;
    }

    public void setDayFormat(String dayFormat) {
        DayFormat = dayFormat;
    }

    public String getTimeFormat() {
        return TimeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        TimeFormat = timeFormat;
    }

    public String getLeaveNum() {
        return LeaveNum;
    }

    public void setLeaveNum(String leaveNum) {
        LeaveNum = leaveNum;
    }

    public String getLeaveStatus() {
        return LeaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        LeaveStatus = leaveStatus;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }



    private String GoOutType;
    private String StartMonth;
    private String EndMonth;
    private String StartTime;
    private String EndTime;
    private String ClientName;
    private String Address;
    private String[] Members;
    private String Contact;
    private String ContactNumber;

    public String getGoOutType() {
        return GoOutType;
    }

    public void setGoOutType(String goOutType) {
        GoOutType = goOutType;
    }

    public String getStartMonth() {
        return StartMonth;
    }

    public void setStartMonth(String startMonth) {
        StartMonth = startMonth;
    }

    public String getEndMonth() {
        return EndMonth;
    }

    public void setEndMonth(String endMonth) {
        EndMonth = endMonth;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String[] getMembers() {
        return Members;
    }

    public void setMembers(String[] members) {
        Members = members;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }


    /*
    * StaffId
    Name
    Status  如果为true  这个人默认给选择上

    * */

    private String Id;
    private String Name;
    private Boolean Status;
    private String StaffPhoto;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public String getStaffPhoto() {
        return StaffPhoto;
    }

    public void setStaffPhoto(String staffPhoto) {
        StaffPhoto = staffPhoto;
    }
}


