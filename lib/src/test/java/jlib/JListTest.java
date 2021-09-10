package jlib;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class JListTest {

    @Test
    public void testCreateEmptyJList() {
        final JList<Integer> list = new JList<>();
        assertTrue(list.isEmpty());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexOnEmptyJList() {
        final var list = new JList<Integer>();
        list.get(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowIllegalArgumentException() {
        final var list = new JList<Integer>();
        list.get(-1);
    }

    @Test
    public void testGetOnJList() {
        final JList<Integer> list = new JList<>();
        list.add(1);
        list.add(3);
        list.add(5);

        assertEquals("The size of the list should be 3",
                     3, list.size());
        assertTrue("The element at index 0 should be 1",
                   list.get(0) == 1);
        assertTrue("The element at index 2 should be 5",
                   list.get(2) == 5);
    }

    @Test
    public void testForEachLoop() {
        final JList<Integer> list = new JList<>();
        list.add(1);
        list.add(3);
        list.add(5);

        final ArrayList<Integer> arrayList = new ArrayList<>();
        for (final Integer e : list) {
            arrayList.add(e);
        }

        assertTrue("The element at index 0 should be 1",
                   1 == arrayList.get(0));
        assertTrue("The element at index 2 should be 5",
                   5 == arrayList.get(2));
    }

    @Test
    public void testPopJList() {
        final JList<Integer> list = new JList<>();
        list.add(54);
        final Optional<Integer> value = list.pop();

        assertTrue("Optional should not be empty", !value.isEmpty());;
        assertTrue(value.get() == 54);
    }

    @Test
    public void testForEachLoopEmptyList() {
        final JList<Integer> list = new JList<>();
        // Check if we don't get any exceptions thrown in our face.
        for (final Integer i : list) {
            // Do nothin
        }
    }

    @Test
    public void testContainsInJList() {
        final JList<Integer> list = new JList<>();
        list.add(5);
        assertTrue(list.contains(5));
    }

    @Test
    public void testRemoveElementFromList() {
        final JList<Integer> list = new JList<>();
        list.add(5);


        assertTrue("The return value of remove should be true", 
                list.remove(5));

        assertTrue("List should be empty", 
                list.isEmpty());
        assertEquals("The list should have size 0", 0, list.size());
    }

    @Test
    public void testRemoveFromEmptyList() {
        final var list = new JList<Integer>();

        assertTrue("The return value of remove on an empty JList should be false",
                !list.remove(5));
        assertTrue("List should be empty", 
                list.isEmpty());
        assertEquals("The list should have size 0", 
                0, list.size());
    }

    @Test
    public void testAddingNullValues() {
        final var list = new JList<Integer>();

        list.add(null);
        list.add(null);

        assertNull(list.get(0));
    }

}
