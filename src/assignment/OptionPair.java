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

import static assignment.GeneralUtils.requireNotEmpty;
import java.lang.reflect.Method;
import java.util.Objects;
import javafx.util.Pair;

/**
 * A {@link Pair} containing the option message and the {@link Method} to
 * invoke.
 *
 * @author Graham Fairweather
 */
public final class OptionPair extends Pair<String, Method> {

    /**
     * Used to get the internal methods of {@link UserInterface}
     *
     * @param methodName A method name within {@link UserInterface}
     * @return The {@link Method}
     * @throws ElementNotFoundException If method not found
     */
    private static Method getMethod(String methodName) {
        try {
            requireNotEmpty(methodName);
            return UserInterface.class.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new ElementNotFoundException();
        }
    }

    /**
     * Allocates a <code>OptionPair</code> object and initialises it.
     *
     * @param key The string that the user must enter to choose this option
     * @param methodName The static method of {@link UserInterface} to call
     */
    public OptionPair(String key, String methodName) {
        this(requireNotEmpty(key), getMethod(methodName));
    }

    /**
     * Allocates a <code>OptionPair</code> object and initialises it.
     *
     * @param key The string that the user must enter to choose this option
     * @param method The static method to call
     */
    public OptionPair(String key, Method method) {
        super(requireNotEmpty(key), Objects.requireNonNull(method));
    }

}
