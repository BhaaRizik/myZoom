package com.bhaa.myapplication.Dto;

public class Zoom {
    private String organizer;
    private String date;
    private String time;
    private String link;

    public Zoom(String organizer, String date, String time, String link) {
        this.organizer = organizer;
        this.date = date;
        this.time = time;
        this.link = link;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
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