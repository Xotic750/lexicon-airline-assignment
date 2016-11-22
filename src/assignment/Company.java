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
public abstract class Company extends AccountableObject implements Serializable {

    private final String name;
    private final AtomicReference<Address> address;
    private final AtomicReference<Phone> phone;
    private final CompanyTypes type;

    /**
     * Allocates a <code>Company</code> object and initialises it.
     *
     * @param name
     * @param address
     * @param phone
     * @param type
     */
    public Company(String name, Address address, Phone phone, CompanyTypes type) {
        this.name = requireNotEmpty(name);
        this.address = new AtomicReference<>(address);
        this.phone = new AtomicReference<>(phone);
        this.type = requireNonNull(type);
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
    public final Address getAddress() {
        return address.get();
    }

    /**
     *
     * @param address
     */
    public final void setAddress(Address address) {
        this.address.set(requireNonNull(address));
        setModified();
    }

    /**
     *
     * @return
     */
    public final Phone getPhone() {
        return phone.get();
    }

    /**
     *
     * @param phone
     */
    public final void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
        setModified();
    }

    /**
     *
     * @return
     */
    public final CompanyTypes getType() {
        return type;
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Company type: " + type);
        println("Company Name: " + name);
        address.get().print();
        phone.get().print();
    }

    @Override
    public String toString() {
        return "Company{" + "name=" + name + ", address=" + address + ", phone=" + phone + ", type=" + type + "} " + super.toString();
    }

}
