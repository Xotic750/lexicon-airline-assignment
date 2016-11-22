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

import static assignment.EmployeeStatusTypes.HIRED;
import static assignment.EmployeeStatusTypes.RELEASED;
import static assignment.PersonTypes.EMPLOYEE;
import static assignment.GeneralUtils.println;
import static assignment.GeneralUtils.requireNotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Extended {@link Person} to hold employee information.
 *
 * @author Graham Fairweather
 */
public class Employee extends Person implements Serializable {

    private final LocalDate startDate;
    private final AtomicReference<LocalDate> endDate;
    private final AtomicReference<EmployeeStatusTypes> status;
    private final AtomicReference<String> login;

    /**
     * Allocates an <code>Employee</code> object and initialises it.
     *
     * @param foreName The employee's forename
     * @param surName The employee's surname
     * @param gender The employee's {@link GenderTypes}
     * @param birthDate The employee's birth date
     * @param address The employee's {@link Address}
     * @param phone The employee's {@link Phone} number
     * @param startDate The employee's start date
     */
    public Employee(String foreName, String surName, GenderTypes gender, LocalDate birthDate, Address address, Phone phone, LocalDate startDate) {
        super(foreName, surName, gender, birthDate, address, phone, EMPLOYEE);
        this.login = new AtomicReference<>((surName + "." + foreName.charAt(0)).toLowerCase());
        this.startDate = requireNonNull(startDate);
        this.endDate = new AtomicReference<>(null);
        this.status = new AtomicReference<>(HIRED);
    }

    /**
     * Get the employee's employment status.
     *
     * @return Their employment status
     */
    public final EmployeeStatusTypes getStatus() {
        return status.get();
    }

    /**
     *
     * @param status
     */
    private void setStatus(EmployeeStatusTypes status) {
        this.status.set(requireNonNull(status));
        setModified();
    }

    /**
     * Get the date the employee ceased employment with the company.
     *
     * @return The date
     */
    public final LocalDate getEndDate() {
        return endDate.get();
    }

    /**
     * Set he date the employee ceased employment with the company.
     *
     * @param endDate The date
     */
    public final void setEndDate(LocalDate endDate) {
        this.endDate.set(requireNonNull(endDate));
        setStatus(RELEASED);
        setModified();
    }

    /**
     * Get the employee's employment start date.
     *
     * @return The date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Get the employee's login to the airline system.
     *
     * @return The login
     */
    public final String getLogin() {
        return login.get();
    }

    /**
     * Set the employee's login to the airline system.
     *
     * @param login The login
     */
    public final void setLogin(String login) {
        this.login.set(requireNotEmpty(login).toLowerCase());
        setModified();
    }

    /**
     * Prints this object.
     */
    @Override
    public void print() {
        println("Employee UUID: " + getId());
        super.print();
        println("Employee Start date: " + startDate);
        println("Employee End date: " + endDate);
        println("Employee status: " + status);
        println("Employee Login: " + login);
    }

    @Override
    public String toString() {
        return "Employee{" + "login=" + login + "} " + super.toString();
    }

}
