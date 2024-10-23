package structures;

public interface IAVL<T extends Comparable<T>> {
    public void add(T value);
    public void delete(T value);
}
