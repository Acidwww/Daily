package com.example.daily.Others;

public class Today {
    private long id;
    private String today;

    public Today() {

    }

    public Today(String today) {
        this.today=today;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
