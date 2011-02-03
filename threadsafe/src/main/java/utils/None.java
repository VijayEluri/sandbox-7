package utils;

import java.util.NoSuchElementException;

import org.apache.commons.lang.Validate;

public final class None<T> implements Option<T> {

	private final Class<T> clazz;

	@Override
	public int hashCode() {
		return clazz.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("unchecked")
		None<T> other = (None<T>) obj;
		return clazz.equals(other.clazz);
	}

	private None(Class<T> clazz) {
		Validate.notNull(clazz);
		this.clazz = clazz;
	}

	public static <T> None<T> of(Class<T> clazz) {
		return new None<T>(clazz);
	}

	@Override
	public T get() {
		throw new NoSuchElementException();
	}

	@Override
	public T getOrElse(T defaultValue) {
		return defaultValue;
	}

}
