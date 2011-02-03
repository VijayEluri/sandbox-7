package utils;

public interface Option<T> {

	public T get();

	public T getOrElse(T defaultValue);

}
