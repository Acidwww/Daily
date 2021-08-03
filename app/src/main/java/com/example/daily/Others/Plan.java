package com.example.daily.Others;

public class Plan {
    private long id;
    private int tag;
    private String content;
    private String time;
    private int Monday;
    private int Tuesday;
    private int Wednesday;
    private int Thursday;
    private int Friday;
    private int Saturday;
    private int Sunday;
    private String State;
    private int week;
    public long getId() {
        return id;
    }

    public Plan() {

    }
    public Plan(int tag,String content,String time,int Monday,int Tuesday,int Wednesday,int Thursday,int Friday,int Saturday,int Sunday,String State,int week) {
        this.tag=tag;
        this.content=content;
        this.time=time;
        this.Monday=Monday;
        this.Tuesday=Tuesday;
        this.Wednesday=Wednesday;
        this.Thursday=Thursday;
        this.Friday=Friday;
        this.Saturday=Saturday;
        this.Sunday=Sunday;
        this.State=State;
        this.week=week;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMonday() {
        return Monday;
    }

    public void setMonday(int monday) {
        Monday = monday;
    }

    public int getTuesday() {
        return Tuesday;
    }

    public void setTuesday(int tuesday) {
        Tuesday = tuesday;
    }

    public int getWednesday() {
        return Wednesday;
    }

    public void setWednesday(int wednesday) {
        Wednesday = wednesday;
    }

    public int getThursday() {
        return Thursday;
    }

    public void setThursday(int thursday) {
        Thursday = thursday;
    }

    public int getFriday() {
        return Friday;
    }

    public void setFriday(int friday) {
        Friday = friday;
    }

    public int getSaturday() {
        return Saturday;
    }

    public void setSaturday(int saturday) {
        Saturday = saturday;
    }

    public int getSunday() {
        return Sunday;
    }

    public void setSunday(int sunday) {
        Sunday = sunday;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }


}
