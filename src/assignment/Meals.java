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
import static assignment.GeneralUtils.printlnLineSpaced;
import static assignment.GeneralUtils.requireNotEmpty;
import java.io.Serializable;
import java.util.Collection;

/**
 * Resizable-array implementation of the <tt>List</tt> interface for {@link
 * Meal} objects. Implements all optional list operations, and permits all
 * elements, excluding
 * <tt>null</tt>.
 *
 * @author Graham Fairweather
 * @see AbstractNoNullList
 */
public class Meals extends AbstractNoNullList<Meal> implements Serializable {

    private static void noMeals(Meals meals) {
        // Meal 0
        ProductClassTypes productType = ECONOMY;
        String description = "None";
        Price price = new Price("0");
        Meal meal = new Meal(productType, description, price);
        meals.add(meal);

        // Meal 1
        productType = FIRST;
        description = "None.";
        price = new Price("0");
        meal = new Meal(productType, description, price);
        meals.add(meal);
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public Meals() {
        this(10);
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity is
     * negative
     */
    public Meals(int initialCapacity) {
        super(initialCapacity);
        Meals meals = this;
        noMeals(meals);
    }

    /**
     * Constructs a list containing the elements of the specified collection, in
     * the order they are returned by the collection's iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public Meals(Collection<? extends Meal> c) {
        super(c);
        Meals meals = this;
        noMeals(meals);
    }

    /**
     * Return A {@code Meals} list of "FIRST" class {code Meal} objects.
     *
     * @return A {@code Meals} list of "FIRST" class {code Meal} objects
     */
    public final Meals getFirstClassMeals() {
        return new Meals(filter(meal -> meal.getType() == FIRST));
    }

    /**
     * Return A {@code Meals} list of "ECONOMY" class {code Meal} objects.
     *
     * @return A {@code Meals} list of "ECONOMY" class {code Meal} objects
     */
    public final Meals getEconomyClassMeals() {
        return new Meals(filter(meal -> meal.getType() == ECONOMY));
    }

    /**
     * Get a {@code Meal} object by matching the start of its description,
     * ignoring case.
     *
     * @param pattern A string description
     * @return A {@code Meal} object or {@code null}
     */
    public final Meal getByDescriptionIgnoreCase(String pattern) {
        final String p = requireNotEmpty(pattern).trim().toLowerCase();
        return find(meal -> meal.getDescription().toLowerCase().startsWith(p));
    }

    /**
     * Get a {@code Meal} object by matching the start of its description.
     *
     * @param pattern A string description
     * @return A {@code Meal} object or {@code null}
     */
    public final Meal getByDescription(String pattern) {
        final String p = requireNotEmpty(pattern).trim();
        return find(meal -> meal.getDescription().startsWith(p));
    }

    /**
     * Prints each {@link Meal} in the list.
     */
    @Override
    public void print() {
        printlnLineSpaced("List of Meals:");
        super.print();
    }

    @Override
    public String toString() {
        return "Meals: " + super.toString();
    }

}
