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

import java.io.Serializable;
import java.time.LocalDateTime;
import static java.util.Objects.requireNonNull;
import java.util.UUID;
import static java.util.UUID.randomUUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An abstract object that is able to provide a UUID to its children, and also
 * perform accounting in the form maintaining a creation and modified date,
 * along with a record of the user that performed them.
 *
 * @author Graham Fairweather
 * @see java.util.UUID
 */
public abstract class AccountableObject implements Serializable {

    private static final UUID NOBODY = randomUUID();

    /**
     * Tests if the supplied UUID is the {@code ROOT} UUID.
     *
     * @param userId The UUID to test
     * @return <code>true</code> if the supplied UUDI is {@code NOBODY},
     * otherwise <code>false</code>
     */
    public static final boolean isNobodyId(UUID userId) {
        return userId.equals(NOBODY);
    }

    /**
     * Returns the {@code String} UUID of {@code NOBODY}.
     *
     * @return The {@code String} UUID of {@code NOBODY}
     */
    public static final UUID getNobodyId() {
        return NOBODY;
    }

    private final UUID id;
    private final LocalDateTime created;
    private final UUID createdUserId;
    private AtomicReference<LocalDateTime> modified;
    private AtomicReference<UUID> modifiedUserId;

    /**
     * Allocates an <code>AccountableObject</code> object and initialises it
     * using a userId of {@code NOBODY}
     */
    public AccountableObject() {
        this(NOBODY);
    }

    /**
     * Allocates an <code>AccountableObject</code> object and initialises it
     * using the supplied {@code userId}
     *
     * @param userId The unique id of the user
     */
    public AccountableObject(UUID userId) {
        this.created = LocalDateTime.now();
        this.createdUserId = requireNonNull(userId);
        this.modified = new AtomicReference<>(null);
        this.modifiedUserId = new AtomicReference<>(requireNonNull(userId));
        this.id = randomUUID();
    }

    /**
     * Returns the {@code String} UUID.
     *
     * @return The {@code String} UUID
     */
    public final UUID getId() {
        return id;
    }

    /**
     * The class <code>Date</code> representing a specific instant in time, with
     * millisecond precision. The date that this object was created.
     *
     * @return A specific instant in time
     */
    public final LocalDateTime getCreated() {
        return created;
    }

    /**
     * The class <code>Date</code> representing a specific instant in time, with
     * millisecond precision. The date that this object was modified.
     *
     * @return A specific instant in time
     */
    public final LocalDateTime getModified() {
        return modified.get();
    }

    /**
     * Sets the date that this object was modified to now.
     */
    public final void setModified() {
        this.modified.set(LocalDateTime.now());
    }

    /**
     * Sets the date that this object was modified to now. It also takes a
     * {@code uderId} to track who made the modification.
     *
     * @param userId The unique id of the user
     */
    public final void setModified(UUID userId) {
        this.modifiedUserId.set(requireNonNull(userId));
        setModified();
    }

    /**
     * Get the unique id of the user that created this object.
     *
     * @return The unique id of the user that created this object
     */
    public final UUID getCreatedUserId() {
        return createdUserId;
    }

    /**
     * Get the unique id of the user that modified this object.
     *
     * @return The unique id of the user that modified this object
     */
    public final UUID getModifiedUserId() {
        return modifiedUserId.get();
    }

    /**
     * Prints this object.
     */
    public abstract void print();

    @Override
    public String toString() {
        return "AccountableObject{" + "id=" + id + ", created=" + created + ", modified=" + modified + ", createdUserId=" + createdUserId + ", modifiedUserId=" + modifiedUserId + '}';
    }

}
