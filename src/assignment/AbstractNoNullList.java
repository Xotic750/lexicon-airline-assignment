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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toList;

/**
 * Resizable-array implementation of the <tt>List</tt> interface. Implements all
 * optional list operations, and permits all elements, excluding
 * <tt>null</tt>.
 *
 * @author Graham Fairweather
 * @param <T> Generic support
 * @see Collection
 * @see List
 */
public abstract class AbstractNoNullList<T extends AccountableObject> extends AccountableObject implements List<T>, Serializable {

    private final List<T> syncBackingList;

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity is
     * negative
     */
    public AbstractNoNullList(int initialCapacity) {
        syncBackingList = Collections.synchronizedList(new ArrayList<>(initialCapacity));
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public AbstractNoNullList() {
        this(10);
    }

    /**
     * Constructs a list containing the elements of the specified collection, in
     * the order they are returned by the collection's iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public AbstractNoNullList(Collection<? extends T> c) {
        syncBackingList = Collections.synchronizedList(new ArrayList<>(c));
    }

    /**
     * Prints each {@link AccountableObject} in the list.
     */
    @Override
    public void print() {
        synchronized (syncBackingList) {
            syncBackingList.stream().forEach(element -> {
                element.print();
                println();
            });
        }
    }

    /**
     * The find() method returns a value of the first element in the List that
     * satisfies the provided predicate. Otherwise null is returned.
     *
     * @param predicate Represents a predicate (boolean-valued function) of one
     * argument.
     * @return A value in the array if an element passes the test; otherwise,
     * null.
     */
    public T find(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        synchronized (syncBackingList) {
            for (final T element : syncBackingList) {
                if (predicate.test(element)) {
                    return element;
                }
            }
        }
        return null;
    }

    /**
     * The findIndex() method returns an index of the first element in the List
     * that satisfies the provided predicate. Otherwise -1 is returned.
     *
     * @param predicate Represents a predicate (boolean-valued function) of one
     * argument.
     * @return An index in the List if an element passes the test; otherwise,
     * -1.
     */
    public int findIndex(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        synchronized (syncBackingList) {
            for (int index = 0; index < syncBackingList.size(); index++) {
                if (predicate.test(syncBackingList.get(index))) {
                    return index;
                }
            }
        }
        return -1;
    }

    /**
     * Returns a view of the portion of this list that test true by the provided
     * predicate.
     *
     * @param predicate Represents a predicate (boolean-valued function) of one
     * argument.
     * @return a view of the specified range within this list
     */
    public List<T> filter(Predicate<? super T> predicate) {
        synchronized (syncBackingList) {
            return syncBackingList.stream().filter(predicate).collect(toList());
        }
    }

    /**
     * Returns the number of elements in this list. If this list contains more
     * than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return syncBackingList.size();
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * @return <tt>true</tt> if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return syncBackingList.isEmpty();
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified element. More
     * formally, returns <tt>true</tt> if and only if this list contains at
     * least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     * @throws ClassCastException if the type of the specified element is
     * incompatible with this list
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     * list does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean contains(Object o) {
        requireNonNull(o);
        return syncBackingList.contains(o);
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<T> iterator() {
        return syncBackingList.iterator();
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element).
     *
     * <p>
     * The returned array will be "safe" in that no references to it are
     * maintained by this list. (In other words, this method must allocate a new
     * array even if this list is backed by an array). The caller is thus free
     * to modify the returned array.
     *
     * <p>
     * This method acts as bridge between array-based and collection-based APIs.
     *
     * @return an array containing all of the elements in this list in proper
     * sequence
     * @see java.util.Arrays#asList(Object[])
     */
    @Override
    public Object[] toArray() {
        return syncBackingList.toArray();
    }

    /**
     * Not supported.
     *
     * @param <T> Generic support
     * @throws UnsupportedOperationException Not supported
     */
    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Appends the specified element to the end of this list (optional
     * operation).
     *
     * <p>
     * Lists that support this operation may place limitations on what elements
     * may be added to this list. In particular, some lists will refuse to add
     * null elements, and others will impose restrictions on the type of
     * elements that may be added. List classes should clearly specify in their
     * documentation any restrictions on what elements may be added.
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     * @throws UnsupportedOperationException if the <tt>add</tt> operation is
     * not supported by this list
     * @throws ClassCastException if the class of the specified element prevents
     * it from being added to this list
     * @throws NullPointerException if the specified element is null and this
     * list does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     * prevents it from being added to this list
     */
    @Override
    public boolean add(T e) {
        requireNonNull(e);
        int index = findIndex(element -> element.getId().equals(e.getId()));
        boolean changed = index == -1 && syncBackingList.add(e);
        if (changed) {
            setModified();
        }
        return changed;
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException Not supported
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException Not supported
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException Not supported
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException Not supported
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException Not supported
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Removes all of the elements from this list (optional operation). The list
     * will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation is
     * not supported by this list
     */
    @Override
    public void clear() {
        if (syncBackingList.size() > 0) {
            syncBackingList.clear();
            setModified();
        }
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range (<tt>index
     * &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public T get(int index) {
        return syncBackingList.get(index);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException Not supported
     */
    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException Not supported
     */
    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Removes the element at the specified position in this list (optional
     * operation). Shifts any subsequent elements to the left (subtracts one
     * from their indices). Returns the element that was removed from the list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation is
     * not supported by this list
     * @throws IndexOutOfBoundsException if the index is out of range (<tt>index
     * &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public T remove(int index) {
        final T removedElement = syncBackingList.remove(index);
        setModified();
        return removedElement;
    }

    /**
     * Removes the first occurrence of the specified element from this list, if
     * it is present (optional operation). If this list does not contain the
     * element, it is unchanged. More formally, removes the element with the
     * lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists). Returns <tt>true</tt> if this list contained
     * the specified element (or equivalently, if this list changed as a result
     * of the call).
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     * @throws ClassCastException if the type of the specified element is
     * incompatible with this list
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     * list does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation is
     * not supported by this list
     */
    @Override
    public boolean remove(Object o) {
        requireNonNull(o);
        boolean changed = syncBackingList.remove(o);
        if (changed) {
            setModified();
        }
        return changed;
    }

    /**
     * Returns the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element. More
     * formally, returns the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException if the type of the specified element is
     * incompatible with this list
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     * list does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int indexOf(Object o) {
        requireNonNull(o);
        return syncBackingList.indexOf(o);
    }

    /**
     * Returns the index of the last occurrence of the specified element in this
     * list, or -1 if this list does not contain the element. More formally,
     * returns the highest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in this
     * list, or -1 if this list does not contain the element
     * @throws ClassCastException if the type of the specified element is
     * incompatible with this list
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     * list does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int lastIndexOf(Object o) {
        requireNonNull(o);
        return syncBackingList.lastIndexOf(o);
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * @return a list iterator over the elements in this list (in proper
     * sequence)
     */
    @Override
    public ListIterator<T> listIterator() {
        return syncBackingList.listIterator();
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list. The specified
     * index indicates the first element that would be returned by an initial
     * call to {@link ListIterator#next next}. An initial call to
     * {@link ListIterator#previous previous} would return the element with the
     * specified index minus one.
     *
     * @param index index of the first element to be returned from the list
     * iterator (by a call to {@link ListIterator#next next})
     * @return a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range
     * ({@code index < 0 || index > size()})
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        return syncBackingList.listIterator(index);
    }

    /**
     * Returns a view of the portion of this list between the specified
     * <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive. (If
     * <tt>fromIndex</tt> and <tt>toIndex</tt> are equal, the returned list is
     * empty.) The returned list is backed by this list, so non-structural
     * changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all of the optional list operations supported
     * by this list.<p>
     *
     * This method eliminates the need for explicit range operations (of the
     * sort that commonly exist for arrays). Any operation that expects a list
     * can be used as a range operation by passing a subList view instead of a
     * whole list. For example, the following idiom removes a range of elements
     * from a list:
     * <pre>{@code
     *      list.subList(from, to).clear();
     * }</pre> Similar idioms may be constructed for <tt>indexOf</tt> and
     * <tt>lastIndexOf</tt>, and all of the algorithms in the
     * <tt>Collections</tt> class can be applied to a subList.<p>
     *
     * The semantics of the list returned by this method become undefined if the
     * backing list (i.e., this list) is <i>structurally modified</i> in any way
     * other than via the returned list. (Structural modifications are those
     * that change the size of this list, or otherwise perturb it in such a
     * fashion that iterations in progress may yield incorrect results.)
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     * (<tt>fromIndex &lt; 0 || toIndex &gt; size || fromIndex &gt;
     * toIndex</tt>)
     */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return syncBackingList.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        return "AbstractNoNullList{" + "backingArrayList=" + syncBackingList + '}';
    }

}
