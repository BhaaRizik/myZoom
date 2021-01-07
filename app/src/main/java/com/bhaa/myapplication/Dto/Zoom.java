package com.bhaa.myapplication.Dto;

import java.util.Date;

public class Zoom {
    private String meetingTitle;
    private Date date;
    private String time;
    private String link;
    private boolean isWeekly;

    public Zoom(String meetingTitle, Date date, String time, String link, boolean isWeekly) {
        this.meetingTitle = meetingTitle;
        this.date = date;
        this.time = time;
        this.link = link;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isWeekly() {
        return isWeekly;
    }

    public void setWeekly(boolean weekly) {
        isWeekly = weekly;
    }

    @Override
    public String toString() {
        return "Zoom :" +
                "\nMeeting Title='" + meetingTitle + '\'' +
                "\nDate='" + date + '\'' +
                "\nTime='" + time + '\'' +
                "\nLink=\n" + link ;
    }
}