/*
 * The MIT License
 *
 * Copyright 2016 Graham Fairweather.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package assignment;

import static assignment.GenderTypes.FEMALE;
import static assignment.GenderTypes.MALE;
import static assignment.ProductClassTypes.ECONOMY;
import static assignment.ProductClassTypes.FIRST;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import static java.time.LocalDate.of;
import java.time.LocalDateTime;
import static java.time.Month.APRIL;
import static java.time.Month.DECEMBER;
import static java.time.Month.MAY;

/**
 *
 * @author Graham Fairweather
 */
public class Data implements Serializable {

    final static Airlines AIRLINES;
    final static Airports AIRPORTS;

    static {
        AIRPORTS = new Airports();

        // Airport 1
        String airportName = "Stockholm International Airport";

        String address1 = "Luftgatan 1";
        String address2 = "Stockholm";
        String postcode = "17752";
        String country = "Sweden";
        Address address = new Address(address1, address2, postcode, country);

        String countryCode = "0046";
        String areaCode = "070";
        String subscriberNumber = "3333 444";
        Phone phone = new Phone(countryCode, areaCode, subscriberNumber);

        Airport airport = new Airport(airportName, address, phone);

        AIRPORTS.add(airport);

        // Airport 2
        airportName = "London International Airport";

        address1 = "Whatto Drive 1";
        address2 = "London";
        postcode = "EC4 6HP";
        country = "England";
        address = new Address(address1, address2, postcode, country);

        countryCode = "0043";
        areaCode = "020";
        subscriberNumber = "6699 123";
        phone = new Phone(countryCode, areaCode, subscriberNumber);

        airport = new Airport(airportName, address, phone);

        AIRPORTS.add(airport);
    }

    static {
        AIRLINES = new Airlines();

        // Airline
        String airlineName = "Queezy Jet";

        String address1 = "Nån gatan 3";
        String address2 = "Stockholm";
        String postcode = "17052";
        String country = "Sweden";
        Address address = new Address(address1, address2, postcode, country);

        String countryCode = "0046";
        String areaCode = "070";
        String subscriberNumber = "1234 567";
        Phone phone = new Phone(countryCode, areaCode, subscriberNumber);

        Airline airline1 = new Airline(airlineName, address, phone);

        /* Aircraft */
        // Aircraft 1
        String aircraftName = "QJ1";
        String make = "Cessna";
        String model = "441";
        int firstClassSeatCount = 4;
        int economyClassSeatCount = 6;
        Aircraft aircraft = new AircraftOfPassengerType(aircraftName, make, model, firstClassSeatCount, economyClassSeatCount);
        airline1.getAircrafts().add(aircraft);

        // Aircraft 2
        aircraftName = "QJ2";
        make = "Cessna";
        model = "Caravan";
        firstClassSeatCount = 6;
        economyClassSeatCount = 8;
        aircraft = new AircraftOfPassengerType(aircraftName, make, model, firstClassSeatCount, economyClassSeatCount);
        airline1.getAircrafts().add(aircraft);

        // Aircraft 3
        aircraftName = "QJ3";
        make = "Cessna";
        model = "Model 680 Sovereign";
        firstClassSeatCount = 4;
        economyClassSeatCount = 8;
        aircraft = new AircraftOfPassengerType(aircraftName, make, model, firstClassSeatCount, economyClassSeatCount);
        airline1.getAircrafts().add(aircraft);

        /* Meals */
        // Meal 1
        ProductClassTypes productType = ECONOMY;
        String description = "Cheese Roll plus Coke.";
        Price price = new Price("1.5");
        Meal meal = new Meal(productType, description, price);
        airline1.getMeals().add(meal);

        // Meal 2
        productType = ECONOMY;
        description = "Ham Roll plus Coke.";
        price = new Price("1.5");
        meal = new Meal(productType, description, price);
        airline1.getMeals().add(meal);

        // Meal 3
        productType = FIRST;
        description = "Koura-yaki plus Hiedsieck 1907 Diamant Bleu cuvee.";
        price = new Price("19.99");
        meal = new Meal(productType, description, price);
        airline1.getMeals().add(meal);

        // Meal 4
        productType = FIRST;
        description = "Mandarin Oriental Roast Chicken plus Hiedsieck 1907 Diamant Bleu cuvee.";
        price = new Price("19.99");
        meal = new Meal(productType, description, price);
        airline1.getMeals().add(meal);

        /* Passengers */
        // Passenger 1
        String forName = "Graham";
        String surName = "Fairweather";
        GenderTypes gender = MALE;
        LocalDate birthDate = of(1971, MAY, 7);

        address1 = "Blahvägan 15";
        address2 = "Stockholm";
        postcode = "17156";
        country = "Sweden";
        address = new Address(address1, address2, postcode, country);

        countryCode = "0046";
        areaCode = "070";
        subscriberNumber = "9876 543";
        phone = new Phone(countryCode, areaCode, subscriberNumber);

        Passenger passenger = new Passenger(forName, surName, gender, birthDate, address, phone);
        airline1.getPassengers().add(passenger);

        // Passenger 2
        forName = "Sten";
        surName = "Sjökvist";
        gender = MALE;
        birthDate = of(1985, DECEMBER, 21);

        address1 = "Whoopvägan 8";
        address2 = "Stockholm";
        postcode = "17623";
        country = "Sweden";
        address = new Address(address1, address2, postcode, country);

        countryCode = "0046";
        areaCode = "070";
        subscriberNumber = "6541 852";
        phone = new Phone(countryCode, areaCode, subscriberNumber);

        passenger = new Passenger(forName, surName, gender, birthDate, address, phone);
        airline1.getPassengers().add(passenger);

        // Passenger 3
        forName = "Anna";
        surName = "Lövbiff";
        gender = FEMALE;
        birthDate = of(1965, APRIL, 1);

        address1 = "Wibblegatan 22";
        address2 = "Stockholm";
        postcode = "17624";
        country = "Sweden";
        address = new Address(address1, address2, postcode, country);

        countryCode = "0046";
        areaCode = "070";
        subscriberNumber = "3541 349";
        phone = new Phone(countryCode, areaCode, subscriberNumber);

        passenger = new Passenger(forName, surName, gender, birthDate, address, phone);
        airline1.getPassengers().add(passenger);

        // Employee
        forName = "Graham";
        surName = "Fairweather";
        gender = MALE;
        birthDate = of(1971, MAY, 7);

        address1 = "Blahvägan 15";
        address2 = "Stockholm";
        postcode = "17156";
        country = "Sweden";
        address = new Address(address1, address2, postcode, country);

        countryCode = "0046";
        areaCode = "070";
        subscriberNumber = "9876 543";
        phone = new Phone(countryCode, areaCode, subscriberNumber);

        LocalDate startDate = of(1971, MAY, 7);

        Employee employee = new Employee(forName, surName, gender, birthDate, address, phone, startDate);
        airline1.getEmployees().add(employee);

        /* Flights */
        // Flight1
        String flightNumber = "SL1";
        aircraft = airline1.getAircrafts().getByName("QJ1");
        LocalDateTime departureDate = LocalDateTime.now().plusMinutes(60);
        Airport from = AIRPORTS.getByName("Stockholm");
        Airport to = AIRPORTS.getByName("London");
        Duration duration = Duration.ofMinutes(150);
        Price firstClassPrice = new Price("20000");
        Price economyClassPrice = new Price("5000");
        Flight flight = new Flight(flightNumber, aircraft, departureDate, from, to, duration, firstClassPrice, economyClassPrice);
        airline1.getFlights().add(flight);

        // Flight2
        flightNumber = "LS1";
        aircraft = airline1.getAircrafts().getByName("QJ2");
        departureDate = LocalDateTime.now().plusHours(60);
        from = AIRPORTS.getByName("London");
        to = AIRPORTS.getByName("Stockholm");
        duration = Duration.ofMinutes(120);
        firstClassPrice = new Price("20000");
        economyClassPrice = new Price("50000");
        flight = new Flight(flightNumber, aircraft, departureDate, from, to, duration, firstClassPrice, economyClassPrice);
        airline1.getFlights().add(flight);

        /* Bookings */
        // Booking 1
        flight = airline1.getFlights().getByFlightNumber("LS1");
        passenger = airline1.getPassengers().getByName("Graham");
        Seat seat = flight.getSeats().getFirstClassSeatsAvailable().get(0);
        meal = airline1.getMeals().getFirstClassMeals().getByDescription("Mandarin");
        Booking booking = new Booking(flight, passenger, seat, meal);
        airline1.getBookings().add(booking);

        // Booking 2
        flight = airline1.getFlights().getByFlightNumber("LS1");
        passenger = airline1.getPassengers().getByName("Sten");
        seat = flight.getSeats().getEconomyClassSeatsAvailable().get(0);
        meal = airline1.getMeals().getEconomyClassMeals().getByDescription("Ham");
        booking = new Booking(flight, passenger, seat, meal);
        airline1.getBookings().add(booking);

        // Booking 3
        flight = airline1.getFlights().getByFlightNumber("LS1");
        passenger = airline1.getPassengers().getByName("Anna");
        seat = flight.getSeats().getEconomyClassSeatsAvailable().get(0);
        meal = airline1.getMeals().getEconomyClassMeals().getByDescription("Cheese");
        booking = new Booking(flight, passenger, seat, meal);
        airline1.getBookings().add(booking);

        // Add the airline
        AIRLINES.add(airline1);
    }

    private Data() {
    }
}
