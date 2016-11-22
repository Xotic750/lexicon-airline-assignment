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
import static assignment.SeatsStatusTypes.AVAILABLE;
import static assignment.SeatsStatusTypes.RESERVED;
import static assignment.GeneralUtils.printlnLineSpaced;
import java.io.Serializable;
import java.util.Collection;

/**
 * Resizable-array implementation of the <tt>List</tt> interface for {@link
 * Seat} objects. Implements all optional list operations, and permits all
 * elements, excluding
 * <tt>null</tt>.
 *
 * @author Graham Fairweather
 * @see AbstractNoNullList
 */
public class Seats extends AbstractNoNullList<Seat> implements Serializable {

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public Seats() {
        this(10);
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity is
     * negative
     */
    public Seats(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs a list containing the elements of the specified collection, in
     * the order they are returned by the collection's iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public Seats(Collection<? extends Seat> c) {
        super(c);
    }

    /**
     * Get a {@code Seats} list of {code Seat} objects with the status of
     * "AVAILABLE".
     *
     * @return A {@code Seats} list of {code Seat} objects
     */
    public Seats available() {
        return new Seats(filter(seat -> seat.getStatus() == AVAILABLE));
    }

    /**
     * Get a {@code Seats} list of {code Seat} objects with the status of
     * "RESERVED".
     *
     * @return A {@code Seats} list of {code Seat} objects
     */
    public Seats reserved() {
        return new Seats(filter(seat -> seat.getStatus() == RESERVED));
    }

    /**
     * Get a {@code Seats} list of {code Seat} objects with the type of "FIRST".
     *
     * @return A {@code Seats} list of {code Seat} objects
     */
    public Seats getFirstClassSeats() {
        return new Seats(filter(seat -> seat.getType() == FIRST));
    }

    /**
     * Get a {@code Seats} list of {code Seat} objects with the type of "FIRST"
     * and a status of "AVAILABLE".
     *
     * @return A {@code Seats} list of {code Seat} objects
     */
    public Seats getFirstClassSeatsAvailable() {
        return new Seats(available().filter(seat -> seat.getType() == FIRST));
    }

    /**
     * Get a {@code Seats} list of {code Seat} objects with the type of "FIRST"
     * and a status of "RESERVED".
     *
     * @return A {@code Seats} list of {code Seat} objects
     */
    public Seats getFirstClassSeatsReserved() {
        return new Seats(reserved().filter(seat -> seat.getType() == FIRST));
    }

    /**
     * Get a {@code Seats} list of {code Seat} objects with the type of
     * "ECONOMY".
     *
     * @return A {@code Seats} list of {code Seat} objects
     */
    public Seats getEconomyClassSeats() {
        return new Seats(filter(seat -> seat.getType() == ECONOMY));
    }

    /**
     * Get a {@code Seats} list of {code Seat} objects with the type of
     * "ECONOMY" and a status of "AVAILABLE".
     *
     * @return A {@code Seats} list of {code Seat} objects
     */
    public Seats getEconomyClassSeatsAvailable() {
        return new Seats(available().filter(seat -> seat.getType() == ECONOMY));
    }

    /**
     * Get a {@code Seats} list of {code Seat} objects with the type of
     * "ECONOMY" and a status of "RESERVED".
     *
     * @return A {@code Seats} list of {code Seat} objects
     */
    public Seats getEconomyClassSeatsReserved() {
        return new Seats(reserved().filter(seat -> seat.getType() == ECONOMY));
    }

    /**
     * Get a {@code Seat} by its seat number on the airplane.
     *
     * @param seatnumber The seat number
     * @return A {@code Seat} or null
     */
    public Seat getSeatBy(int seatnumber) {
        return find(seat -> seat.getSeatNumber() == seatnumber);
    }

    /**
     * Prints each {@link Seat} in the list.
     */
    @Override
    public void print() {
        printlnLineSpaced("List of Seats:");
        super.print();
    }

    @Override
    public String toString() {
        return "Seats: " + super.toString();
    }

}
