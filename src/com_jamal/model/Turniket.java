package com_jamal.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.LinkedList;

@DatabaseTable(tableName = "turniket")
public class Turniket {
    @DatabaseField(generatedId = true)
    private int turniketId;
    @ForeignCollectionField(eager = false)
    public ForeignCollection<Passenger> turniketPassengersColl;
    public LinkedList<Passenger> turniketPassengers;

    public Turniket() {
        turniketPassengers = new LinkedList<>();
    }

    public synchronized void addPassengers(int numberOfPassengers) {
        for (int i = 0; i < numberOfPassengers; i++) {
            turniketPassengers.add(new Passenger());
        }
        notifyAll();
    }

    public StringBuffer printPassengers() {
        StringBuffer sb = new StringBuffer();
        synchronized (turniketPassengers) {
            for (Passenger pass : turniketPassengers)
                sb.append("[" + pass + "] ");
        }
        return sb;
    }

    public ForeignCollection<Passenger> getTurniketPassengersColl() {
        return turniketPassengersColl;
    }

    public void setTurniketPassengersColl(ForeignCollection<Passenger> turniketPassengersColl) {
        this.turniketPassengersColl = turniketPassengersColl;
    }

    public LinkedList<Passenger> getTurniketPassengers() {
        return turniketPassengers;
    }

    public void setTurniketPassengers(LinkedList<Passenger> turniketPassengers) {
        this.turniketPassengers = turniketPassengers;
    }

    public int getTurniketId() {
        return turniketId;
    }

    public void setTurniketId(int turniketId) {
        this.turniketId = turniketId;
    }
}