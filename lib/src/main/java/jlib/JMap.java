package jlib;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JMap is a simple chained HashMap implementation.
 */
public class JMap<K, V> implements Map<K, V> {

    private List<JList<Entry<K, V>>> buckets;
    private final int bucketLength;
    private int size;

    public JMap() {
        this(2048);
    }

    public JMap(final int bucketLength) {
        this.bucketLength = bucketLength;
        this.buckets = new ArrayList<>(this.bucketLength);
        this.size = 0;
        this.init();
    }

    public JMap(final Map<? extends K, ? extends V> m) {
        this();
        this.putAll(m);
    }


    /**
     * Lazy way of avoiding {@link IndexOutOfBoundsException}'s but when I have
     * time I will try to find a clever way to only increase the size of the
     * underlying Array when needed.
     * <p>
     * TODO: Be more clever then this.
     */
    private void init() {
        for (int i = 0; i < this.bucketLength; i++)
            this.buckets.add(new JList<>());
    }

    @Override
    public void clear() {
        this.buckets.clear();
    }

    private int hash(@NonNull final Object key) {
        return Math.abs(key.hashCode() % this.bucketLength);
    }

    @Override
    public boolean containsKey(final Object key) {
        return this.keySet().stream()
                .anyMatch(k -> k.equals(key));
    }


    @Override
    public boolean containsValue(final Object value) {
        return this.values().stream()
                .anyMatch(val -> val.equals(value));
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.buckets.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned.
     * @throws NullPointerException if the specified key is null.
     */
    @Override
    public V get(@NonNull final Object key) {
        final var hash = hash(key);
        if (this.buckets.size() <= hash)
            return null;

        final var bucket = this.buckets.get(hash);
        return bucket.stream()
                .filter(entry -> entry.getKey().equals(key))
                .map(Entry::getValue)
                .findAny().orElse(null);
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. The set is NOT backed
     * by the map, so changes to the map are NOT reflected in the set, and vice-versa.
     *
     * TODO: Make sure changes are reflected since this is what the contract of
     * the inferface demands
     *
     * @return a set view of the keys contained in this map
     */
    @Override
    public Set<K> keySet() {
        return this.buckets.stream()
                .flatMap(Collection::stream)
                .map(Entry::getKey)
                .collect(Collectors.toSet());
    }

    /**
     * Associates the specified value with the specified key in this map
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was
     * no mapping for key.
     * @throws NullPointerException if the specified key and/or value is null.
     */
    @Override
    public V put(@NonNull final K key, @NonNull final V value) {
        final var hash = hash(key);
        if (this.buckets.size() <= hash)
            this.buckets.set(hash, new JList<>());

        final var bucket = this.buckets.get(hash);
        final var entry = new JMapEntry<>(key, value);
        final Optional<Entry<K, V>> oldEntryOptional = bucket.stream()
                .filter(e -> e.getKey().equals(key))
                .findAny();

        V ret = null;
        if (oldEntryOptional.isPresent()) {
            bucket.remove(oldEntryOptional.get());
            ret = oldEntryOptional.get().getValue();
        }

        bucket.add(entry);
        this.size++;
        return ret;
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * More formally, if this map contains a mapping from key k to value v such
     * that Objects.equals(key, k), that mapping is removed.
     * <p>
     * Returns the value to which this map previously associated the key, or
     * null if the map contained no mapping for the key.
     *
     * @param key key whose mapping is to be removed from this {@link JMap}.
     * @return the previous value associated with key, or null if there was no
     * mapping for key.
     * @throws NullPointerException if the specified key is null
     */
    @Override
    public V remove(@NonNull final Object key) {
        final var bucket = this.buckets.get(hash(key));
        final var optionalOldEntry = bucket.stream()
                .filter(e -> e.getKey().equals(key))
                .findAny();

        if (optionalOldEntry.isEmpty())
            return null;

        bucket.remove(optionalOldEntry.get());
        this.size--;
        return optionalOldEntry.get().getValue();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Collection<V> values() {
        return this.buckets.stream()
                .flatMap(Collection::stream)
                .map(Entry::getValue)
                .collect(Collectors.toList());
    }

}
