package jlib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

public class JListTest {

    @Test
    public void testCreateEmptyJList() {
        final JList<Integer> list = new JList<>();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGetIndexOnEmptyJList() {
        final JList<Integer> list = new JList<>();
        list.get(0);
    }

    @Test
    public void testGetOnJList() {
        final JList<Integer> list = new JList<>();
        list.push(1);
        list.push(3);
        list.push(5);

        assertEquals("The size of the list should be 3", 
                3, list.size());

        assertTrue("optional should not be empty", 
                !list.get(0).isEmpty());
        assertTrue("The element at index 0 should be 1", 
                list.get(0).get() == 1);

        assertTrue("optional should not be empty",
                !list.get(2).isEmpty());
        assertTrue("The element at index 2 should be 5", 
                list.get(2).get() == 5);
    }

    @Test
    public void testForEachLoop() {
        final JList<Integer> list = new JList<>();
        list.push(1);
        list.push(3);
        list.push(5);
        
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
        list.push(54);
        final Optional<Integer> value = list.pop();

        assertTrue("Optional should not be empty", !value.isEmpty());;
        assertTrue(value.get() == 54);
    }

    @Test
    public void testForEachLoopEmptyList() {
        final JList<Integer> list = new JList<>();
        // Check if we don't get any exceptions thrown in our face.
        for (final Integer i : list) {
        }
    }

}
