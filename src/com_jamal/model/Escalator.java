package com_jamal.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/*Escalator that transfers Passengers from lobby to Station*/

@DatabaseTable(tableName = "escalators")
public class Escalator implements Runnable {
    @DatabaseField(generatedId = true)
    private Integer escalatorId;

    private Station station;

    /*Escalator name*/

    @DatabaseField(useGetSet = true)
    private String name;

    public Escalator() {
    }

    public Escalator(String name, Station station) {
        this.name = name;
        this.station = station;
    }

    @Override
    public String toString() {
        return "Escalator [name=" + name + "]";
    }

    @Override
    public void run() {
        Random random = new Random();

        /*Constant for quantity of Passengers moved via Escalator*/

        final int escalatorMoved = 3;

        /*Constant value time for threads sleep*/

        final int escalatorSleep = 1000;

        while (true) {
            try {
                Thread.sleep(escalatorSleep);
                System.out.println(" " + Escalator.this.name + " перевёз [" + Escalator.this.movePassengers(
                        random.nextInt(escalatorMoved) + 1
                        , Escalator.this.station.getTurniket().turniketPassengers
                        , Escalator.this.station.getWaitingPassengers()) + "]");
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    /*Move number of passengers from LinkedList to LinkedBlockingQueue.*/

    private int movePassengers(int numberOfPassengers, LinkedList<Passenger> listFrom,
                               LinkedBlockingQueue<Passenger> listTo) {
        int counter = 0;

        for (int i = 0; i < numberOfPassengers; i++) {
            /*Locking source List*/
            synchronized (listFrom) {
                if (!listFrom.isEmpty()) {
                    Passenger pass = listFrom.remove();
                    System.out.print(this.name + "=>[" + pass + "] ");
                    counter++;
                    listTo.add(pass);
                }
            }
        }
        if (counter > 0)
            System.out.println();
        return counter;
    }

    public Integer getEscalatorId() {
        return escalatorId;
    }

    public void setEscalatorId(Integer escalatorId) {
        this.escalatorId = escalatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}