package com.example.demo.CosasPatrones;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car {

    private String make;
    private int numberOfSeats;

    public Car(String make, int numberOfSeats) {
        this.make = make;
        this.numberOfSeats = numberOfSeats;
    }


}

