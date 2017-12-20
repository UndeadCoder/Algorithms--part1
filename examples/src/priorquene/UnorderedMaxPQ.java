package priorquene;

public class UnorderedMaxPQ<Key extends Comparable<Key>> {
  private Key[] pq；
  private int n;

  public UnorderedMaxPQ(int capcity) {
    int n = 0;
    pq = (Key[]) new Comparable[capcity];
  }

  public void insert(Key v) {
    pq[n++] = v;
  }

  private void exch(Comparable[] a, int i, int j) {
    Comparable temp = a[i];
    a[i] = a[j];
    a[j] =  temp;
  }

  public Key max() {
    Key max = pq[0];
    for (int i = 0; i < n; i++) {
      if (max.compareTo(pq[i] < 0)) {
        max = pq[i];
        exch(pq, i, --n);
      }
    }
    return max;
  }

  public Key delMax() {
    Key max = max();
    pq[n] = null;
    return max;
  }

  boolean isEmpty() {
    return n == 0;
  }

  int size() {
    return n;
  }
}
