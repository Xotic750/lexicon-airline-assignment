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

import static assignment.GeneralUtils.printlnLineSpaced;
import static assignment.GeneralUtils.requireNotEmpty;
import java.io.Serializable;
import java.util.Collection;

/**
 * Resizable-array implementation of the <tt>List</tt> interface for {@link
 * Passenger} objects. Implements all optional list operations, and permits all
 * elements, excluding
 * <tt>null</tt>.
 *
 * @author Graham Fairweather
 * @see AbstractNoNullList
 */
public class Passengers extends AbstractNoNullList<Passenger> implements Serializable {

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public Passengers() {
        this(10);
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity is
     * negative
     */
    public Passengers(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs a list containing the elements of the specified collection, in
     * the order they are returned by the collection's iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public Passengers(Collection<? extends Passenger> c) {
        super(c);
    }

    /**
     * Get a {@code Passenger} object by matching the start of their name,
     * ignoring case.
     *
     * @param pattern A string description
     * @return A {@code Meal} object or {@code null}
     */
    public final Passenger getByNameIgnoreCase(String pattern) {
        final String p = requireNotEmpty(pattern).trim().toLowerCase();
        return find(passenger -> passenger.getName().toLowerCase().startsWith(p));
    }

    /**
     * Get a {@code Passenger} object by matching the start of their name
     *
     * @param pattern A string description
     * @return A {@code Meal} object or {@code null}
     */
    public final Passenger getByName(String pattern) {
        final String p = requireNotEmpty(pattern).trim();
        return find(passenger -> passenger.getName().startsWith(p));
    }

    /**
     * Prints each {@link Passenger} in the list.
     */
    @Override
    public void print() {
        printlnLineSpaced("List of Passengers:");
        super.print();
    }

    @Override
    public String toString() {
        return "Passengers: " + super.toString();
    }

}
