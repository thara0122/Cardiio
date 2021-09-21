package com.example.cardiio;

public class CelsuisTemperature {

//    private double Celcius;
//    public CelsuisTemperature(){
//
//    }
//    public CelsuisTemperature(double Celcius){
//        this.Celcius = Celcius;
//    }
//    public double getCelcius(){
//        return Celcius;
//    }
//    public void setCelcius(double celcius){
//        Celcius = celcius;
//    }
private String Temperature;

    public CelsuisTemperature() {
    }

    public CelsuisTemperature(String Temperature) {
        this.Temperature = Temperature;
        //  this.Celcius = Celcius;
    }

    public String getTime_Stamp() {
        return Temperature;
    }

    public void setTime_Stamp(String temperature) {
        Temperature = temperature;
    }
}
