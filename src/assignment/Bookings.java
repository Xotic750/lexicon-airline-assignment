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
import static assignment.BookingStatusTypes.ClOSED;
import static assignment.GeneralUtils.printlnLineSpaced;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.Function;

/**
 * Resizable-array implementation of the <tt>List</tt> interface for {@link
 * Booking} objects. Implements all optional list operations, and permits all
 * elements, excluding
 * <tt>null</tt>.
 *
 * @author Graham Fairweather
 * @see AbstractNoNullList
 */
public class Bookings extends AbstractNoNullList<Booking> implements Serializable {

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public Bookings() {
        this(10);
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity is
     * negative
     */
    public Bookings(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs a list containing the elements of the specified collection, in
     * the order they are returned by the collection's iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public Bookings(Collection<? extends Booking> c) {
        super(c);
    }

    /**
     * Get a {@code Bookings} list of {code Booking} objects that match the
     * flight name.
     *
     * @return A {@code Bookings} list
     */
    public final Bookings getBookingsByFlightNumber(String flightNumber) {
        return new Bookings(filter(booking -> booking.getFlight().getFlightNumber().equals(flightNumber)));
    }

    /**
     * Get a {@code Bookings} list of {code Booking} objects with the status of
     * "CLOSED".
     *
     * @return A {@code Bookings} list of "CONFIRMED" {code Booking} objects
     */
    public final Bookings confirmed() {
        return new Bookings(filter(booking -> booking.getStatus() == CONFIRMED));
    }

    /**
     * Get a {@code Bookings} list of {code Booking} objects with the status of
     * "CLOSED".
     *
     * @return A {@code Bookings} list of "CLOSED" {code Booking} objects
     */
    public final Bookings closed() {
        return new Bookings(filter(booking -> booking.getStatus() == ClOSED));
    }

    /**
     * Takes a functional interface to map a {@link Booking} to a
     * {@link BigDecimal} and then sums the values and returns a
     * {@link BigDecimal}
     *
     * @param bdMapper Functional interface
     * @return A {@link BigDecimal}
     */
    private BigDecimal sum(Function<Booking, BigDecimal> bdMapper) {
        synchronized (this) {
            return this.stream().map(bdMapper).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    /**
     * Sum all the prices of the bookings.
     *
     * @return The total of all the booking prices
     */
    public final Price getPrice() {
        return new Price(sum((Booking booking) -> booking.getPrice().getBigDecimal()));
    }

    /**
     * Sum all the costs of the bookings.
     *
     * @return The total of all the booking prices
     */
    public final Price getCosts() {
        return new Price(sum((Booking booking) -> booking.getCosts().getBigDecimal()));
    }

    /**
     * Sum all the profit of the bookings.
     *
     * @return The total of all the booking profits
     */
    public final Price getProfit() {
        return new Price(sum((Booking booking) -> booking.getProfit().getBigDecimal()));
    }

    /**
     * Prints each {@link Booking} in the list.
     */
    @Override
    public void print() {
        printlnLineSpaced("List of Bookings:");
        super.print();
    }

    @Override
    public String toString() {
        return "Bookings: " + super.toString();
    }

}
