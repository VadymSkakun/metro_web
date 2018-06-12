package com_jamal.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.LinkedList;

/*Class for Train of metro*/

@DatabaseTable(tableName = "trains")
public class Train {

    @DatabaseField(generatedId = true)
    private Integer trainId;

    /*Max value of wagons in the train*/

    public static final byte MAX_NUMBER_OF_WAGONS = 5;

    /*Name of Train*/

    @DatabaseField(useGetSet = true)
    private String name;

    private String number;

    /*Line where train moves in*/

    private Line trainLine;

    /*Can a train go in a line?*/

    @DatabaseField(useGetSet = true)
    public boolean readyToGo;

    /*Trains driver*/

    @DatabaseField(columnName = "driverId", canBeNull = true, foreign = true, useGetSet = true)
    private Driver driver;

    public LinkedList<Wagon> wagonsList;
    @ForeignCollectionField(eager = false)
    public ForeignCollection<Wagon> wagons;
    @DatabaseField(columnName = "lineId", canBeNull = true, foreign = true, useGetSet = true)
    public Line line;

    public Train() {

    }

    public Train(String name, String number) {
        this.name = name;
        this.number = number;
        wagonsList = new LinkedList<>();
        readyToGo = false;
    }

    private boolean hasHeaderWagon() {
        if (wagonsList.size() >= 1)
            if (wagonsList.getFirst().isHeaderWagon())
                return true;
        return false;
    }

    /*Is the first wagon the main one?*/

    private boolean hasTrailerWagon() {
        if (wagonsList.size() >= 1)
            if (wagonsList.getLast().isHeaderWagon())
                return true;
        return false;
    }

    /*Adding wagon to a train.*/

    public void addWagon(Wagon wagon) {
        if (this.wagonsList.size() < MAX_NUMBER_OF_WAGONS) {
            if (wagon.isHeaderWagon() & !hasHeaderWagon()) {
                wagonsList.addFirst(wagon);
            } else {
                if (wagon.isHeaderWagon() & !hasTrailerWagon()) {
                    wagonsList.addLast(wagon);
                } else {
                    if (!hasHeaderWagon() || !hasTrailerWagon()) {
                        wagonsList.add(wagon);
                    } else {
                        wagonsList.add(1, wagon);
                    }
                }
            }
            wagon.setTrain(this);
            readyToGo = hasHeaderWagon() & hasTrailerWagon() & (this.wagonsList.size() == MAX_NUMBER_OF_WAGONS);
        } else
            System.out.println("Can't add more wagons!");
    }

    /*Count quantity of passengers in train.*/

    public int getPassengersCount() {
        int cnt = 0;
        for (Wagon wagon : this.wagonsList)
            cnt += wagon.getWagonPassengers().size();
        return cnt;
    }

    @Override
    public String toString() {
        return "\n Train [name=" + name + ", ready=" + readyToGo + ", num=" + number
                + ", driver=" + driver + "]";
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Line getTrainLine() {
        return trainLine;
    }

    public void setTrainLine(Line trainLine) {
        this.trainLine = trainLine;
    }

    public boolean isReadyToGo() {
        return readyToGo;
    }

    public void setReadyToGo(boolean readyToGo) {
        this.readyToGo = readyToGo;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public LinkedList<Wagon> getWagonsList() {
        return this.wagonsList;
    }

    public void setWagonsList(LinkedList<Wagon> wagonsList) {
        this.wagonsList = wagonsList;
    }

    public ForeignCollection<Wagon> getWagons() {
        return wagons;
    }

    public void setWagons(ForeignCollection<Wagon> wagons) {
        this.wagons = wagons;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
