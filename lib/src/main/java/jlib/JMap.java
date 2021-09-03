package jlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

public class JMap<K, V> implements Iterable<V>, Map<K, V> {

    private ArrayList<JList<JMapEntry<K, V>>> buckets;
    private final int bucketLength;
    private int size;

    public JMap() {
        this(40);
    }

    public JMap(final int bucketLength) {
        this.buckets = new ArrayList<>();
        this.bucketLength = bucketLength;
        this.size = 0;

        this.init();
    }

    private void init() {
        for (int i = 0; i < this.bucketLength; i++)
            this.buckets.add(new JList<>());
    }

    @Override
    public void clear() {
        this.buckets.clear();
    }

    private int hash(final Object key) {
        return key.hashCode() & this.bucketLength;
    }

    @Override
    public boolean containsKey(final Object key) {
        boolean contains = false;
        for (final JMapEntry<K, V> entry : this.buckets.get(hash(key))) {
            contains = entry.getKey().equals(key);
            if (contains) break;
        }
        return contains;
    }


    @Override
    public boolean containsValue(final Object value) {
        boolean contains = false;
        outerLoop:
        for (final JList<JMapEntry<K, V>> bucket : this.buckets) {
            for (final JMapEntry<K, V> entry : bucket) {
                contains = entry.getValue().equals(value);
                if (contains) break outerLoop;
            }
        }
        return contains;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public V get(Object key) {
        // TODO: Implement
        return null;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<K> keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public V put(@NonNull final K key, @NonNull final V value) {
        final JMapEntry<K, V> entry = new JMapEntry<>(key, value);
        final int hash = this.hash(key);
        final JList<JMapEntry<K, V>> bucket = this.buckets.get(hash);

        //TODO: Implement the rest ;)
        this.size++;
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        // TODO Auto-generated method stub
        this.size += m.size();
    }

    @Override
    public V remove(Object key) {
        // TODO Auto-generated method stub
        this.size--;
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Collection<V> values() {
        // TODO Auto-generated method stub
        return null;
    }

    @ToString
    @EqualsAndHashCode
    public class JMapEntry<K, V> implements Entry<K, V> {
        @Getter
        private final K key;
        @Getter
        private V value;

        public JMapEntry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public V setValue(V value) {
            final V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    @Override
    public Iterator<V> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    public class JMapIterator<V> implements Iterator<V> {

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public V next() {
            // TODO Auto-generated method stub
            return null;
        }
    }

}
