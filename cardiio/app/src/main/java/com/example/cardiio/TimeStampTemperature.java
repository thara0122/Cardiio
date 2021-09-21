package com.example.cardiio;

public class TimeStampTemperature {
   // private double Celcius;
    private String Time_Stamp;

    public TimeStampTemperature() {
    }

    public TimeStampTemperature(String Time_Stamp) {
        this.Time_Stamp = Time_Stamp;
      //  this.Celcius = Celcius;
    }

//    public double getCelcius() {
//        return Celcius;
//    }
//
//    public void setCelcius(double celcius) {
//        Celcius = celcius;
//    }

    public String getTime_Stamp() {
        return Time_Stamp;
    }

    public void setTime_Stamp(String time_Stamp) {
        Time_Stamp = time_Stamp;
    }

}
