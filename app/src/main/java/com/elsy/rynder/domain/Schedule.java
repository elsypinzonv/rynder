package com.elsy.rynder.domain;

import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("week_day")
    private String weekDay;

    @SerializedName("open_hour")
    private int openHour;

    @SerializedName("close_hour")
    private int closeHour;

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public int getOpenHour() {
        return openHour;
    }

    public void setOpenHour(int openHour) {
        this.openHour = openHour;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(int closeHour) {
        this.closeHour = closeHour;
    }
}
