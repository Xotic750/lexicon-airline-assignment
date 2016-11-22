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
import java.math.BigDecimal;
import static java.math.RoundingMode.UP;
import static assignment.GeneralUtils.requireBigDecimalParseable;

/**
 *
 * @author Graham Fairweather
 */
public class Price extends AccountableObject implements Serializable {

    private final String price;

    /**
     * Allocates an <code>Address</code> object and initialises it with 0.
     */
    public Price() {
        this("0");
    }

    /**
     * Allocates an <code>Address</code> object and initialises it.
     *
     * @param price
     */
    public Price(String price) {
        this.price = requireBigDecimalParseable(price);
    }

    /**
     * Allocates an <code>Address</code> object and initialises it.
     *
     * @param price
     */
    public Price(Price price) {
        this(price.getValue());
    }

    /**
     *
     * @param price
     */
    public Price(BigDecimal price) {
        this(price.setScale(2, UP).toString());
    }

    /**
     *
     * @return
     */
    public final String getValue() {
        return price;
    }

    /**
     *
     * @return
     */
    public final BigDecimal getBigDecimal() {
        return new BigDecimal(price);
    }
    
    /**
     *
     * @return
     */
    public final double getDouble() {
        return Double.parseDouble(price);
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Price: " + price);
    }

    @Override
    public String toString() {
        return "Price{" + "price=" + price + "} " + super.toString();
    }

}
