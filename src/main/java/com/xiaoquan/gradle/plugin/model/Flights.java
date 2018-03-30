// Adapted from the spring oxm test - Thanks!

package com.xiaoquan.gradle.plugin.model;

import java.util.ArrayList;

public class Flights {

    private ArrayList<FlightType> flightList = new ArrayList<>();

    public void addFlight(FlightType flight) {
        flightList.add(flight);
    }

    public FlightType getFlight(int index) {
        return (FlightType) flightList.get(index);
    }

    public int sizeFlightList() {
        return flightList.size();
    }

    public ArrayList<FlightType> getFlightList() {
        return flightList;
    }

    public void setFlightList(ArrayList<FlightType> flightList) {
        this.flightList = flightList;
    }
}
