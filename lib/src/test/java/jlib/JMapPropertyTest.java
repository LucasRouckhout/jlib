package jlib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class JMapPropertyTest {
    
    @Property public void propertySizeEqualsSizeOfInputCollection(final ArrayList<String> keys, final ArrayList<Integer> values) {
        final var map = new JMap<String, Integer>();
        final int SIZE = Math.min(keys.size(), values.size());
        for (int i = 0; i < SIZE; i++)
            map.put(keys.get(i), values.get(i));

        assertEquals("The size of the input string array should be equal to the size of the map", 
                SIZE, map.size());
        assertTrue(values.containsAll(map.values()));
        assertTrue(keys.containsAll(map.keySet()));
    }

}
