package com.example.matei.behealthy.utility;

import java.io.Serializable;
import java.sql.Time;

public class Task implements Serializable {
    public String name;
    public int serii;
    public int repetari;
    public DaysOfTheWeek day;
    public Time time;

    public Task(String name, int serii, int repetari, DaysOfTheWeek day, Time time){
        this.name = name;
        this.time = time;
        this.serii = serii;
        this.repetari = repetari;
        this.day = day;
    }
}
