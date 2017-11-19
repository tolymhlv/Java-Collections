package com.javarush.task.task20.task2028;


import java.io.Serializable;
import java.util.*;

/*
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    private Entry<String> root;
    private LinkedList<CustomTree.Entry<String>> queue;
    private int visibleSize;
    private int lastNumber;

    public CustomTree() {
        root = new Entry<String>("0");
        root.lineNumber = 0;

    }
    public static void main(String[] args) {
        List<String> list = new CustomTree();
        for (int i = 0; i < 15; i++) {
            list.add(String.valueOf(i));
        }
        //System.out.println("Expected 3, actual is " + ((CustomTree) list).getParent("8"));
        list.remove("0");
        System.out.println("Expected null, actual is " + ((CustomTree) list).getParent("0"));
    }

    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        void checkChildren() {
            if (leftChild != null)
                availableToAddLeftChildren = false;
            if (rightChild != null)
                availableToAddRightChildren = false;
        }

        boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }

        @Override
        public String toString() {
            return "en" + elementName;
        }
    }
    @Override
    public boolean add(String elementName) {
        queue = new LinkedList<>(Collections.singleton(root));

        Entry<String> next;
        while ((next = queue.poll()) != null) {
            next.checkChildren();
            if (next.isAvailableToAddChildren()) break;
            if (next.leftChild != null) queue.add(next.leftChild);
            if (next.rightChild != null) queue.add(next.rightChild);
        }

        Entry<String> newChild = new Entry<>(String.valueOf(++lastNumber));
        newChild.parent = next;
        if (next.availableToAddLeftChildren) next.leftChild = newChild;
        else next.rightChild = newChild;
        next.checkChildren();
        visibleSize++;
        return true;
    }
    @Override
    public boolean remove(Object o) {
        if (!(o instanceof String)) return false;
        if (o.equals(root.elementName)) return false;

        queue = new LinkedList<>(Collections.singleton(root));
        Entry<String> entry;
        while ((entry = queue.poll()) != null) {
            if (entry.elementName.equals(o)) {
                break;
            }
            if (entry.leftChild != null) queue.add(entry.leftChild);
            if (entry.rightChild != null) queue.add(entry.rightChild);
        }
        if (entry == null) return false;

        Entry<String> parent;
        if ((parent = entry.parent) != null) {
            if (parent.leftChild != null && parent.leftChild.elementName.equals(o))
                parent.leftChild = null;
            else parent.rightChild = null;
            parent.checkChildren();
        }

        queue = new LinkedList<>(Collections.singleton(entry));
        Entry<String> nextChild;
        while ((nextChild = queue.poll()) != null) {
            visibleSize--;
            nextChild.parent = null;
            if (nextChild.leftChild != null) {
                queue.add(nextChild.leftChild);
                nextChild.leftChild = null;
            }
            if (nextChild.rightChild != null) {
                queue.add(nextChild.rightChild);
                nextChild.rightChild = null;
            }
        }
        return true;
    }

    String getParent(Object o) {
        if (!(o instanceof String)) return null;
        if (o.equals(root.elementName)) return null;

        queue = new LinkedList<>(Collections.singleton(root));
        Entry<String> next;
        while ((next = queue.poll()) != null) {
            if (next.elementName.equals(o)) {
                break;
            }
            if (next.leftChild != null) queue.add(next.leftChild);
            if (next.rightChild != null) queue.add(next.rightChild);
        }
        if (next == null) return null;

        return next.parent.elementName;
    }

    @Override
    public int size() {
        return visibleSize;
    }

    //@Contract(pure = true)
    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
