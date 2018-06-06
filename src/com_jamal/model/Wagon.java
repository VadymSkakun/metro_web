package com_jamal.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.LinkedList;

@DatabaseTable(tableName = "wagons")
public class Wagon {
    public static final int MAX_WAGON_CAPACITY = 10;
    @DatabaseField(generatedId = true)
    private int wagonId;
    @DatabaseField(useGetSet = true, columnName = "isHeader")
    private boolean isHeaderWagon;
    @DatabaseField(useGetSet = true)
    private String name;
    LinkedList<Passenger> wagonPassengers;
    @ForeignCollectionField(eager = false)
    public ForeignCollection<Passenger> passengers;
    @DatabaseField(columnName = "trainId", canBeNull = true, foreign = true, useGetSet = true)
    public Train train;

    public Wagon() {
    }

    public Wagon(String name, boolean isHeaderWagon) {
        this.name = name;
        this.isHeaderWagon = isHeaderWagon;
        this.wagonPassengers=new LinkedList<>();
    }

    @Override
    public String toString() {
        return "Wagon [head=" + isHeaderWagon + ", name=" + name + "]";
    }

    public void addPassenger(Passenger passenger) {
        this.wagonPassengers.add(passenger);
    }

    public boolean isHeaderWagon() {
        return isHeaderWagon;
    }

    public boolean getIsHeaderWagon() {
        return isHeaderWagon;
    }

    public void setIsHeaderWagon(boolean isHeaderWagon) {
        this.isHeaderWagon = isHeaderWagon;
    }

    public void setHeaderWagon(boolean isHeaderWagon) {
        this.isHeaderWagon = isHeaderWagon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWagonId() {
        return wagonId;
    }

    public void setWagonId(int wagonId) {
        this.wagonId = wagonId;
    }

    public ForeignCollection<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ForeignCollection<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public LinkedList<Passenger> getWagonPassengers() {
        return  this.wagonPassengers;
    }

    public void setWagonPassengers(LinkedList<Passenger> wagonPassengers) {
        this.wagonPassengers = wagonPassengers;
    }
}
