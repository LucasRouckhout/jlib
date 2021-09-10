package jlib;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class JSet<T> implements Set<T> {

    private JMap<T, T> map;

    public JSet() {
        this.map = new JMap<>();
    }

	@Override
	public boolean add(final T value) {
		return this.map.put(value, value) == null;
	}

	@Override
	public boolean addAll(final Collection<? extends T> c) {
        boolean added = false;
        for (final T e : c)
            added = this.add(e);
        return added;
	}

	@Override
	public void clear() {
        this.map.clear();
	}

	@Override
	public boolean contains(final Object element) {
        return this.map.containsKey(element);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
        return this.map.values().containsAll(c);
	}

	@Override
	public boolean isEmpty() {
        return this.map.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
        return this.map.values().iterator(); 
	}

	@Override
	public boolean remove(final Object element) {
        return this.map.remove(element) != null;
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
        return c.stream()
            .map(e -> this.remove(e))
            .filter(b -> b == true)
            .count() > 0;
	}

	@Override
	public boolean retainAll(final Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(final T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}}
