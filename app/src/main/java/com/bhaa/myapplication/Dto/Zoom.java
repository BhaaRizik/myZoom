package com.bhaa.myapplication.Dto;

public class Zoom {
    private String meetingTitle;
    private String date;
    private String time;
    private String link;

    public Zoom(String meetingTitle, String date, String time, String link) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
}