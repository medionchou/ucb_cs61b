package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {

    @Test(expected = RuntimeException.class)
    public void someTest() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer<>(1);


        arb.enqueue(3.9);

        assertEquals((Double) 3.9, arb.peek());
        assertEquals(1, arb.fillCount());
        assertEquals(arb.isFull(), true);
        assertEquals((Double) 3.9, arb.dequeue());
        assertEquals(arb.isFull(), false);

        arb.enqueue(3.8);

        assertEquals((Double) 3.8, arb.peek());
        assertEquals(1, arb.fillCount());
        assertEquals(arb.isFull(), true);
        assertEquals((Double) 3.8, arb.dequeue());
        assertEquals(arb.isFull(), false);

        arb.enqueue(3.7);
        assertEquals((Double) 3.7, arb.peek());
        assertEquals(1, arb.fillCount());
        assertEquals(arb.isFull(), true);

        arb.enqueue(3.6);
    }

    @Test
    public void testIterator() {


        int[] a = {1, 2};
        int[] b = {1, 2, 3};

        assertArrayEquals(a, b);

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
