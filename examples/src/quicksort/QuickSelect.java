package quicksort;

public class QuickSelect {

  public static int select(Comparable[] a, int k) {
    int lo = 0;
    int hi = a.length - 1;
    while (lo < hi) {
     int j = QuickSort.partition(a, lo, hi);
     if (j < k) {
       lo = j + 1;
     } else if (j > k) {
       hi = j - 1;
     } else {
       return a[k];
     }
    }
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
