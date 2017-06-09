// TODO: Make sure to make this class a part of the synthesizer package
// package <package name>;
package synthesizer;

import java.util.Iterator;
import java.util.function.Consumer;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    @Override
    public Iterator<T> iterator() {
        return new RingBufferIterator(first, fillCount);
    }

    private class RingBufferIterator implements Iterator<T> {

        private int iFirst;
        private int iFillCount;

        public RingBufferIterator(int first, int fillCount) {
            iFirst = first;
            iFillCount = fillCount;
        }

        @Override
        public boolean hasNext() {
            return iFillCount != 0;
        }

        @Override
        public T next() {
            T item = rb[iFirst];
            iFirst = advance(iFirst);
            iFillCount -= 1;
            return item;
        }
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.

        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()) throw new RuntimeException("Ring buffer overflow");

        rb[last] = x;
        last = advance(last);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (isEmpty()) throw new RuntimeException("Ring buffer underflow");
        T item = rb[first];
        rb[first] = null;
        first = advance(first);
        fillCount -= 1;
        return item;
    }

    private int advance(int pt) {
        if (pt + 1 == capacity) return (pt + 1) % capacity;
        return pt + 1;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (isEmpty()) throw new RuntimeException("Ring buffer is empty");
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.

    public static void main(String[] args) {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer<>(10);

        for (int i = 0; i < 10; i++) arb.enqueue(1.0 * i);

        Iterator<Double> it = arb.iterator();

        for (Double d : arb) {
            arb.dequeue();
            for (Double d1 : arb) {
                System.out.println(d + " " + d1);
            }
        }


        System.out.println("Test");
    }
}
