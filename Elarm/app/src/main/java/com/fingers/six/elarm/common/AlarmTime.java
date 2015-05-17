package com.fingers.six.elarm.common;

/**
 * Created by PhanVanTrung on 2015/05/16.
 */
public class AlarmTime {
    private String  time;
    private String  time_status;
    private int id;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTime_status(String time_status) {
        this.time_status = time_status;
    }

    public String getTime_status() {
        return time_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
