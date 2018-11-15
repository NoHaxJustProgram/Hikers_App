package com.sictc.cspm.hikers_app;

public class ApplicantData
{
    private int caloriesBurned;
    private int caloriesIntake;

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public int getCaloriesIntake() {
        return caloriesIntake;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public void setCaloriesIntake(int caloriesIntake)
    {
        this.caloriesIntake = caloriesIntake;
    }

    private int milesWalked;
    private int milesToGo;

    public int getMilesWalked()
    {
        return milesWalked;
    }

    public int getMilesToGo()
    {
        return milesToGo;
    }

    public void setMilesWalked(int milesWalked) {
        this.milesWalked = milesWalked;
    }

    public void setMilesToGo(int milesToGo) {
        this.milesToGo = milesToGo;
    }
}
