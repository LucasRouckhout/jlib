package jlib;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class JListPropertyTest {

    @Property
    public void propertyListEqualSizeCollection(final ArrayList<Integer> values) {
        final var list = new JList<Integer>();
        list.addAll(values);

        assertEquals("The size of the input collection should be the same as the list",
                values.size(), list.size());
    }

    @Property
    public void propertyBilboBagins(final ArrayList<Integer> values) {
        final var list = new JList<Integer>();
        list.addAll(values);
        list.clear();

        assertTrue("The list should be empty after a clear",
                list.isEmpty());
    }
}
