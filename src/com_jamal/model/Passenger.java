package com_jamal.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "passengers")
public class Passenger {
    private static int ID_COUNTER = 0;
    @DatabaseField(generatedId = true)
    private Integer passengerId;
    @DatabaseField(useGetSet = true)
    private String name;
    @DatabaseField(columnName = "wagonId", canBeNull = false, foreign = true, useGetSet = true)
    public Wagon wagon;
    @DatabaseField(columnName = "stationId", canBeNull = false, foreign = true, useGetSet = true)
    public Station station;
    @DatabaseField(columnName = "turniketId", canBeNull = false, foreign = true, useGetSet = true)
    public Turniket turniket;

    public Passenger() {
        this.passengerId = ID_COUNTER++;
    }

    public Passenger(String name) {
        this.name = name;
        this.passengerId = ID_COUNTER++;
    }

    @Override
    public String toString() {
        return "P" + passengerId;
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWagon(Wagon wagon) {
        this.wagon = wagon;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setTurniket(Turniket turniket) {
        this.turniket = turniket;
    }

    public Wagon getWagon() {
        return wagon;
    }

    public Station getStation() {
        return station;
    }

    public Turniket getTurniket() {
        return turniket;
    }
}