package it.unibo.inner.test.impl;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

import java.util.Iterator;


public class IterableWithPolicyImpl<T>  implements it.unibo.inner.api.IterableWithPolicy<T> {

    private final T[] elements;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(final T[] array, Predicate<T> filter) {
        this.elements = array;
        this.filter = filter;
    }

    public IterableWithPolicyImpl(final T[] array) {
        this(array, elem -> true); // Default filter that accepts all elements
    }

    @Override
    public void setIterationPolicy(final Predicate<T> filter) {
        this.filter = filter;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        // Implementation goes here
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {

        private T nextElement;
        private boolean nextReady;
        private int currentIndex = 0;
        
        @Override
        public boolean hasNext() {
            if(nextReady) {
                return true;
            }
            while (currentIndex < elements.length) {
                T elem = elements[currentIndex++];
                if(filter.test(elem)) {
                    nextElement = elem;
                    nextReady = true;
                    return true;
                }
            }
            return false;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            nextReady = false;
            return nextElement;
        }
    }
}