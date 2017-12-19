package quicksort;

import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdRandom;

public class Quick3way{

  public static void sort(Comparable[] a) {
    StdRandom.shuffle(a);
    sort(a, 0, a.length - 1);
  }

  private static void sort(Comparable[] a, int lo, int hi) {
    if (lo > hi) {
      return;
    }
    int lt = lo;
    int i = lt + 1;
    int gt = hi;
    Comparable v = a[lo];
    while (i <= gt) {
      if (less(a[i], v)) {
        exch(a, lt++, i++);
      } else if (less(v, a[i])) {
        exch(a, i++, gt--);
      } else {
        i++;
      }
    }
    sort(a, lo, lt - 1);
    sort(a, lt + 1, hi);
  }

  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  private static void exch(Comparable[] a, int i, int j) {
    Comparable temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }
}
