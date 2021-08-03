package com.example.daily.util;




import java.util.Calendar;

public class MyTime {

    String today;
    String time;
    int Week;

    public MyTime(){
        Calendar calendar = Calendar.getInstance();
        String mYear = String.valueOf(calendar.get(Calendar.YEAR));
        String mMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);        //获取日期的月
        String mDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));      //获取日期的天
        int h=calendar.get(Calendar.HOUR_OF_DAY); //24小时制    Calendar.HOUR是12小时制
        int mi=calendar.get(Calendar.MINUTE);
        this.time=h+"时"+mi+"分";
        this.Week=calendar.get(Calendar.DAY_OF_WEEK);
        this.today=mYear+"年"+mMonth+"月"+mDay+"日";

    }
    public String getToday() {
        return today;
    }
    public int getWeek(){
        return Week-1;
    }
    public String getTime() {
        return time;
    }

}
