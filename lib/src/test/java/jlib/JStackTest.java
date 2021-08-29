package jlib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

public class JStackTest {
    @Test
    public void testCreateEmptyStack() {
        final JStack<Integer> s = new JStack<>();
        assertTrue("Stack should be empty", s.isEmpty());
    }

    @Test
    public void testPushElements() {
        final JStack<Integer> s = new JStack<>();
        s.push(5);
        s.push(5);
        s.push(5);

        final Optional<Integer> val = s.peek();
        assertEquals(3, s.size());
        assertTrue(val.isPresent());
        assertEquals(5, (int) val.get());
    }

    @Test
    public void testPopElements() {
        final JStack<Integer> s = new JStack<>();
        s.push(5);
        s.push(5);
        s.push(5);

        assertEquals(3, s.size());
        final Optional<Integer> val = s.pop();
        assertTrue(val.isPresent());
        assertEquals(5, (int) val.get());
        assertEquals(2, s.size());
    }
}
