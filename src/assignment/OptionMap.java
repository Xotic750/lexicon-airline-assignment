/*
 * The MIT License
 *
 * Copyright 2016 graham.
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
import static java.lang.System.exit;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * A {@link LinkedHashMap} using the string pattern for matching as the key
 * value, and {@link OptionPair} as the related value.
 *
 * @author Graham Fairweather
 */
public final class OptionMap extends LinkedHashMap<String, OptionPair> {

    /**
     * For use with {@link #getMethod(java.lang.String)}.
     *
     * @return {@code true}
     */
    public static boolean backAction() {
        println("Back");
        return true;
    }

    /**
     * For use with {@link #getMethod(java.lang.String)}.
     */
    public static void exitAction() {
        println("Exit");
        exit(0);
    }

    /**
     * Used to get the internal methods {@link #addBackAction} and
     * {@link #addExitAction}
     *
     * @param methodName The name of the method to get
     * @return The {@link Method}
     * @throws ElementNotFoundException If method not found
     */
    public static Method getMethod(String methodName) {
        try {
            requireNotEmpty(methodName);
            return OptionMap.class.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new ElementNotFoundException();
        }
    }

    /**
     * Adds a back action to the menu options.
     */
    public final void addBackAction() {
        this.put("b", new OptionPair("Back", getMethod("backAction")));
    }

    /**
     * Adds an exit action to the menu options.
     */
    public final void addExitAction() {
        this.put("x", new OptionPair("Exit", getMethod("exitAction")));
    }

    /**
     * Adds the specified action to the menu options.
     *
     * @param pattern The string that the user must enter to choose this option
     * @param message The message that will be displayed with the pattern
     * @param methodName The static method of {@link UserInterface} to call
     */
    public final void add(String pattern, String message, String methodName) {
        this.put(requireNotEmpty(pattern), new OptionPair(message, methodName));
    }

}
