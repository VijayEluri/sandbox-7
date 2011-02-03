package utils;

import org.apache.commons.lang.Validate;

public final class Some<T> implements Option<T> {

	private final T value;

	@Override
	public int hashCode() {
		return value.hashCode();
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
		Some<T> other = (Some<T>) obj;
		return value.equals(other.value);
	}

	public static <T> Some<T> of(T value) {
		return new Some<T>(value);
	}

	private Some(T value) {
		Validate.notNull(value);
		this.value = value;
	}

	@Override
	public T get() {
		return value;
	}

	@Override
	public T getOrElse(T defaultValue) {
		return value;
	}

}
