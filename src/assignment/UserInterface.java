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

import static assignment.Data.AIRLINES;
import static assignment.Data.AIRPORTS;
import static assignment.GeneralUtils.println;
import static assignment.GeneralUtils.printlnLineSpaced;
import static assignment.GeneralUtils.requireElementFound;
import static assignment.GeneralUtils.requireGreaterOrEqual;
import static assignment.ProductClassTypes.ECONOMY;
import static assignment.ProductClassTypes.FIRST;
import static assignment.UserInterface.airportsAction;
import static assignment.UserInterfaceUtils.confirmYesNo;
import static assignment.UserInterfaceUtils.getGender;
import static assignment.UserInterfaceUtils.getInput;
import static assignment.UserInterfaceUtils.getInputInt;
import static assignment.UserInterfaceUtils.getLocalDate;
import static assignment.UserInterfaceUtils.getLocalTime;
import static assignment.UserInterfaceUtils.getPrice;
import static assignment.UserInterfaceUtils.getProductClass;
import static assignment.UserInterfaceUtils.getRequireNotEmpty;
import static assignment.UserInterfaceUtils.runOptionMenus;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import static java.util.Objects.requireNonNull;

/**
 * A {@link Class} of static methods that implements the console based user
 * interface of the administration and booking system.
 *
 * @author Graham Fairweather
 */
public class UserInterface {

    /**
     * The airline currently chosen and used by the menu actions.
     */
    private static Airline airline;

    /**
     * List the aircraft of the currently chosen airline.
     */
    public static void listAircraft() {
        airline.getAircrafts().print();
    }

    /**
     * Aircraft action menu.
     */
    public static void aircraftAction() {
        OptionMap optionMap = new OptionMap();
        optionMap.add("0", "List aircraft", "listAircraft");
        optionMap.add("1", "Add aircraft", "addAircraft");
        optionMap.addBackAction();
        optionMap.addExitAction();
        if (runOptionMenus(optionMap)) {
            aircraftAction();
        }
    }

    /**
     * Prints all the totals of the supplied {@link Bookings} list.
     *
     * @param bookings The {@link Bookings}
     */
    private static void printTotals(Bookings bookings) {
        println();
        println("Bookings price: " + bookings.getPrice().getValue());
        println("Bookings costs: " + bookings.getCosts().getValue());
        println("Bookings profit: " + bookings.getProfit().getValue());
        println();
    }

    /**
     * List the bookings of the currently chosen airline.
     */
    public static void listBookings() {
        Bookings bookings = airline.getBookings();
        bookings.print();
        printTotals(bookings);
    }

    /**
     * List the completed bookings of the currently chosen airline.
     */
    public static void listBookingsCompleted() {
        Bookings bookings = airline.getBookings().closed();
        bookings.print();
        printTotals(bookings);
    }

    /**
     * List the confirmed bookings of the currently chosen airline.
     */
    public static void listBookingsConfirmed() {
        Bookings bookings = airline.getBookings().confirmed();
        bookings.print();
        printTotals(bookings);
    }

    /**
     * Make a booking with the currently chosen airline.
     */
    public static void makeBooking() {
        Flight flight;
        try {
            flight = chooseOpenFlight();
        } catch (RuntimeException ex) {
            return;
        }

        Seats firstClassSeats = flight.getSeats().getFirstClassSeatsAvailable();
        println("First class seats: " + firstClassSeats.size());
        Seats economyClassSeats = flight.getSeats().getEconomyClassSeatsAvailable();
        println("Economy class seats: " + economyClassSeats.size());
        if (firstClassSeats.size() <= 0 && economyClassSeats.size() <= 0) {
            printlnLineSpaced("Sorry, no seats available");
            return;
        }

        Seat seat = null;
        ProductClassTypes seatType = getProductClass();
        boolean running = true;
        while (running) {
            switch (seatType) {
                case FIRST:
                    if (firstClassSeats.size() <= 0) {
                        printlnLineSpaced("Sorry, no first class seats available");
                        if (confirmYesNo("Choose another seating class, (y)es or (n]o?")) {
                            seatType = getProductClass();
                            break;
                        }
                        return;
                    }
                    seat = firstClassSeats.get(0);
                    running = false;
                    break;
                case ECONOMY:
                    if (economyClassSeats.size() <= 0) {
                        printlnLineSpaced("Sorry, no economy seats available");
                        if (confirmYesNo("Choose another seating class, (y)es or (n]o?")) {
                            seatType = getProductClass();
                            break;
                        }
                        return;
                    }
                    seat = economyClassSeats.get(0);
                    running = false;
                    break;
                default:
                    throw new RuntimeException("missing ProductClassTypes");
            }
        }

        Passenger passenger;
        try {
            passenger = choosePassengerByName();
        } catch (RuntimeException ex) {
            return;
        }

        Meal meal;
        if (confirmYesNo("Do you want a mean? (y)es or (n)o")) {
            switch (seatType) {
                case FIRST:
                    try {
                        meal = chooseFirstClassMeal();
                    } catch (RuntimeException ex) {
                        return;
                    }
                    break;
                case ECONOMY:
                    try {
                        meal = chooseEconomyClassMeal();
                    } catch (RuntimeException ex) {
                        return;
                    }
                    break;
                default:
                    throw new RuntimeException("missing ProductClassTypes");
            }
        } else {
            switch (seatType) {
                case FIRST:
                    meal = airline.getMeals().getFirstClassMeals().getByDescriptionIgnoreCase("none");
                    break;
                case ECONOMY:
                    meal = airline.getMeals().getEconomyClassMeals().getByDescriptionIgnoreCase("none");
                    break;
                default:
                    throw new RuntimeException("missing ProductClassTypes");
            }
        }

        Booking booking = new Booking(flight, passenger, seat, meal);
        airline.getBookings().add(booking);
        booking.print();
    }

    /**
     * Get a first class meal by first matching description.
     *
     * @return The meal
     * @throws ElementNotFoundException If no meal found
     */
    public static Meal chooseFirstClassMeal() throws ElementNotFoundException {
        Meals meals = airline.getMeals().getFirstClassMeals();
        println();
        meals.print();
        String data = getInput("Meal description");
        Meal meal;
        try {
            meal = airline.getMeals().getByDescriptionIgnoreCase(data);
            requireElementFound(meal);
        } catch (ElementNotFoundException ex) {
            printlnLineSpaced("Meal not found: " + data);
            throw ex;
        } catch (IllegalArgumentException ex) {
            printlnLineSpaced(ex.getMessage() + ": " + data);
            throw ex;
        }
        printlnLineSpaced("Meal: " + meal.getDescription());
        return meal;
    }

    /**
     * Get an economy class meal by first matching description.
     *
     * @return The meal
     * @throws ElementNotFoundException If no meal found
     */
    public static Meal chooseEconomyClassMeal() throws ElementNotFoundException {
        Meals meals = airline.getMeals().getEconomyClassMeals();
        println();
        meals.print();
        String data = getInput("Meal description");
        Meal meal;
        try {
            meal = airline.getMeals().getByDescriptionIgnoreCase(data);
            requireElementFound(meal);
        } catch (ElementNotFoundException ex) {
            printlnLineSpaced("Meal not found: " + data);
            throw ex;
        } catch (IllegalArgumentException ex) {
            printlnLineSpaced(ex.getMessage() + ": " + data);
            throw ex;
        }
        printlnLineSpaced("Meal: " + meal.getDescription());
        return meal;
    }

    /**
     * Choose a flight by flight number.
     *
     * @return The first matching open flight
     * @throws ElementNotFoundException If no flight found
     */
    public static Flight chooseFlight() throws ElementNotFoundException {
        println();
        airline.getFlights().print();
        String data = getInput("Flight number");
        Flight flight;
        try {
            flight = airline.getFlights().getByFlightNumberIgnoreCase(data);
            requireElementFound(flight);
        } catch (ElementNotFoundException ex) {
            printlnLineSpaced("Flight not found: " + data);
            throw ex;
        } catch (IllegalArgumentException ex) {
            printlnLineSpaced(ex.getMessage() + ": " + data);
            throw ex;
        }
        printlnLineSpaced("Flight: " + flight.getFlightNumber());
        return flight;
    }

    /**
     * Choose a flight by flight number and print the operating totals.
     */
    public static void printFlightTotals() {
        try {
            Flight flight = chooseFlight();
            Bookings bookings = airline.getBookings().getBookingsByFlightNumber(flight.getFlightNumber());
            printTotals(bookings);
        } catch (ElementNotFoundException | IllegalArgumentException ex) {
            // Do nothing
        }
    }

    /**
     * Choose an open flight by flight number.
     *
     * @return The first matching open flight
     * @throws ElementNotFoundException If no flight found
     */
    public static Flight chooseOpenFlight() throws ElementNotFoundException {
        println();
        airline.getFlights().getOpen().print();
        String data = getInput("Flight number");
        Flight flight;
        try {
            flight = airline.getFlights().getOpen().getByFlightNumberIgnoreCase(data);
            requireElementFound(flight);
        } catch (ElementNotFoundException ex) {
            printlnLineSpaced("Flight not found: " + data);
            throw ex;
        } catch (IllegalArgumentException ex) {
            printlnLineSpaced(ex.getMessage() + ": " + data);
            throw ex;
        }
        printlnLineSpaced("Flight: " + flight.getFlightNumber());
        return flight;
    }

    /**
     * Bookings action menu.
     */
    public static void bookingsAction() {
        OptionMap optionMap = new OptionMap();
        optionMap.add("0", "List bookings", "listBookings");
        optionMap.add("1", "List completed bookings", "listBookingsCompleted");
        optionMap.add("2", "List confirmed bookings", "listBookingsConfirmed");
        optionMap.add("3", "Make bookings", "makeBooking");
        optionMap.addBackAction();
        optionMap.addExitAction();
        if (runOptionMenus(optionMap)) {
            bookingsAction();
        }
    }

    /**
     * Choose a passenger by first matching name.
     *
     * @return The passenger
     * @throws ElementNotFoundException If passenger not found
     */
    public static Passenger choosePassengerByName() throws ElementNotFoundException {
        String data = getInput("Passenger name");
        Passenger passenger;
        try {
            passenger = airline.getPassengers().getByNameIgnoreCase(data);
            requireElementFound(passenger);
        } catch (ElementNotFoundException ex) {
            printlnLineSpaced("Passenger not found: " + data);
            throw ex;
        } catch (IllegalArgumentException ex) {
            printlnLineSpaced(ex.getMessage() + ": " + data);
            throw ex;
        }
        printlnLineSpaced("Passenger: " + passenger.getName());
        return passenger;
    }

    /**
     *
     */
    public static void addPassenger() {
        try {
            String forName = getRequireNotEmpty("Forname");
            String surName = getRequireNotEmpty("Surname");
            GenderTypes gender = getGender();
            LocalDate birthDate = getLocalDate("Birth date (yyyy-MM-dd)");

            String address1 = getRequireNotEmpty("Address 1");
            String address2 = getRequireNotEmpty("Address 2");
            String postcode = getRequireNotEmpty("Postcode");
            String country = getRequireNotEmpty("country");
            Address address = new Address(address1, address2, postcode, country);

            String countryCode = getRequireNotEmpty("Phone country code");
            String areaCode = getRequireNotEmpty("Area code");
            String subscriberNumber = getRequireNotEmpty("Subscriber number");
            Phone phone = new Phone(countryCode, areaCode, subscriberNumber);

            Passenger passenger = new Passenger(forName, surName, gender, birthDate, address, phone);
            airline.getPassengers().add(passenger);
            printlnLineSpaced(passenger.toString());
        } catch (NullPointerException | IllegalArgumentException | DateTimeParseException ex) {
            printlnLineSpaced(ex.getMessage());
        }
    }

    /**
     * List the passengers of the currently chosen airline.
     */
    public static void listPassengers() {
        airline.getPassengers().print();
    }

    /**
     * Passengers action menu.
     */
    public static void passengersAction() {
        OptionMap optionMap = new OptionMap();
        optionMap.add("0", "List passengers", "listPassengers");
        optionMap.add("1", "Add passenger", "addPassenger");
        optionMap.addBackAction();
        optionMap.addExitAction();
        if (runOptionMenus(optionMap)) {
            passengersAction();
        }
    }

    /**
     * Choose an aircraft by the first matching name.
     *
     * @return The aircraft
     * @throws ElementNotFoundException If no aircraft found
     */
    public static Aircraft chooseAircraft() throws ElementNotFoundException {
        String data = getInput("Aircraft name");
        Aircraft aircraft;
        try {
            aircraft = airline.getAircrafts().getByNameIgnoreCase(data);
            requireElementFound(aircraft);
        } catch (ElementNotFoundException ex) {
            printlnLineSpaced("Aircraft not found: " + data);
            throw ex;
        } catch (IllegalArgumentException ex) {
            printlnLineSpaced(ex.getMessage() + ": " + data);
            throw ex;
        }
        printlnLineSpaced("Aircraft: " + aircraft.getName());
        return aircraft;
    }

    /**
     *
     */
    public static void addAircraft() {
        try {
            String aircraftName = getRequireNotEmpty("Aircraft name");
            String make = getRequireNotEmpty("Make");
            String model = getRequireNotEmpty("Model");
            int firstClassSeatCount = getInputInt("Number of first class seats");
            requireGreaterOrEqual(firstClassSeatCount, 0);
            int economyClassSeatCount = getInputInt("Number of first class seats");
            requireGreaterOrEqual(economyClassSeatCount, 0);
            AircraftOfPassengerType aircraft = new AircraftOfPassengerType(aircraftName, make, model, firstClassSeatCount, economyClassSeatCount);
            airline.getAircrafts().add(aircraft);
            printlnLineSpaced(aircraft.toString());
        } catch (NullPointerException | IllegalArgumentException ex) {
            printlnLineSpaced(ex.getMessage());
        }
    }

    /**
     *
     */
    public static void addEmployee() {
        try {
            String forName = getRequireNotEmpty("Forname");
            String surName = getRequireNotEmpty("Surname");
            GenderTypes gender = getGender();
            LocalDate birthDate = getLocalDate("Birth date (yyyy-MM-dd)");

            String address1 = getRequireNotEmpty("Address 1");
            String address2 = getRequireNotEmpty("Address 2");
            String postcode = getRequireNotEmpty("Postcode");
            String country = getRequireNotEmpty("country");
            Address address = new Address(address1, address2, postcode, country);

            String countryCode = getRequireNotEmpty("Phone country code");
            String areaCode = getRequireNotEmpty("Area code");
            String subscriberNumber = getRequireNotEmpty("Subscriber number");
            Phone phone = new Phone(countryCode, areaCode, subscriberNumber);

            LocalDate startDate = getLocalDate("Start date (yyyy-MM-dd)");

            Employee employee = new Employee(forName, surName, gender, birthDate, address, phone, startDate);
            airline.getEmployees().add(employee);
            printlnLineSpaced(employee.toString());
        } catch (NullPointerException | IllegalArgumentException | DateTimeParseException ex) {
            printlnLineSpaced(ex.getMessage());
        }
    }

    /**
     * List the employees of the currently chosen airline.
     */
    public static void listEmployees() {
        airline.getEmployees().print();
    }

    /**
     * Employees action menu.
     */
    public static void employeesAction() {
        OptionMap optionMap = new OptionMap();
        optionMap.add("0", "List employees", "listEmployees");
        optionMap.add("1", "Add employee", "addEmployee");
        optionMap.addBackAction();
        optionMap.addExitAction();
        if (runOptionMenus(optionMap)) {
            employeesAction();
        }
    }

    /**
     *
     */
    public static void addMeal() {
        try {
            ProductClassTypes productType = getProductClass();
            String description = getRequireNotEmpty("Description");
            Price price = getPrice("Price");

            Meal meal = new Meal(productType, description, price);
            airline.getMeals().add(meal);
            printlnLineSpaced(meal.toString());
        } catch (NullPointerException | IllegalArgumentException ex) {
            printlnLineSpaced(ex.getMessage());
        }
    }

    /**
     * List the meals of the currently chosen airline.
     */
    public static void listMeals() {
        airline.getMeals().print();
    }

    /**
     * Meals action menu.
     */
    public static void mealsAction() {
        OptionMap optionMap = new OptionMap();
        optionMap.add("0", "List meals", "listMeals");
        optionMap.add("1", "Add meal", "addMeal");
        optionMap.addBackAction();
        optionMap.addExitAction();
        if (runOptionMenus(optionMap)) {
            mealsAction();
        }
    }

    /**
     *
     */
    public static void addFlight() {
        try {
            String flightNumber = getRequireNotEmpty("Flight number");
            String aircraftName = getRequireNotEmpty("Aircraft name");
            Aircraft aircraft = airline.getAircrafts().getByNameIgnoreCase(aircraftName);
            requireNonNull(aircraft);
            LocalDateTime nowDateTime = LocalDateTime.now();
            LocalDate departureDate = getLocalDate("Departure date (yyyy-MM-dd)");
            int dateCompareTo = departureDate.compareTo(nowDateTime.toLocalDate());
            if (dateCompareTo < 0) {
                throw new IllegalArgumentException("departure date in the past");
            }
            LocalTime departureTime = getLocalTime("Departure date (HH:mm)");
            if (dateCompareTo == 0 && departureTime.compareTo(nowDateTime.toLocalTime()) < 0) {
                throw new IllegalArgumentException("departure time in the past");
            }
            LocalDateTime departureDateTime = departureDate.atTime(departureTime);

            String fromAirport = getRequireNotEmpty("From airport");
            Airport from = AIRPORTS.getByNameIgnoreCase(fromAirport);
            requireNonNull(from);

            String toAirport = getRequireNotEmpty("To airport");
            Airport to = AIRPORTS.getByNameIgnoreCase(toAirport);
            requireNonNull(to);
            if (to.equals(from)) {
                throw new IllegalArgumentException("from and to are the same");
            }

            int minutesDuration = getInputInt("Duration (minutes)");
            requireGreaterOrEqual(minutesDuration, 1);
            Duration duration = Duration.ofMinutes(minutesDuration);

            Price firstClassPrice = getPrice("First class price");
            requireGreaterOrEqual(firstClassPrice.getBigDecimal(), new BigDecimal("1.0"));

            Price economyClassPrice = getPrice("Economy class price");
            requireGreaterOrEqual(economyClassPrice.getBigDecimal(), new BigDecimal("1.0"));

            Flight flight = new Flight(flightNumber, airline, aircraft, departureDateTime, from, to, duration, firstClassPrice, economyClassPrice);
            airline.getFlights().add(flight);
            printlnLineSpaced(flight.toString());
        } catch (NullPointerException | IllegalArgumentException | DateTimeParseException ex) {
            printlnLineSpaced(ex.getMessage());
        }
    }

    /**
     * List the closed flights of the currently chosen airline.
     */
    public static void listFlightsClosed() {
        airline.getFlights().getClosed().print();
    }

    /**
     * List the departed flights of the currently chosen airline.
     */
    public static void listFlightsDeparted() {
        airline.getFlights().getDeparted().print();
    }

    /**
     * List the open flights of the currently chosen airline.
     */
    public static void listFlightsOpen() {
        airline.getFlights().getOpen().print();
    }

    /**
     * Flights action menu.
     */
    public static void flightsAction() {
        OptionMap optionMap = new OptionMap();
        optionMap.add("0", "List closed flights", "listFlightsClosed");
        optionMap.add("1", "List departed flights", "listFlightsDeparted");
        optionMap.add("2", "List open flights", "listFlightsOpen");
        optionMap.add("3", "Add flight", "addFlight");
        optionMap.add("4", "Print flight", "printFlightTotals");
        optionMap.addBackAction();
        optionMap.addExitAction();
        if (runOptionMenus(optionMap)) {
            flightsAction();
        }
    }

    /**
     * Economy action menu.
     */
    public static void economyAction() {
        OptionMap optionMap = new OptionMap();
        optionMap.addBackAction();
        optionMap.addExitAction();
        if (runOptionMenus(optionMap)) {
            economyAction();
        }
    }

    /**
     * The currently choosen airline action menu.
     */
    public static void mainAction() {
        OptionMap optionMap = new OptionMap();
        optionMap.add("0", "Employees", "employeesAction");
        optionMap.add("1", "Aircraft", "aircraftAction");
        optionMap.add("2", "Meals", "mealsAction");
        optionMap.add("3", "Flights", "flightsAction");
        optionMap.add("4", "Passengers", "passengersAction");
        optionMap.add("5", "Bookings", "bookingsAction");
        optionMap.add("6", "Economy", "economyAction");
        optionMap.addBackAction();
        optionMap.addExitAction();
        if (runOptionMenus(optionMap)) {
            mainAction();
        }
        airline = null;
    }

    /**
     *
     */
    public static void chooseAirline() {
        String data = getInput("Airline name");
        try {
            airline = AIRLINES.getByNameIgnoreCase(data);
            requireElementFound(airline);
            println();
            println("Airline: " + airline.getName());
            mainAction();
        } catch (ElementNotFoundException ex) {
            printlnLineSpaced("Company not found: " + data);
        } catch (IllegalArgumentException ex) {
            printlnLineSpaced(ex.getMessage() + ": " + data);
        }
    }

    /**
     *
     */
    public static void addAirline() {
        try {
            String airlineName = getRequireNotEmpty("Company name");
            String address1 = getRequireNotEmpty("Address1");
            String address2 = getRequireNotEmpty("Address2");
            String postcode = getRequireNotEmpty("Postcode");
            String country = getRequireNotEmpty("country");
            Address address = new Address(address1, address2, postcode, country);
            String countryCode = getRequireNotEmpty("Country code");
            String areaCode = getRequireNotEmpty("Area code");
            String subscriberNumber = getRequireNotEmpty("Subscriber number");
            Phone phone = new Phone(countryCode, areaCode, subscriberNumber);
            Airline airline1 = new Airline(airlineName, address, phone);
            AIRLINES.add(airline1);
            printlnLineSpaced(airline1.toString());
        } catch (NullPointerException | IllegalArgumentException ex) {
            printlnLineSpaced(ex.getMessage());
        }
    }

    /**
     * List the airports.
     */
    public static void listAirports() {
        AIRPORTS.print();
    }

    /**
     * Airports action menu.
     */
    public static void airportsAction() {
        OptionMap optionMap = new OptionMap();
        optionMap.add("0", "List airports", "listAirports");
        optionMap.add("1", "Add airports", "addAirport");
        optionMap.addBackAction();
        optionMap.addExitAction();
        if (runOptionMenus(optionMap)) {
            airportsAction();
        }
    }

    /**
     *
     */
    public static void addAirport() {
        try {
            String airportName = getRequireNotEmpty("Airport name");
            String address1 = getRequireNotEmpty("Address1");
            String address2 = getRequireNotEmpty("Address2");
            String postcode = getRequireNotEmpty("Postcode");
            String country = getRequireNotEmpty("country");
            Address address = new Address(address1, address2, postcode, country);
            String countryCode = getRequireNotEmpty("Country code");
            String areaCode = getRequireNotEmpty("Area code");
            String subscriberNumber = getRequireNotEmpty("Subscriber number");
            Phone phone = new Phone(countryCode, areaCode, subscriberNumber);
            Airport airport = new Airport(airportName, address, phone);
            AIRPORTS.add(airport);
            printlnLineSpaced(airport.toString());
        } catch (NullPointerException | IllegalArgumentException ex) {
            printlnLineSpaced(ex.getMessage());
        }
    }

    /**
     * List the airlines.
     */
    public static void listAirlines() {
        AIRLINES.print();
    }

    /**
     * Airlines action menu.
     */
    public static void airlinesAction() {
        OptionMap optionMap = new OptionMap();
        optionMap.add("0", "List airlines", "listAirlines");
        optionMap.add("1", "Add airline", "addAirline");
        optionMap.add("2", "Manage airline", "chooseAirline");
        optionMap.addBackAction();
        optionMap.addExitAction();
        if (runOptionMenus(optionMap)) {
            airlinesAction();
        }
    }

    /**
     * The entry menu into the user interface.
     */
    @SuppressWarnings("InfiniteRecursion")
    public static void startMenu() {
        airline = null;
        OptionMap optionMap = new OptionMap();
        optionMap.add("0", "Airports", "airportsAction");
        optionMap.add("1", "Airlines", "airlinesAction");
        optionMap.addExitAction();
        runOptionMenus(optionMap);
        startMenu();
    }

    private UserInterface() {
    }

}
