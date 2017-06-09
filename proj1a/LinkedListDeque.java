/**
 * Created by medionchou on 2017/5/19.
 */
public class LinkedListDeque<Item> {

    private static class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> prev;

        public Node(Item item, Node<Item> next, Node<Item> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<Item> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node<>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(Item item) {
        Node<Item> theNode = new Node<>(item, null, null);
        theNode.prev = sentinel;
        theNode.next = sentinel.next;
        sentinel.next.prev = theNode;
        sentinel.next = theNode;
        size += 1;
    }

    public void addLast(Item item) {
        Node<Item> theNode = new Node<>(item, null, null);
        theNode.prev = sentinel.prev;
        theNode.next = sentinel;
        sentinel.prev.next = theNode;
        sentinel.prev = theNode;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node<Item> head = sentinel.next;

        while (head != sentinel) {
            System.out.println(head.item + " ");
            head = head.next;
        }
    }

    public Item removeFirst() {
        if (size == 0) return null;
        Node<Item> first = sentinel.next;
        first.next.prev = sentinel;
        sentinel.next = first.next;
        size -= 1;
        return first.item;
    }

    public Item removeLast() {
        if (size == 0) return null;
        Node<Item> last = sentinel.prev;
        last.prev.next = sentinel;
        sentinel.prev = last.prev;
        size -= 1;
        return last.item;
    }

    public Item get(int index) {
        if (index >= size) return null;
        Node<Item> first = sentinel.next;

        for (int i = 0; i < index; i++) {
            first = first.next;
        }
        return first.item;
    }

    public Item getRecursive(int index) {
        if (index >= size) return null;

        Node<Item> node = search(sentinel.next, index, 0);

        return node.item;
    }

    private Node<Item> search(Node<Item> node, int index, int cur) {
        if (cur < index) return search(node.next, index, cur + 1);
        else if (cur == index) return node;
        else return null;
    }
}
