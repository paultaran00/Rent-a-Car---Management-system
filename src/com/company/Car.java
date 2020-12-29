package com.company;

import java.io.Serializable;

public class Car implements Comparable<Car>, Serializable {
    int vehicleIdentificationNumber;
    String brend;
    String model;
    double motor;
    int horsePower;
    String description;

    public Car(int vin, String brend, String model, double motor, int horsePower, String description) {
        this.vehicleIdentificationNumber = vin;
        this.brend = brend;
        this.model = model;
        this.motor = motor;
        this.horsePower = horsePower;
        this.description = description;
    }

    @Override
    public String toString() {
        return "VIN: "+vehicleIdentificationNumber + ", Brend: " + brend + ", Model: " + model + ", Motor: " + motor + ", Horse Power: " + horsePower;
    }

    public int getVehicleIdentificationNumber() {
        return vehicleIdentificationNumber;
    }

    public String getBrend() {
        return brend;
    }

    public String getModel() {
        return model;
    }

    public double getMotor() {
        return motor;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public String getDescription() {
        return description;
    }

    public void setVehicleIdentificationNumber(int vehicleIdentificationNumber) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
    }

    public void setBrend(String brend) {
        this.brend = brend;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMotor(double motor) {
        this.motor = motor;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Car o) {
        return Integer.compare(horsePower, o.horsePower);
    }
}
