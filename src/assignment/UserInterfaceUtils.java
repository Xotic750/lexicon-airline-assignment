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

import static assignment.GenderTypes.FEMALE;
import static assignment.GenderTypes.MALE;
import static assignment.GenderTypes.UNKNOWN;
import static assignment.GeneralUtils.print;
import static assignment.GeneralUtils.println;
import static assignment.GeneralUtils.requireBigDecimalParseable;
import static assignment.GeneralUtils.requireNotEmpty;
import static assignment.ProductClassTypes.ECONOMY;
import static assignment.ProductClassTypes.FIRST;
import static assignment.UserInterfaceUtils.getInput;
import static assignment.UserInterfaceUtils.getInputLowerCase;
import static assignment.UserInterfaceUtils.getLocalDate;
import static assignment.UserInterfaceUtils.getRequireNotEmpty;
import static java.lang.Integer.parseInt;
import static java.lang.System.in;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Scanner;

/**
 *
 * @author Graham Fairweather
 */
public class UserInterfaceUtils {

    /**
     * Constructs a new <code>Scanner</code> that produces values scanned from
     * the specified input stream. Bytes from the stream are converted into
     * characters using the underlying platform's
     * {@linkplain java.nio.charset.Charset#defaultCharset() default charset}.
     *
     * The input stream to be scanned is
     * <code>{@link java.lang.System#in}</code>
     *
     * The text provided will have ": " appended, and will be displayed before
     * the input cursor of {@link java.util.Scanner}
     *
     * @param text Will be printed directly before the input cursor
     * @return the current line, excluding any line separator at the end.
     */
    public static String getInput(String text) {
        print(text + ": ");
        return new Scanner(in).nextLine();
    }

    /**
     * Just like {@link getInput} except the returned input is converted to
     * lower case.
     *
     * @param text The text message to display
     * @return the current line in lower case, excluding any line separator at
     * the end.
     */
    public static String getInputLowerCase(String text) {
        return getInput(text).toLowerCase();
    }

    /**
     * Just like {@link getInput} except the returned input is converted to
     * upper case.
     *
     * @param text The text message to display
     * @return The current line in upper case, excluding any line separator at
     * the end.
     */
    public static String getInputUpperCase(String text) {
        return getInput(text).toUpperCase();
    }

    /**
     * Just like {@link getInputLowerCase} except the text use is "Choice", and
     * makes this ideal for choosing menu options.
     *
     * @return the current line in lower case, excluding any line separator at
     * the end.
     */
    public static String getChoice() {
        return getInputLowerCase("Choice");
    }

    /**
     * Just like {@link getInput} apart from an exception will be thrown if the
     * input is empty. as determined by
     * {@link GeneralUtils#requireNotEmpty(java.lang.String)}
     *
     * @param text The text message to display
     * @return the current line, excluding any line separator at the end.
     * @throws IllegalArgumentException if no line was empty
     */
    public static String getRequireNotEmpty(String text) {
        return requireNotEmpty(getInput(text));
    }

    /**
     * Just like {@link getRequireNotEmpty} apart from the returned value is
     * parsed to an integer value.
     *
     * @param text The text message to display
     * @return the current line parsed as an integer.
     * @throws IllegalArgumentException if no line was empty
     * @throws NumberFormatException if the line could not be integer parsed
     */
    public static int getInputInt(String text) {
        return parseInt(getRequireNotEmpty(text), 10);
    }

    /**
     * Just like {@link getRequireNotEmpty} apart from the returned value is
     * parsed to a {@link java.time.LocalDate}.
     *
     * @param text IS0 8601 formatted date, yyyy-MM-dd
     * @return the current line parsed as a LocalDate.
     * @throws IllegalArgumentException if no line was empty
     * @throws DateTimeParseException if the line could not be parsed
     */
    public static LocalDate getLocalDate(String text) {
        return LocalDate.parse(getRequireNotEmpty(text), ISO_LOCAL_DATE);
    }

    /**
     * Just like {@link getRequireNotEmpty} apart from the returned value is
     * parsed to a {@link java.time.LocalTime}.
     *
     * @param text IS0 8601 formatted time, HH:mm:ss
     * @return the current line parsed as a LocalTime.
     * @throws IllegalArgumentException if no line was empty
     * @throws DateTimeParseException if the line could not be parsed
     */
    public static LocalTime getLocalTime(String text) {
        return LocalTime.parse(getRequireNotEmpty(text), ISO_LOCAL_TIME);
    }

    /**
     * Just like {@link getLocalDate} apart from the returned value is parsed to
     * a {@link java.time.LocalDateTime}.
     *
     * @param text IS0 8601 formatted date, yyyy-MM-dd'T'HH:mm:ss (T is the
     * separator)
     * @return the current line parsed as an integer.
     * @throws IllegalArgumentException if no line was empty
     * @throws DateTimeParseException if the line could not be parsed
     */
    public static LocalDateTime getLocalDateTime(String text) {
        return LocalDateTime.parse(getRequireNotEmpty(text), ISO_LOCAL_DATE_TIME);
    }

    /**
     * Get a {@link Price} from input.
     *
     * @param text IS0 8601 formatted date, yyyy-MM-dd'T'HH:mm:ss (T is the
     * separator)
     * @return the current line parsed as a {@link Price}
     * @throws IllegalArgumentException if {@code value} is
     * !{@link GeneralUtils#isDouble(java.lang.String)}
     * @throws NullPointerException if the {@code value} is null
     * @throws NumberFormatException if {@code value} is not a valid
     */
    public static Price getPrice(String text) {
        return new Price(requireBigDecimalParseable(text));
    }

    /**
     * Just like {@link getInputLowerCase} except the returned input is
     * converted to {@link GenderTypes}.
     *
     * @return The current line as a {@link GenderTypes}
     * @throws IllegalArgumentException if option is invalid
     */
    public static GenderTypes getGender() {
        GenderTypes gender;
        switch (getInputLowerCase("Gender (M)ale/(F)emale/(U)nknown")) {
            case "m":
                gender = MALE;
                break;
            case "f":
                gender = FEMALE;
                break;
            case "u":
                gender = UNKNOWN;
                break;
            default:
                throw new IllegalArgumentException("Invalid option");
        }
        return gender;
    }

    /**
     * Just like {@link getInputLowerCase} except the returned input is
     * converted to {@link ProductClassTypes}.
     *
     * @return The current line as a {@link ProductClassTypes}
     * @throws IllegalArgumentException if option is invalid
     */
    public static ProductClassTypes getProductClass() {
        ProductClassTypes productType;
        switch (getInputLowerCase("Class (F)irst or (E)conomy")) {
            case "f":
                productType = FIRST;
                break;
            case "e":
                productType = ECONOMY;
                break;
            default:
                throw new IllegalArgumentException("Invalid option");
        }
        return productType;
    }

    /**
     * Build and run an actions menu.
     *
     * @param optionMap The map of the option for this actions menu
     * @return {@code true} if failed option or remain in this menu, otherwise
     * {@code false} to indicate back
     */
    public static boolean runOptionMenus(OptionMap optionMap) {
        println();
        try {
            optionMap.entrySet().stream().forEach(entry -> println(entry.getKey() + ": " + entry.getValue().getKey()));
            String choice = getChoice();
            OptionPair pair = requireNonNull(optionMap.get(choice));
            Method method = pair.getValue();
            return Objects.isNull(method.invoke(null));
        } catch (InvocationTargetException ex) {
            throw new RuntimeException("PLONKER ALERT!", ex);
        } catch (NullPointerException | IllegalAccessException | IllegalArgumentException ex) {
            // Do nothing
        }
        return true;
    }

    /**
     * Get a yes or no confirmation.
     *
     * @param text The map of the option for this actions menu
     * @return {@code true} if "(y)es", {@code false} if "(n)o"
     */
    public static boolean confirmYesNo(String text) {
        while (true) {
            switch (getInputLowerCase(text)) {
                case "y":
                case "yes":
                    return true;
                case "n":
                case "no":
                    return false;
                default:
            }
        }
    }

    private UserInterfaceUtils() {
    }

}
