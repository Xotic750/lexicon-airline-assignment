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

import static assignment.CompanyTypes.AIRLINE;
import static assignment.GeneralUtils.println;
import java.io.Serializable;
import static java.util.Objects.requireNonNull;

/**
 * An object for holding an airline's details.
 *
 * @author Graham Fairweather
 */
public class Airline extends Company implements Serializable {

    private final Employees employees;
    private final Passengers passengers;
    private final Bookings bookings;
    private final Meals meals;
    private final Aircrafts aircrafts;
    private final Flights flights;

    /**
     * Allocates an <code>Airline</code> object and initialises it.
     *
     * @param name The airline's name
     * @param address The airline's address
     * @param phone The airline's phone number
     */
    public Airline(String name, Address address, Phone phone) {
        super(name, address, phone, AIRLINE);
        employees = requireNonNull(new Employees());
        passengers = requireNonNull(new Passengers());
        bookings = requireNonNull(new Bookings());
        meals = requireNonNull(new Meals());
        aircrafts = requireNonNull(new Aircrafts());
        flights = requireNonNull(new Flights());
    }

    /**
     * Gets the airline's {@link Flights}.
     *
     * @return The flights
     */
    public final Flights getFlights() {
        return flights;
    }

    /**
     * Gets the airline's {@link Employees}.
     *
     * @return The employee's
     */
    public final Employees getEmployees() {
        return employees;
    }

    /**
     * Gets the airline's {@link Passengers}.
     *
     * @return the passenger's
     */
    public final Passengers getPassengers() {
        return passengers;
    }

    /**
     * Gets the airline's {@link Bookings}.
     *
     * @return The booking's
     */
    public final Bookings getBookings() {
        return bookings;
    }

    /**
     * Gets the airline's {@link Meals}.
     *
     * @return The meals
     */
    public final Meals getMeals() {
        return meals;
    }

    /**
     * Gets the airline's {@link Aircrafts}.
     *
     * @return The aircraft
     */
    public final Aircrafts getAircrafts() {
        return aircrafts;
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Airline UUID: " + getId());
        super.print();
        println("Airline No. employees: " + employees.size());
        println("Airline No. aircraft: " + aircrafts.size());
    }

    @Override
    public String toString() {
        return "Airline: " + super.toString();
    }

}
