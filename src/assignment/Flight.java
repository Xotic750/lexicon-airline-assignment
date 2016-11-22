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

import static assignment.GeneralUtils.println;
import static assignment.GeneralUtils.requireNotEmpty;
import static assignment.ProductClassTypes.ECONOMY;
import static assignment.ProductClassTypes.FIRST;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Extended {@link  AccountableObject} to hold flight information.
 *
 * @author Graham Fairweather
 */
public class Flight extends AccountableObject implements Serializable {

    private final String flightNumber;
    private final Aircraft aircraft;
    private final LocalDateTime departureDateTime;
    private final LocalDateTime arrivalDateTime;
    private final Airport from;
    private final Airport to;
    private final Duration duration;
    private final Price firstClassPrice;
    private final Price economyClassPrice;
    private final AtomicReference<FlightStatusTypes> status;
    private final Seats seats;

    /**
     * Called during construction of a flight to initialise the seats as per the
     * aircraft definition.
     *
     * @param flight The flight
     * @param nextSeatNumber The next seat number to use during allocation
     * @param productType The {@link ProductClassTypes}
     */
    private static int addSeats(Flight flight, int nextSeatNumber, ProductClassTypes productType) {
        for (int index = 0; index < flight.aircraft.getFirstClassSeatCount(); index++) {
            flight.seats.add(new Seat(flight.aircraft, nextSeatNumber++, productType));
        }
        return nextSeatNumber;
    }

    /**
     * Allocates a <code>Flight</code> object and initialises it.
     *
     * @param flightNumber
     * @param aircraft
     * @param departureDateTime
     * @param from
     * @param to
     * @param duration
     * @param firstClassPrice
     * @param economyClassPrice
     */
    public Flight(String flightNumber, Aircraft aircraft, LocalDateTime departureDateTime, Airport from, Airport to, Duration duration, Price firstClassPrice, Price economyClassPrice) {
        this.flightNumber = requireNotEmpty(flightNumber);
        this.aircraft = requireNonNull(aircraft);
        this.departureDateTime = requireNonNull(departureDateTime);
        this.from = requireNonNull(from);
        this.to = requireNonNull(to);
        this.duration = requireNonNull(duration);
        this.arrivalDateTime = departureDateTime.plus(duration);
        this.firstClassPrice = requireNonNull(firstClassPrice);
        this.economyClassPrice = requireNonNull(economyClassPrice);
        this.seats = new Seats();
        int nextSeatNumber = 1;
        nextSeatNumber = addSeats(this, nextSeatNumber, FIRST);
        addSeats(this, nextSeatNumber, ECONOMY);
        this.status = new AtomicReference<>(FlightStatusTypes.OPEN);
    }

    /**
     * Gets the flight number.
     *
     * @return the flight number
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Gets the aircraft.
     *
     * @return The aircraft
     */
    public Aircraft getAircraft() {
        return aircraft;
    }

    /**
     * Gets the departure date and time.
     *
     * @return The departure date and time
     */
    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    /**
     * Get the arrival date and time, calculated using the departure date and
     * time plus the duration.
     *
     * @return The arrival date and time
     */
    public LocalDateTime getArrivalDate() {
        return arrivalDateTime;
    }

    /**
     * Get the departure airport.
     *
     * @return The airport
     */
    public Airport getFrom() {
        return from;
    }

    /**
     * Get the arrival airport.
     *
     * @return The airport
     */
    public Airport getTo() {
        return to;
    }

    /**
     * Get the duration of the flight.
     *
     * @return The {@link Duration}
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Get the price of a first class seat.
     *
     * @return The price
     */
    public Price getFirstClassPrice() {
        return firstClassPrice;
    }

    /**
     * Get the price of an economy class seat.
     *
     * @return The price
     */
    public Price getEconomyClassPrice() {
        return economyClassPrice;
    }

    /**
     * Get the current status of the flight.
     *
     * @return The {@link FlightStatusTypes}
     */
    public FlightStatusTypes getStatus() {
        return status.get();
    }

    /**
     * Get the seat list.
     *
     * @return The seats
     */
    public Seats getSeats() {
        return seats;
    }

    /**
     * Set the current flight status.
     *
     * @param status The {@link FlightStatusTypes}
     */
    public void setStatus(FlightStatusTypes status) {
        this.status.set(requireNonNull(status));
        setModified();
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Flight UUID: " + getId());
        println("Flight number: " + flightNumber);
        println("Flight Status: " + status);
        println("Flight Aircraft: " + aircraft.getName());
        println("Flight From: " + from.getName());
        println("Flight To: " + to.getName());
        println("Flight Departs: " + departureDateTime);
        println("Flight Arrives: " + arrivalDateTime);
        println("Flight First class available: " + seats.getFirstClassSeatsAvailable().size() + " @ " + firstClassPrice.getValue());
        println("Flight Economy class seats available: " + seats.getEconomyClassSeatsAvailable().size() + " @ " + economyClassPrice.getValue());
    }

    @Override
    public String toString() {
        return "Flight{" + "flightNumber=" + flightNumber + ", aircraft=" + aircraft + ", departureDate=" + departureDateTime + ", arrivalDate=" + arrivalDateTime + ", from=" + from + ", to=" + to + ", duration=" + duration + ", firstClassPrice=" + firstClassPrice + ", economyClassPrice=" + economyClassPrice + ", status=" + status + ", seats=" + seats + "} " + super.toString();
    }

}
