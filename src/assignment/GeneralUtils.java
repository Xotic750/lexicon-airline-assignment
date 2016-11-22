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

import static assignment.GeneralUtils.isDouble;
import static java.lang.Double.parseDouble;
import static java.lang.System.out;
import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_UP;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Graham Fairweather
 */
public class GeneralUtils {

    /**
     *
     * Checks whether the given String {@code value} is a parsable double.
     *
     * @param value The string to test
     * @return <code>true</code> if parses, otherwise <code>false</code>
     */
    public static boolean isDouble(String value) {
        try {
            parseDouble(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Checks that the specified object reference is not {@code null}. This
     * method is designed primarily for doing parameter validation in
     * {@link AbstractNoNullList} methods and constructors, as demonstrated
     * below:
     * <blockquote><pre>
     * public Foo(Bar bar) {
     *     this.bar = requireElementFound(bar);
     * }
     * </pre></blockquote>
     *
     * @param obj the object reference to check for nullity
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T requireElementFound(T obj) throws ElementNotFoundException {
        if (isNull(obj)) {
            throw new ElementNotFoundException();
        }
        return obj;
    }

    /**
     * Checks that the specified string reference is not {@code null} and after
     * {@link java.lang.String#trim}., that it is not empty
     * {@link java.lang.String#isEmpty}. This method is designed primarily for
     * doing parameter validation in methods and constructors, as demonstrated
     * below:
     * <blockquote><pre>
     * public Foo(string bar) {
     *     this.bar = requireNotEmpty(bar);
     * }
     * </pre></blockquote>
     *
     * @param str the string reference to check for nullity
     * @return a trimmed copy of {@code str} if not {@code null} and not empty
     * @throws NullPointerException if {@code str} is {@code null}
     * @throws IllegalArgumentException if the trimmed {@code str} is empty
     */
    public static String requireNotEmpty(String str) {
        requireNonNull(str, "value can not be null");
        str = str.trim();
        if (str.isEmpty()) {
            throw new IllegalArgumentException("value can not be empty");
        }
        return str;
    }

    /**
     * Return {@code value} if the value is greater than or equals {@code min}
     * value.
     *
     * @param value the integer to be tested
     * @param min used in comparison to {@code value}
     * @return {@code value}
     * @throws IllegalArgumentException if supplied value is less than min
     * @throws NullPointerException if the supplied value is null
     */
    public static int requireGreaterOrEqual(int value, int min) {
        requireNonNull(value, "value can not be null");
        requireNonNull(min, "min can not be null");
        if (value < min) {
            throw new IllegalArgumentException("value can not be less than " + min);
        }
        return value;
    }

    /**
     * Return {@code value} if the value is greater than or equals {@code min}
     * value.
     *
     * @param value the double to be tested
     * @param min used in comparison to {@code value}
     * @return {@code value}
     * @throws IllegalArgumentException if supplied value is less than min
     * @throws NullPointerException if the supplied value is null
     */
    public static double requireGreaterOrEqual(double value, double min) {
        requireNonNull(value, "value can not be null");
        requireNonNull(min, "min can not be null");
        if (value < min) {
            throw new IllegalArgumentException("value can not be less than " + min);
        }
        return value;
    }

    /**
     * Return {@code value} if the value is greater than or equals {@code min}
     * value.
     *
     * @param value the {@link BigDecimal} to be tested
     * @param min used in comparison to {@code value}
     * @return {@code value}
     * @throws IllegalArgumentException if supplied value is less than min
     * @throws NullPointerException if the supplied value is null
     */
    public static BigDecimal requireGreaterOrEqual(BigDecimal value, BigDecimal min) {
        requireNonNull(value, "value can not be null");
        requireNonNull(min, "min can not be null");
        if (value.compareTo(min) < 0) {
            throw new IllegalArgumentException("value can not be less than " + min);
        }
        return value;
    }

    /**
     * Checks to make sure that the string {@code value} can be parsed as a
     * double and can construct a {@link java.math.BigDecimal}. The returned
     * value will be rounded up to 2 dp.
     *
     * @param value A string representation of a double
     * @return A formatted representation of {@code value}, rounded up to 2 dp
     * @throws IllegalArgumentException if {@code value} is !{@link isDouble}
     * @throws NullPointerException if the {@code value} is null
     * @throws NumberFormatException if {@code value} is not a valid
     * representation
     */
    public static String requireBigDecimalParseable(String value) {
        requireNonNull(value, "value can not be null");
        if (!isDouble(value)) {
            throw new IllegalArgumentException("value must be parseable as a double");
        }
        return new BigDecimal(value).setScale(2, ROUND_UP).toString();
    }

    /**
     * Print an object. The string produced by the
     * <code>{@link java.lang.String#valueOf(Object)}</code> method is
     * translated into bytes according to the platform's default character
     * encoding, and these bytes are written in exactly the manner of the
     * <code>{@link java.io.PrintStream#write(int)}</code> method.
     *
     * @param <T> generic support
     * @param obj The <code>Object</code> to be printed
     * @see java.lang.Object#toString()
     */
    public static <T> void print(T obj) {
        out.print(obj);
    }

    /**
     * Print an Object and then terminate the line. This method calls at first
     * String.valueOf(x) to get the printed object's string value, then behaves
     * as though it invokes
     * <code>{@link java.io.PrintStream#print(String)}</code> and then
     * <code>{@link java.io.PrintStream#println()}</code>.
     *
     * @param <T> Generic support
     * @param obj The <code>Object</code> to be printed.
     */
    public static <T> void println(T obj) {
        out.println(obj);
    }

    /**
     * Terminates the current line by writing the line separator string. The
     * line separator string is defined by the system property
     * <code>line.separator</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     */
    public static void println() {
        out.println();
    }

    /**
     * Prints a String "b: Back" and then terminate the line.
     */
    public static void printlnBackOption() {
        println("b: Back");
    }

    /**
     * Prints a String "x: Exit" and then terminate the line.
     */
    public static void printlnExitOption() {
        println("x: Exit");
    }

    /**
     * Prints a String and then terminate the line. This method behaves as
     * though it invokes <code>{@link #println()}</code> and then
     * <code>{@link #println(java.lang.Object)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param <T> Generic support
     * @param obj The <code>String</code> to be printed.
     */
    public static <T> void printlnLineSpaced(T obj) {
        println();
        println(obj);
        println();
    }

    private GeneralUtils() {
    }

}
