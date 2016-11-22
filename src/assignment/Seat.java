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

import static assignment.SeatsStatusTypes.AVAILABLE;
import static assignment.GeneralUtils.println;
import static assignment.GeneralUtils.requireGreaterOrEqual;
import java.io.Serializable;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Graham Fairweather
 */
public final class Seat extends AccountableObject implements Serializable {

    private final int seatNumber;
    private final Aircraft aircraft;
    private final ProductClassTypes type;
    private final AtomicReference<SeatsStatusTypes> status;

    /**
     * Allocates a <code>Seat</code> object and initialises it.
     *
     * @param aircraft
     * @param seatNumber
     * @param type
     */
    public Seat(Aircraft aircraft, int seatNumber, ProductClassTypes type) {
        this.aircraft = requireNonNull(aircraft);
        this.seatNumber = requireGreaterOrEqual(seatNumber, 1);
        this.type = requireNonNull(type);
        this.status = new AtomicReference<>(requireNonNull(AVAILABLE));
    }

    /**
     *
     * @return
     */
    public SeatsStatusTypes getStatus() {
        return status.get();
    }

    /**
     *
     * @param status
     */
    public void setStatus(SeatsStatusTypes status) {
        this.status.set(requireNonNull(status));
        setModified();
    }

    /**
     *
     * @return
     */
    public int getSeatNumber() {
        return seatNumber;
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
    public ProductClassTypes getType() {
        return type;
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Seat number: " + seatNumber);
        println("Seat Type: " + type);
    }

    @Override
    public String toString() {
        return "Seat{" + "seatNumber=" + seatNumber + ", aircraft=" + aircraft + ", type=" + type + "} " + super.toString();
    }

}
