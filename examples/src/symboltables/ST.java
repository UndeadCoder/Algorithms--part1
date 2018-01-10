package symboltables;

public class ST<Key extends Comparable<Key>, Value> {
    private Key[] keySet;
    private Value[] valueSet;

    public St() {
        keySet = new Key[2];
        valueSet = new Value[2];
    }

    public void put(Key key, Value val) {
        if (val == null) {
            throw new IllegalArgumentException();
        }

    }

    public void delete(Key key) {

    }

    public boolean contains(Key key) {

    }

    public boolean isEmpty() {
        return keySet[0] != null;
    }

    public int size() {
        return keySet.length;
    }

    public Iterable<Key> keys() {
        return new KeyIterator();
    }



}
