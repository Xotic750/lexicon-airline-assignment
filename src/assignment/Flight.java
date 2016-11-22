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

import static assignment.ProductClassTypes.ECONOMY;
import static assignment.ProductClassTypes.FIRST;
import static assignment.GeneralUtils.println;
import static assignment.GeneralUtils.requireNotEmpty;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicReference;

/**
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
        int seatNumber = 1;
        for (int index = 0; index < aircraft.getFirstClassSeatCount(); index++) {
            Seat seat = new Seat(aircraft, seatNumber++, FIRST);
            this.seats.add(seat);
        }
        for (int index = 0; index < aircraft.getEconomyClassSeatCount(); index++) {
            Seat seat = new Seat(aircraft, seatNumber++, ECONOMY);
            this.seats.add(seat);
        }
        this.status = new AtomicReference<>(FlightStatusTypes.OPEN);
    }

    /**
     *
     * @return
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     *
     * @return
     */
    public Aircraft getAircraft() {
        return aircraft;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getDepartureDate() {
        return departureDateTime;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getArrivalDate() {
        return arrivalDateTime;
    }

    /**
     *
     * @return
     */
    public Airport getFrom() {
        return from;
    }

    /**
     *
     * @return
     */
    public Airport getTo() {
        return to;
    }

    /**
     *
     * @return
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     *
     * @return
     */
    public Price getFirstClassPrice() {
        return firstClassPrice;
    }

    /**
     *
     * @return
     */
    public Price getEconomyClassPrice() {
        return economyClassPrice;
    }

    /**
     *
     * @return
     */
    public FlightStatusTypes getStatus() {
        return status.get();
    }

    /**
     *
     * @return
     */
    public Seats getSeats() {
        return seats;
    }

    /**
     *
     * @param status
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
