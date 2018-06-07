package com_jamal.core;

import com_jamal.model.*;

import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class MetroWeb {
    final static int WAGONS_IN_DEPOT = 100;

    LinkedList<Wagon> depot;
    HashSet<Train> trains;
    HashMap<String, Line> allLines;
    Line redLine, blueLine, greenLine;

    static Map<String, LinkedBlockingQueue<Passenger>> passengersInStation = new HashMap<>();
    static Random rnd;

    static Comparator<Driver> comparator;
    static BlockingQueue<Driver> driverQueue;

    PrintWriter printWriter;

    public MetroWeb(PrintWriter pw) {
        this.printWriter = pw;
        rnd = new Random();
        this.depot = new LinkedList<>();
        this.trains = new LinkedHashSet<>();
        this.allLines = new HashMap<>();

        createWagons();
        createTrains();
        createLines();
        createLineTrains();
        manageDriversQueue();
        createLineStations();
        passengersInOutTrains();
    }

    /**
     * Creates trains from depot wagons using train.
     * Adds ready train to trains collection.
     */
    public void createTrains() {
        //Filling of trains with wagons.
        for (int j = 0; j < depot.size() / 5; j++) {
            Train train = new Train("Train " + j, "#0" + j);
            while (!depot.isEmpty()) {
                train.addWagon(depot.poll());
                if (train.getWagonsList().size() == Train.MAX_NUMBER_OF_WAGONS)
                    break;
            }
            if (train.readyToGo)
                trains.add(train);
        }
    }

    /**
     * Creates wagons for depot collection. Approximate 30% of header wagons will be created.
     */
    public void createWagons() {
        for (int i = 0; i < WAGONS_IN_DEPOT; i++) {
            Wagon wgn = new Wagon("Wagon_" + i, (rnd.nextInt(100) < 30));
            depot.add(wgn);
        }
    }

    /**
     * Prints information about wagons.
     */
    public void printWagons() {
        LinkedList<String> header = new LinkedList<>();
        LinkedList<LinkedList<String>> rows = new LinkedList<>();

        header.add("Info about wagon:");
        for (Wagon wgn : depot) {
            LinkedList<String> row = new LinkedList<>();
            row.add(wgn.getName());
            rows.add(row);
        }
        printTable("Wagons ", header, rows);
    }

    /**
     * Prints information about trains.
     */
    public void printTrains() {
        LinkedList<String> header = new LinkedList<>();
        LinkedList<LinkedList<String>> rows = new LinkedList<>();

        header.add("Name of train");
        header.add("Driver");

        for (int i = 0; i < Train.MAX_NUMBER_OF_WAGONS; i++)
            header.add("Wagon #" + (i + 1));

        for (Train trn : trains) {
            LinkedList<String> row = new LinkedList<>();
            row.add(trn.getName());
            row.add((trn.getDriver() == null) ? "" : trn.getDriver().getName());
            for (Wagon wgn : trn.wagonsList) {
                row.add(wgn.getName());
            }
            rows.add(row);
        }
        printTable("Trains", header, rows);
        printTable("Trains" + header.size() + ":" + rows.size() + ":" + rows.getFirst().size(), header, rows);
    }

    /**
     * Adds trains from trains collection to line trains.
     */
    private void createLineTrains() {
        redLine.setLineTrains(new LinkedList<>());
        blueLine.setLineTrains(new LinkedList<>());
        greenLine.setLineTrains(new LinkedList<>());

        Iterator<Train> iter = trains.iterator();

        while (iter.hasNext()) {
            redLine.lineTrains.add(iter.next());
            if (iter.hasNext())
                blueLine.lineTrains.add(iter.next());
            if (iter.hasNext())
                greenLine.lineTrains.add(iter.next());
        }
    }

    /**
     * Creates three lines. Adds them to allLines collection.
     */
    private void createLines() {
        redLine = new Line("Red");
        blueLine = new Line("Blue");
        greenLine = new Line("Green");

        allLines.put("Red", redLine);
        allLines.put("Blue", blueLine);
        allLines.put("Green", greenLine);
    }

    /**
     * Prints queue in right order. Using a copy of queue to poll.
     */
    public void printPriorityQueue(Queue<?> queue) {
        Queue<Driver> temp;

        if (queue instanceof PriorityBlockingQueue) {
            temp = new PriorityBlockingQueue<>(10, comparator);
        } else {
            temp = new PriorityQueue<>(10, comparator);
        }
        for (Object e : queue) {
            temp.add((Driver) e);
        }

        System.out.println("---------------Result Queue---------------------");
        while (!temp.isEmpty())
            System.out.println(temp.poll());
    }

    /**
     * Creates drivers and froms a driver queue from them.
     * Comparator of queue is based on drivers experience.
     */
    private void manageDriversQueue() {
        comparator = (o1, o2) -> {

            if (o1.getExperience() > o2.getExperience()) {
                return -1;
            }
            if (o1.getExperience() < o2.getExperience()) {
                return 1;
            }
            return 0;
        };

        driverQueue = new PriorityBlockingQueue<>(10, comparator);

        driverQueue.add(new Driver("Ushat Nadoev", 10));
        driverQueue.add(new Driver("Garem Playboev", 30));
        driverQueue.add(new Driver("Ramzanka Dirov", 50));
        driverQueue.add(new Driver("Ulov Nalimov", 5));
        driverQueue.add(new Driver("Buket Levkoev", 40));
        driverQueue.add(new Driver("Podriv Ustoev", 80));

        Driver drv = null;

        for (Train trn : trains) {
            while (!driverQueue.isEmpty()) {
                drv = driverQueue.poll();
                drv.setCurrentTrain(trn);
                break;
            }
        }
    }

    /**
     * Add stations to the lines(line station collection).
     */
    private void createLineStations() {
        for (int i = 0; i < 10; i++) {
            redLine.lineStations.add(new Station(redLine.getName() + "Station " + i));
            blueLine.lineStations.add(new Station(blueLine.getName() + "Station " + i));
            greenLine.lineStations.add(new Station(greenLine.getName() + "Station " + i));
        }
    }

    /**
     * Runs train through line. Train stays at every station and let passengers go out and in.
     * Train goes in separate thread.
     */
    private void runTrainThread(Line line, Train train) {
        Random rnd = new Random();
        int cntToOperate = 0;

        for (Station station : line.lineStations) {
            cntToOperate = rnd.nextInt(45);
            for (Wagon wgn : train.getWagonsList()) {
                if (!wgn.getWagonPassengers().isEmpty()) {
                    Iterator<Passenger> passIter = wgn.getWagonPassengers().iterator();
                    cntToOperate = rnd.nextInt(10);
                    while (passIter.hasNext() && cntToOperate > 0) {
                        passIter.next();
                        passIter.remove();
                        cntToOperate--;
                    }
                }
                while (wgn.getWagonPassengers().size() < Wagon.MAX_WAGON_CAPACITY
                        & station.getWaitingPassengers().size() > 0) {
                    try {
                        wgn.getWagonPassengers().add(station.getWaitingPassengers().take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Add passengers to stations in thread.
     */
    private void passengersEnterStations() {
        Random rnd = new Random();

        for (Line line : allLines.values()) {
            for (Station station : line.lineStations) {
                for (int i = 0; i < rnd.nextInt(2500); i++) {
                    try {
                        station.getWaitingPassengers().put(new Passenger(
                                "Passenger " + rnd.nextInt(1000)));

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Combine creating passengers and running all trains through stations.
     */
    private void passengersInOutTrains() {
        passengersEnterStations();
        for (Line line : allLines.values()) {
            Iterator<Train> iterLine = line.getLineTrains().iterator();
            while (iterLine.hasNext()) {
                Train train = iterLine.next();
                runTrainThread(line, train);
            }
        }
    }

    /**
     * Adds remaining passengers into HashMap for quick find and print passengers table.
     */
    public void showPasssengersLeft() {
        LinkedList<String> header = new LinkedList<>();
        LinkedList<LinkedList<String>> rows = new LinkedList<>();

        for (Station station : redLine.lineStations) {
            passengersInStation.put(station.getName(), station.getWaitingPassengers());
        }
        for (Station station : blueLine.lineStations) {
            passengersInStation.put(station.getName(), station.getWaitingPassengers());
        }
        for (Station station : greenLine.lineStations) {
            passengersInStation.put(station.getName(), station.getWaitingPassengers());
        }

        header.add("Passenger");
        for (Passenger pass : passengersInStation.get("RedStation 9")) {
            LinkedList<String> row = new LinkedList<>();
            row.add(pass.getName());
            rows.add(row);
        }

        printTable("Passengers at end station of Red line:", header, rows);

        for (Passenger pass : passengersInStation.get("BlueStation 9")) {
            LinkedList<String> row = new LinkedList<>();
            row.add(pass.getName());
            rows.add(row);
        }
        printTable("Passengers at end station of Blue line:", header, rows);

        for (Passenger pass : passengersInStation.get("GreenStation 9")) {
            LinkedList<String> row = new LinkedList<>();
            row.add(pass.getName());
            rows.add(row);
        }
        printTable("Passengers at end station of Green line:", header, rows);

    }

    /**
     * Prints information about station of line with name of lineName.
     */
    public void printLine(String lineName) {
        Line line = allLines.get(lineName);
        LinkedList<String> header = new LinkedList<>();
        LinkedList<LinkedList<String>> rows = new LinkedList<>();

        if (line != null) {
            header.add("Stations");
            for (Station station : line.lineStations) {
                LinkedList<String> row = new LinkedList<>();
                row.add(station.getName());
                rows.add(row);
            }
            printTable("Line info " + line.getName(), header, rows);
        }
    }

    /**
     * Prints a table.
     */
    private void printTable(String name, LinkedList<String> header, LinkedList<LinkedList<String>> rows) {
        printWriter.println("<B>" + name + "</B>");
        printWriter.println("<table border=1>");
        printWriter.println("<tr>");

        for (String headerValue : header) {
            printWriter.println("<th>");
            printWriter.println(headerValue);
            printWriter.println("</th>");
        }

        printWriter.println("</tr>");

        for (LinkedList<String> rowValues : rows) {
            printWriter.println("<tr>");
            for (String columnValue : rowValues) {
                printWriter.println("<td>");
                printWriter.println(columnValue);
                printWriter.println("</td>");
            }
            printWriter.println("</tr>");
        }
        printWriter.println("</table>");
    }
}