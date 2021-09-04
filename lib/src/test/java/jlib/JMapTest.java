package jlib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JMapTest {
    @Test
    public void testCreateEmptyMap() {
        final var map = new JMap<String, Integer>();

        assertEquals("The size of an empty map should be 0", 
                0, map.size());
        assertTrue("An empty map should not contain any values", 
                !map.containsValue(4));
        assertTrue("Expected isEmpty() to return true", map.isEmpty());
    }

    @Test
    public void testAddElementToEmptyMap() {
        final var map = new JMap<String, Integer>();
        map.put("key", 4);

        assertEquals("Expected size of map to be 1", 
                1, map.size());;
        assertEquals("Expected 4 to be the returned value",
                4, (int) map.get("key"));
        assertTrue("Expected containsValue to return true", 
                map.containsValue(4));
        assertTrue("Expected containsKey to return true", 
                map.containsKey("key"));
    }

    @Test
    public void testRemoveFromJMap() {
        final var map = new JMap<String, Integer>();
        map.put("key", 4);

        assertTrue("The map should not be empty", 
                !map.isEmpty());
        assertEquals("The size of the map should be 1", 
                1, map.size());

        final var val = map.remove("key");
        assertEquals("The returned value of remove() should be the old value",
                4, (int) val);
        assertNull("Removing twice should return null when calling remove()", 
                map.remove("key"));
    }

}
