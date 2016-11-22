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

import static assignment.PersonTypes.PASSENGER;
import static assignment.GeneralUtils.println;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Graham Fairweather
 */
public final class Passenger extends Person implements Serializable {

    /**
     * Allocates a <code>Passenger</code> object and initialises it.
     *
     * @param foreName The passenger's forename
     * @param surName The passenger's surname
     * @param gender The passenger's {@link GenderTypes}
     * @param birthDate The passenger's birth date
     * @param address The passenger's {@link Address}
     * @param phone The passenger's {@link Phone} number
     */
    public Passenger(String foreName, String surName, GenderTypes gender, LocalDate birthDate, Address address, Phone phone) {
        super(foreName, surName, gender, birthDate, address, phone, PASSENGER);
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Passenger UUID: " + getId());
        super.print();
    }

    @Override
    public String toString() {
        return "Passenger{" + "} " + super.toString();
    }

}
