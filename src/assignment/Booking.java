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
import static assignment.GeneralUtils.println;
import static assignment.SeatsStatusTypes.RESERVED;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Extended {@link  AccountableObject} to hold booking information.
 *
 * @author Graham Fairweather
 */
public class Booking extends AccountableObject implements Serializable {

    private final Flight flight;
    private final Passenger passenger;
    private final Seat seat;
    private final Meal meal;
    private final Price price;
    private final Price costs;
    private final Price profit;
    private AtomicReference<BookingStatusTypes> status;

    /**
     * Allocates a <code>Booking</code> object and initialises it.
     *
     * @param flight The {@link Flight}
     * @param passenger The {@link Passenger}
     * @param seat The {@link Seat}
     * @param meal The {@link Meal}
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
                throw new RuntimeException("missing BookingStatusTypes");
        }
        BigDecimal bdFlightPrice = new BigDecimal(flightPrice.getValue());
        BigDecimal bdMealPrice = new BigDecimal(meal.getPrice().getValue());
        BigDecimal bdTotal = bdFlightPrice.add(bdMealPrice);
        BigDecimal bdCost = bdTotal.multiply(new BigDecimal("0.7")).setScale(2, RoundingMode.UP);
        BigDecimal bdProfit = bdTotal.subtract(bdCost);
        if (bdCost.add(bdProfit).compareTo(bdTotal) != 0) {
            throw new ArithmeticException("rounding error");
        }
        this.price = new Price(bdTotal);
        this.costs = new Price(bdCost);
        this.profit = new Price(bdProfit);
        if (seat.getStatus() == RESERVED) {
            throw new IllegalArgumentException("seat is already reserved");
        }
        seat.setStatus(RESERVED);
        this.status = new AtomicReference<>(CONFIRMED);
    }

    /**
     * Gets the seat that was reserved for this booking.
     *
     * @return The seat
     */
    public Seat getSeat() {
        return seat;
    }

    /**
     * Gets the flight.
     *
     * @return The flight
     */
    public final Flight getFlight() {
        return flight;
    }

    /**
     * Gets the passenger.
     *
     * @return The passenger
     */
    public final Passenger getPassenger() {
        return passenger;
    }

    /**
     * Get the total price of the booking.
     *
     * @return The total price
     */
    public final Price getPrice() {
        return price;
    }

    /**
     * Gets the meal chosen.
     *
     * @return The meal
     */
    public final Meal getMeal() {
        return meal;
    }

    /**
     * Gets the booking status.
     *
     * @return The status.
     */
    public final BookingStatusTypes getStatus() {
        return status.get();
    }

    /**
     * Sets the status of this booking.
     *
     * @param status A {@link BookingStatusTypes}
     */
    public final void setStatus(BookingStatusTypes status) {
        this.status.set(status);
        setModified();
    }

    /**
     * Get the operating cost portion of the total price.
     *
     * @return The cost
     */
    public Price getCosts() {
        return costs;
    }

    /**
     * Get the operating profit portion of the total price.
     *
     * @return The cost
     */
    public Price getProfit() {
        return profit;
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
        println("Booking status: " + status);
        println("Booking total price: " + price.getValue());
        println("Booking cost: " + costs.getValue());
        println("Booking profit: " + profit.getValue());
    }

    @Override
    public String toString() {
        return "Booking{" + "flight=" + flight + ", passenger=" + passenger + ", seat=" + seat + ", meal=" + meal + ", price=" + price + ", costs=" + costs + ", profit=" + profit + ", status=" + status + "} " + super.toString();
    }

}
