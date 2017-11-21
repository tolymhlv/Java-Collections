package com.javarush.task.task37.task3707;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.util.*;

public class AmigoSet<E> extends AbstractSet implements Serializable, Cloneable, Set {
    private final static Object PRESENT = new Object();

    private transient HashMap<E, Object> map;

    public AmigoSet() {
        map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        int largeCapacity = (int) (collection.size() / .75f) + 1;
        int capacity = 16 > largeCapacity ? 16 : largeCapacity;
        map = new HashMap<>(capacity);
        collection.forEach(elem -> map.put(elem, PRESENT));
    }


    @Override
    public boolean add(Object o) {
        E key = (E) o;
        return map.put(key, PRESENT) == null;
    }

    @Override
    public Iterator iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Object clone() throws InternalError {
        try {
            AmigoSet<E> cloneSet = new AmigoSet<>();
            cloneSet.addAll(this);
            cloneSet.map = (HashMap<E, Object>) map.clone();
            return cloneSet;
        } catch (Throwable e) {
            throw new InternalError(e);
        }

    }
}
