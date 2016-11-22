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

import static assignment.BookingStatusTypes.CONFIRMED;
import static assignment.SeatsStatusTypes.RESERVED;
import static assignment.GeneralUtils.println;
import java.io.Serializable;
import java.math.BigDecimal;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Graham Fairweather
 */
public class Booking extends AccountableObject implements Serializable {

    private final Flight flight;
    private final Passenger passenger;
    private final Seat seat;
    private final Meal meal;
    private final Price totalPrice;
    private AtomicReference<BookingStatusTypes> status;

    /**
     * Allocates an <code>Booking</code> object and initialises it.
     *
     * @param flight
     * @param passenger
     * @param seat
     * @param meal
     */
    public Booking(Flight flight, Passenger passenger, Seat seat, Meal meal) {
        this.flight = requireNonNull(flight);
        this.passenger = requireNonNull(passenger);
        this.meal = requireNonNull(meal);
        this.seat = requireNonNull(seat);
        Price flightPrice;
        switch (seat.getType()) {
            case FIRST:
                flightPrice = flight.getFirstClassPrice();
                break;
            case ECONOMY:
                flightPrice = flight.getEconomyClassPrice();
                break;
            default:
                throw new RuntimeException();
        }
        BigDecimal bdFlightPrice = new BigDecimal(flightPrice.getValue());
        BigDecimal bdMealPrice = new BigDecimal(meal.getPrice().getValue());
        this.totalPrice = new Price(bdFlightPrice.add(bdMealPrice));
        if (seat.getStatus() == RESERVED) {
            throw new RuntimeException();
        }
        seat.setStatus(RESERVED);
        this.status = new AtomicReference<>(CONFIRMED);
    }

    /**
     *
     * @return
     */
    public Seat getSeat() {
        return seat;
    }

    /**
     *
     * @return
     */
    public final Flight getFlight() {
        return flight;
    }

    /**
     *
     * @return
     */
    public final Passenger getPassenger() {
        return passenger;
    }

    /**
     *
     * @return
     */
    public final Price getPrice() {
        return totalPrice;
    }

    /**
     *
     * @return
     */
    public final Meal getMeal() {
        return meal;
    }

    /**
     *
     * @return
     */
    public final BookingStatusTypes getStatus() {
        return status.get();
    }

    /**
     *
     * @param status
     */
    public final void setStatus(BookingStatusTypes status) {
        this.status.set(status);
        setModified();
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Booking UUID: " + getId());
        flight.print();
        passenger.print();
        seat.print();
        meal.print();
        println("Booking total price: " + totalPrice.getValue());
        println("Booking status: " + status);
    }

    @Override
    public String toString() {
        return "Booking{" + "flight=" + flight + ", passenger=" + passenger + ", meal=" + meal + ", price=" + totalPrice + ", status=" + status + "} " + super.toString();
    }

}
