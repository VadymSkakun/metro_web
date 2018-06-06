package com_jamal.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.LinkedList;

@DatabaseTable(tableName = "lines")
public class Line {
    @DatabaseField(generatedId = true)
    private Integer lineId;
    @DatabaseField(useGetSet = true)
    private String name;
    @ForeignCollectionField(eager = false)
    public ForeignCollection<Train> lineTrainsCollection;
    public LinkedList<Train> lineTrains;
    @ForeignCollectionField(eager = false)
    public ForeignCollection<Station> lineStationsCollection;
    public LinkedList<Station> lineStations;

    public Line() {
    }

    public Line(String name) {
        this.name = name;
        lineStations = new LinkedList<>();
        lineTrains = new LinkedList<>();
    }

    public void addTrain(Train train) {
        this.lineTrains.add(train);
        train.setTrainLine(this);
    }

    @Override
    public String toString() {
        return "\nLine [name=" + name + lineTrains + "]";
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ForeignCollection<Train> getLineTrainsCollection() {
        return lineTrainsCollection;
    }

    public void setLineTrainsCollection(ForeignCollection<Train> lineTrainsCollection) {
        this.lineTrainsCollection = lineTrainsCollection;
    }

    public LinkedList<Train> getLineTrains() {
        return lineTrains;
    }

    public void setLineTrains(LinkedList<Train> lineTrains) {
        this.lineTrains = lineTrains;
    }

    public ForeignCollection<Station> getLineStationsCollection() {
        return lineStationsCollection;
    }

    public void setLineStationsCollection(ForeignCollection<Station> lineStationsCollection) {
        this.lineStationsCollection = lineStationsCollection;
    }

    public LinkedList<Station> getLineStations() {
        return lineStations;
    }

    public void setLineStations(LinkedList<Station> lineStations) {
        this.lineStations = lineStations;
    }
}
