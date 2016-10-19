package edu.scu.lwang.finalprojectscene;

import java.util.Date;

/**
 * Created by Sarah on 5/23/2016.
 */

public class Plant {
    protected int id;
    protected String plantName = "";
    protected String photoPath = "";
    protected int waterInterval;
    protected Date lastWater;
    protected String date = "";
    protected Date nextWater;

    public Plant() {

    }

    public Plant(int id, String plantName, String photoPath, String date, int waterInterval, Date lastWater) {
        this.id = id;
        this.plantName = plantName;
        this.photoPath = photoPath;
        this.waterInterval = waterInterval;
        this.lastWater = lastWater;
        this.date = date;


//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//
//        cal.add(Calendar.HOUR_OF_DAY, waterInterval * 24);
        nextWater = new Date();
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getWaterInterval() {
        return waterInterval;
    }

    public void setWaterInterval(int waterInterval) {
        this.waterInterval = waterInterval;
    }

    public Date getLastWater() {
        return lastWater;
    }

    public void setLastWater(Date lastWater) {
        this.lastWater = lastWater;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
