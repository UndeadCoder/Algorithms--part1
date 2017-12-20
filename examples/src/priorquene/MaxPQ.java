package priorquene;

public class MaxPQ<Key extends Comparable<Key>> {
  private Key[] pq;
  private int n;

  public MaxPQ(int capcity) {
    pq = (Key[]) new Comparable[capcity];
    n = 1;
  }

  private boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  private void exch(int i, int j) {
    Comparable temp = pq[i];
    pq[i] = pq[j];
    pq[j] = temp;
  }

    private void swim(Key k) {
      while (k != null && less(k/2, k)) {
        exch(k / 2, k);
        k = k / 2;
    }
  }

  private void sink(Key k) {
    while (2 * k < n) {
      int j = 2 * k;
      if (j < n && less(j, j + 1)) {
        j++;
      }
      if (!less(k, j)) {
        break;
      }
      exch(k, j);
      k = j;
    }
  }

  public void insert(Key v) {
    pq[++n] = v;
    swim(v);
  }

  public Key delMax() {
    Key max = pq[1];
    exch(1, --n);
    pq[n] = null;
    sink(1);
    return max;
  }

}
