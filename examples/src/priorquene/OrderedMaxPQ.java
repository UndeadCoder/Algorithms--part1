package priorquene;

public class OrderedMaxPQ<Key extends Comparable<Key>> {
  private Key[] pq;
  private int n;

  public OrderedMaxPQ(int capcity) {
    int n = 0;
    pq = (Key[]) new Comparable[capcity];
  }

  public void insert(Key v) {
    for (int i = 1; i < n; i++) {
      if (less(pq[i - 1], v) && less(v, pq[i])) {
         for (int j = i; j < n; j++) {
           Key temp = pq[j + 1];
           pq[j + 1] = pq[j];
         }
         pq[i] = v;
      }
    }
    n++;
  }

  private boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  private void exch(Comparable[] a, int i, int j) {
    Comparable temp = a[i];
    a[j] = a[i];
    a[j] = temp;
  }

  public Key Max() {
    return pq[n - 1];
  }

  public Key delMax() {
    Key max = pq[--n];
    pq[n] = null;
    return max;
  }

  public boolean isEmpty() {
    return n == 0;
  }

  public int size() {
    return n;
  }
}
