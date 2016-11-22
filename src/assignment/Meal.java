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
import static assignment.GeneralUtils.requireNotEmpty;
import java.io.Serializable;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Graham Fairweather
 */
public class Meal extends AccountableObject implements Serializable {

    private final ProductClassTypes type;
    private final String description;
    private final AtomicReference<Price> price;

    /**
     * Allocates a <code>Meal</code> object and initialises it.
     *
     * @param type
     * @param description
     * @param price
     */
    public Meal(ProductClassTypes type, String description, Price price) {
        this.type = requireNonNull(type);
        this.description = requireNotEmpty(description);
        this.price = new AtomicReference<>(requireNonNull(price));
    }

    /**
     *
     * @return
     */
    public final ProductClassTypes getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public final String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public final Price getPrice() {
        return price.get();
    }

    /**
     *
     * @param price
     */
    public final void setPrice(Price price) {
        this.price.set(requireNonNull(price));
        setModified();
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Meal UUID: " + getId());
        println("Meal type: " + type);
        println("Meal Description: " + description);
        println("Meal Price: " + price.get().getValue());
    }

    @Override
    public String toString() {
        return "Meal{" + "type=" + type + ", description=" + description + ", price=" + price + "} " + super.toString();
    }

}
