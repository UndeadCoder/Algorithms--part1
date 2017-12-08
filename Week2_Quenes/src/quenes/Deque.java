import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Node<Item> first;
  private Node<Item> last;
  private int n;

  private class Node<Item> {
    private Item item;
    private Node<Item> next;
    private Node<Item> last;
  }

  public Deque() {
    first = null;
    last = null;
    n = 0;
  }

  public boolean isEmpty() {
    return first == null || last == null;
  }

  public int size() {
    return n;
  }

  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    Node<Item> oldfirst = first;
    first = new Node<Item>();
    first.item = item;
    first.next = null;
    first.last = null;
    if (isEmpty()) {
      last = first;
    } else {
      first.next = oldfirst;
      oldfirst.last = first;
    }
    n++;
  }

  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    Node<Item> oldlast = last;
    last = new Node<Item>();
    last.item = item;
    last.next = null;
    last.last = null;
    if (isEmpty()) {
      first = last;
    } else {
      last.last = oldlast;
      oldlast.next = last;
    }
    n++;
  }

  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    final Item item = first.item;
    first = first.next;
    if (isEmpty()) {
      last = null;
    } else {
      first.last = null;
    }
    n--;
    return item;
  }

  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    final Item item = last.item;
    last = last.last;
    if (isEmpty()) {
      first = null;
    } else {
      last.next = null;
    }
    n--;
    return item;
  }

  public Iterator<Item> iterator() {
    return new ListIterator(first);
  }

  private class ListIterator implements Iterator<Item> {

    private Node<Item> current;

    public ListIterator(Node<Item> first) {
      current = first;
    }

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (isEmpty()) {
        throw new NoSuchElementException();
      }
      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  public static void main(String[] args) {
    Deque<String> deque = new Deque<String>();
    System.out.println(deque.isEmpty());
    deque.addFirst("4");
    deque.addFirst("3");
    deque.addFirst("2");
    deque.addFirst("1");
    deque.addLast("5");
    deque.addLast("6");
    deque.addLast("7");
    System.out.println(deque.size());
    for (Object item: deque) {
      System.out.print(item + " ");
    }
    System.out.println();
    System.out.println(deque.removeLast());
    System.out.println(deque.removeFirst());
    System.out.println(deque.size());
    for (Object item: deque) {
      System.out.print(item + " ");
    }
  }
}
