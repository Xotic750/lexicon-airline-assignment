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
import java.io.Serializable;
import static assignment.GeneralUtils.requireNotEmpty;

/**
 *
 * @author Graham Fairweather
 */
public class Phone extends AccountableObject implements Serializable {

    private final String countryCode;
    private final String areaCode;
    private final String subscriberNumber;

    /**
     * Allocates a <code>Phone</code> object and initialises it.
     *
     * @param countryCode
     * @param areaCode
     * @param subscriberNumber
     */
    public Phone(String countryCode, String areaCode, String subscriberNumber) {
        this.countryCode = requireNotEmpty(countryCode);
        this.areaCode = requireNotEmpty(areaCode);
        this.subscriberNumber = requireNotEmpty(subscriberNumber);
    }

    /**
     *
     * @return
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     *
     * @return
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     *
     * @return
     */
    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Phone Country code: " + countryCode);
        println("Phone Area code: " + areaCode);
        println("Phone Subscriber number: " + subscriberNumber);
    }

    @Override
    public String toString() {
        return "Phone{" + "countryCode=" + countryCode + ", areaCode=" + areaCode + ", subscriberNumber=" + subscriberNumber + "} " + super.toString();
    }

}
