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
import static assignment.GeneralUtils.requireGreaterOrEqual;
import static assignment.GeneralUtils.requireNotEmpty;
import java.io.Serializable;
import static java.util.Objects.requireNonNull;

/**
 * Extended {@link  AccountableObject} to hold aircraft information.
 * 
 * @author Graham Fairweather
 */
public abstract class Aircraft extends AccountableObject implements Serializable {

    private final String name;
    private final AircraftTypes type;
    private final String make;
    private final String model;
    private final int firstClassSeatCount;
    private final int economyClassSeatCount;

    /**
     * Allocates an <code>Aircraft</code> object and initialises it.
     *
     * @param name
     * @param type
     * @param make
     * @param model
     * @param firstClassSeatCount
     * @param economyClassSeatCount
     */
    public Aircraft(String name, AircraftTypes type, String make, String model, int firstClassSeatCount, int economyClassSeatCount) {
        this.name = requireNotEmpty(name);
        this.type = requireNonNull(type);
        this.make = requireNotEmpty(make);
        this.model = requireNotEmpty(model);
        this.firstClassSeatCount = requireGreaterOrEqual(firstClassSeatCount, 0);
        this.economyClassSeatCount = requireGreaterOrEqual(economyClassSeatCount, 0);
    }

    /**
     *
     * @return
     */
    public final String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public final AircraftTypes getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public final String getMake() {
        return make;
    }

    /**
     *
     * @return
     */
    public final String getModel() {
        return model;
    }

    /**
     *
     * @return
     */
    public final int getFirstClassSeatCount() {
        return firstClassSeatCount;
    }

    /**
     *
     * @return
     */
    public final int getEconomyClassSeatCount() {
        return economyClassSeatCount;
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Aircraft name: " + name);
        println("Aircraft Type: " + type);
        println("Aircraft Make: " + make);
        println("Aircraft Model: " + model);
        println("Aircraft First class seats: " + firstClassSeatCount);
        println("Aircraft Economy class seats: " + economyClassSeatCount);
    }

    @Override
    public String toString() {
        return "Aircraft{" + "name=" + name + ", type=" + type + ", make=" + make + ", model=" + model + "} " + super.toString();
    }

}
