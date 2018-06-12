package com_jamal.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/*Class for drivers*/

@DatabaseTable(tableName = "drivers")
public class Driver {
    @DatabaseField(generatedId = true)
    private Integer driverId;

    /*Drivers name*/

    @DatabaseField(useGetSet = true)
    private String name;

    /*Drivers current experience*/

    @DatabaseField(useGetSet = true)
    private int experience;

    /*Current train*/

    private Train currentTrain;

    @ForeignCollectionField(eager = false)
    public ForeignCollection<Train> trains;

    public Driver() {
    }

    public Driver(String name, int experience) {
        this.name = name;
        this.experience = experience;
    }

    /*The driving process and experience recalculating.
    Experience can be downgraded for one-threaded application purposes.*/

    public void doDrive() {
        Random rnd = new Random();
        System.out.println("Driver " + this.name + " drives " + this.currentTrain.getName());
        this.experience += rnd.nextInt(80);
    }

    @Override
    public String toString() {
        return "Driver [name=" + name + ", experience=" + experience + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Train getCurrentTrain() {
        return currentTrain;
    }

    public void setCurrentTrain(Train currentTrain) {
        this.currentTrain = currentTrain;
        currentTrain.setDriver(this);
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    /*The driving process and experience recalculating in Thread.*/

    public Thread doThreadDrive(BlockingQueue<Driver> driverQueue) {
        Thread theThread = new Thread(new Runnable() {
            public void run() {
                Random rnd = new Random();
                System.out.println("Driver " + Driver.this.name + "[" + Driver.this.experience + "] runs on the "
                        + Driver.this.currentTrain.getName() + " train");

                Driver.this.doDrive();
                try {
                    Thread.sleep(rnd.nextInt(5) * 1000);
                    driverQueue.put(Driver.this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        theThread.start();
        return theThread;
    }
}