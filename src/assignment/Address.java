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
 * Extended {@link  AccountableObject} to hold address information.
 *
 * @author Graham Fairweather
 */
public class Address extends AccountableObject implements Serializable {

    private final String address1;
    private final String address2;
    private final String postcode;
    private final String country;

    /**
     * Allocates an <code>Address</code> object and initialises it.
     *
     * @param address1 First line of the address
     * @param address2 Second line of the address
     * @param postcode The postcode
     * @param country The country
     */
    public Address(String address1, String address2, String postcode, String country) {
        this.address1 = requireNotEmpty(address1);
        this.address2 = requireNotEmpty(address2);
        this.postcode = requireNotEmpty(postcode);
        this.country = requireNotEmpty(country);
    }

    /**
     * Gets the first line of the address.
     *
     * @return First line of the address
     */
    public final String getAddress1() {
        return address1;
    }

    /**
     * Gets the second line of the address.
     *
     * @return Second line of the address
     */
    public final String getAddress2() {
        return address2;
    }

    /**
     * Gets the postcode.
     *
     * @return The postcode
     */
    public final String getPostcode() {
        return postcode;
    }

    /**
     * Gets the country.
     *
     * @return The country
     */
    public final String getCountry() {
        return country;
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Address: " + address1);
        println("Address: " + address2);
        println("Postcode: " + postcode);
        println("Country: " + country);
    }

    @Override
    public String toString() {
        return "Address{" + "address1=" + address1 + ", address2=" + address2 + ", postcode=" + postcode + ", country=" + country + "} " + super.toString();
    }

}
