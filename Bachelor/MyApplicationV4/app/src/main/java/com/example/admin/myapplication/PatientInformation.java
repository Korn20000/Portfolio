package com.example.admin.myapplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KoRn on 27-11-2017.
 */

public class PatientInformation {


    private double measuredLevel;
    private Date date;

        public double getMeasuredLevel() {
            return measuredLevel;
        }

    public void setMeasuredLevel(double measuredLevel) {
        this.measuredLevel = measuredLevel;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }




}