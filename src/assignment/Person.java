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
import java.time.LocalDate;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Graham Fairweather
 */
public abstract class Person extends AccountableObject implements Serializable {

    private final String foreName;
    private final String surName;
    private final GenderTypes gender;
    private final LocalDate birthDate;
    private final AtomicReference<Address> address;
    private final AtomicReference<Phone> phone;
    private final PersonTypes type;

    /**
     * Allocates a <code>Person</code> object and initialises it.
     *
     * @param foreName The person's forename
     * @param surName The person's surname
     * @param gender The person's {@link GenderTypes}
     * @param birthDate The person's birth date
     * @param address The person's {@link Address}
     * @param phone The person's {@link Phone} number
     * @param type The person's {@link PersonTypes}
     */
    public Person(String foreName, String surName, GenderTypes gender, LocalDate birthDate, Address address, Phone phone, PersonTypes type) {
        this.foreName = requireNotEmpty(foreName);
        this.surName = requireNotEmpty(surName);
        this.gender = requireNonNull(gender);
        this.birthDate = requireNonNull(birthDate);
        this.address = new AtomicReference<>(requireNonNull(address));
        this.phone = new AtomicReference<>(requireNonNull(phone));
        this.type = requireNonNull(type);
    }

    /**
     * Gets the person's {@link Address}.
     *
     * @return {@link Address} object
     */
    public final Address getAddress() {
        return address.get();
    }

    /**
     * Sets the person's {@link Address}.
     *
     * @param address {@link Address} object
     */
    public final void setAddress(Address address) {
        this.address.set(requireNonNull(address));
        setModified();
    }

    /**
     * Gets the person's {@link Phone} number.
     *
     * @return {@link Phone} object
     */
    public final Phone getPhone() {
        return phone.get();
    }

    /**
     * Set the person's {@link Phone} number.
     *
     * @param phone The phone number
     */
    public final void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
        setModified();
    }

    /**
     * Gets the {@link PersonTypes} constant.
     *
     * @return {@link PersonTypes} constant
     */
    public final PersonTypes getType() {
        return type;
    }

    /**
     * Gets the {@link GenderTypes} constant.
     *
     * @return {@link GenderTypes} constant
     */
    public final GenderTypes getGender() {
        return gender;
    }

    /**
     * Gets the person's full name ({@code foreName} plus {@code surName}).
     *
     * @return The person's full name
     */
    public final String getName() {
        return foreName + " " + surName;
    }

    /**
     * Gets the person's {@code foreName}.
     *
     * @return The person's {@code foreName}.
     */
    public final String getForeName() {
        return foreName;
    }

    /**
     * Gets the person's {@code surName}.
     *
     * @return The person's {@code surName}.
     */
    public final String getSurName() {
        return surName;
    }

    /**
     * Gets the person's {@code birthDate}.
     *
     * @return The person's {@code birthDate}.
     */
    public final LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Person type: " + type);
        println("Person Forname: " + foreName);
        println("Person Surname: " + surName);
        println("Person Gender: " + gender);
        println("Person Birth date: " + birthDate);
        address.get().print();
        phone.get().print();
    }

    @Override
    public String toString() {
        return "Person{" + "foreName=" + foreName + ", surName=" + surName + ", gender=" + gender + ", birthDate=" + birthDate + ", address=" + address + ", phone=" + phone + ", type=" + type + "} " + super.toString();
    }

}
