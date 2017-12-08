import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] items;
  private int n;

  public RandomizedQueue() {
    items = (Item[]) new Object[2];
    n = 0;
  }

  public boolean isEmpty() {
    return n == 0;
  }

  public int size() {
    return n;
  }

  private void resize(int capacity) {
    assert capacity >= n;
    Item[] temp = (Item[]) new Object[capacity];
    for (int i = 0; i < n; i++) {
      temp[i] = items[i];
    }
    items = temp;
  }

  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (n == items.length) {
      resize(2 * items.length);
    }
    items[n++] = item;
  }

  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Item item = sample();
    items[--n] = null;
    if (n > 0 && n == items.length / 4) {
      resize(items.length / 4);
    }
    return item;
  }

  public Item sample() {
    StdRandom.shuffle(items, 0, n);
    return items[n - 1];
  }

  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    private int i;

    public ListIterator() {
      StdRandom.shuffle(items, 0, n);
      i = n - 1;
    }

    public boolean hasNext() {
      return i >= 0;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (isEmpty()) {
        throw new NoSuchElementException();
      }
      return items[i--];
    }

  }

  public static void main(String[] args) {
    RandomizedQueue<String> deque = new RandomizedQueue<String>();
    System.out.println(deque.isEmpty());
    deque.enqueue("4");
    deque.enqueue("3");
    deque.enqueue("2");
    deque.enqueue("1");
    deque.enqueue("5");
    deque.enqueue("6");
    deque.enqueue("7");
    System.out.println(deque.size());
    for (Object item: deque) {
      System.out.print(item + " ");
    }
    System.out.println();
    System.out.println(deque.dequeue());
    System.out.println(deque.dequeue());
    System.out.println(deque.size());
    for (Object item: deque) {
      System.out.print(item + " ");
    }
  }
}
