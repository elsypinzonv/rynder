package com.elsy.rynder.domain;

import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("week_day")
    private String weekDay;

    @SerializedName("open_hour")
    private String openHour;

    @SerializedName("close_hour")
    private String closeHour;

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getOpenHour() {
        return openHour;
    }

    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    public String getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(String closeHour) {
        this.closeHour = closeHour;
    }
}
