package com_jamal.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

@DatabaseTable(tableName = "stations")
public class Station {
    public static int MAX_NUMBER_OF_PASSENGERS = 500;
    @DatabaseField(generatedId = true)
    private Integer stationId;
    @DatabaseField(useGetSet = true)
    private String name;
    @ForeignCollectionField(eager = false)
    public ForeignCollection<Passenger> waitingPassengersColl;
    private LinkedBlockingQueue<Passenger> waitingPassengers;
    @DatabaseField(columnName = "lineId", canBeNull = false, foreign = true, useGetSet = true)
    public Line line;
    private Turniket turniket;

    public Station() {
    }

    public Station(String name) {
        this.name = name;
        waitingPassengers = new LinkedBlockingQueue<>();
    }

    @Override
    public String toString() {
        return "Station [" + name + "; waiting passengers (" + waitingPassengers.size() + ")]";
    }

    public void addPassengerToPlatform(Passenger passenger) {
        this.waitingPassengers.add(passenger);
    }

    public Passenger poll() {
        return (this.waitingPassengers.iterator().hasNext()) ? this.waitingPassengers.iterator().next() : null;
    }

    public LinkedList<Passenger> getWaitingPassengersAsList() {
        LinkedList<Passenger> pass = new LinkedList<>();
        pass.addAll(this.waitingPassengersColl);
        return pass;
    }

    public ForeignCollection<Passenger> getWaitingPassengersColl() {
        return waitingPassengersColl;
    }

    public void setWaitingPassengersColl(ForeignCollection<Passenger> waitingPassengersColl) {
        this.waitingPassengersColl = waitingPassengersColl;
    }

    public void setWaitingPassengers(LinkedBlockingQueue<Passenger> waitingPassengers) {
        this.waitingPassengers = waitingPassengers;
    }

    public LinkedBlockingQueue<Passenger> getWaitingPassengers() {
        return waitingPassengers;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Turniket getTurniket() {
        return turniket;
    }

    public void setTurniket(Turniket turniket) {
        this.turniket = turniket;
    }
}