package jlib;

import java.util.Map.Entry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
public class JMapEntry<K, V> implements Entry<K, V> {

    @Getter
    private final K key; 
    @Getter
    private V value;

    public JMapEntry(@NonNull final K key, @NonNull final V value) {
        this.key = key;
        this.value = value;
    }

	@Override
	public V setValue(V value) {
        final var oldValue = this.value;
        this.value = value;
		return oldValue;
	}

}

