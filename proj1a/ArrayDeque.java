/**
 * Created by medionchou on 2017/5/19.
 */
public class ArrayDeque<Item> {

    private Item[] queue;
    private int size;
    private int front;
    private int rear;

    public ArrayDeque() {
        queue = (Item[]) new Object[8];
        size = 0;
        front = 0;
        rear = 1;
    }

    public void addFirst(Item item) {
        if (size == queue.length) extend(queue.length * 2);

        queue[front--] = item;
        size += 1;
        if (front < 0) front += queue.length;
    }

    public void addLast(Item item) {
        if (size == queue.length) extend(queue.length * 2);

        queue[rear++] = item;
        size += 1;
        if (rear >= queue.length) rear -= queue.length;
    }

    private void extend(int capacity) {
        Item[] tmp = (Item[]) new Object[capacity];
        front += 1;
        for (int i = 0; i < size; i++) {
            if (front == queue.length) front -= queue.length;
            tmp[i] = queue[front++];
        }
        front = queue.length - 1;
        rear = size;
        queue = tmp;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int tmp = front + 1;

        for (int i = 0; i < size; i++, tmp++) {
            if (tmp == queue.length) tmp -= queue.length;
            System.out.println(queue[tmp] + " ");
        }
    }

    public Item removeFirst() {
        if (size == 0) return null;
        if (size / queue.length <= 0.25 && queue.length >= 16) constract(queue.length / 2);
        Item item;

        front = front + 1 == queue.length ? 0 : front + 1;
        item = queue[front];
        queue[front] = null;
        size -= 1;

        return item;
    }

    public Item removeLast() {
        if (size == 0) return null;
        if (size / queue.length <= 0.25 && queue.length >= 16) constract(queue.length / 2);
        Item item;

        rear = rear - 1 < 0 ? queue.length - 1 : rear - 1;
        item = queue[rear];
        queue[rear] = null;
        size -= 1;

        return item;
    }

    private void constract(int capacity) {
        Item[] tmp = (Item[]) new Object[capacity];

        front += 1;

        for (int i = 0; i < size; i++) {
            if (front == queue.length) front -= queue.length;
            tmp[i] = queue[front++];
        }
        front = queue.length - 1;
        rear = size;
        queue = tmp;
    }

    public Item get(int index) {
        if (index >= size) return null;

        index = (index + front + 1) % queue.length;

        return queue[index];
    }

}
