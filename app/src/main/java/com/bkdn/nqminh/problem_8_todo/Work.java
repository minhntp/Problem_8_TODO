package com.bkdn.nqminh.problem_8_todo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nqminh on 25/03/2018.
 */

public class Work {
    private String title;
    private String content;
    private Date date;
    private Date hour;

    public Work() {
    }

    public Work(String title, String content, Date date, Date hour) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.hour = hour;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    public static String getDateFormat(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(d);
    }

    public static String getHourFormat(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(d);
    }

    @Override
    public String toString() {
        return title + " - " + getDateFormat(date) + " - " + getHourFormat(hour);
    }
}
