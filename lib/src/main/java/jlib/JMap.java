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

    public JMap() {
        this.buckets = new ArrayList<>();
        this.bucketLength = 40;

        this.init();
    }

    public JMap(final int bucketLength) {
        this.buckets = new ArrayList<>();
        this.bucketLength = bucketLength;

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
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean containsValue(final Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public V get(Object key) {
        // TODO Auto-generated method stub
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
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        // TODO Auto-generated method stub

    }

    @Override
    public V remove(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Collection<V> values() {
        // TODO Auto-generated method stub
        return null;
    }

    @ToString
    @EqualsAndHashCode
    public class JMapEntry<K, V> {
        @Getter
        private final K key;
        @Getter
        private final V value;

        public JMapEntry(final K key, final V value) {
            this.key = key;
            this.value = value;
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
