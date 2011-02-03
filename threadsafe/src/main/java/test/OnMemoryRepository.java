package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OnMemoryRepository<T extends Entity> implements Cloneable {
	private Map<Long, T> entities = new ConcurrentHashMap<Long, T>();

	@SuppressWarnings("unchecked")
	public synchronized List<T> asEntitiesList() {
		List<T> result = new ArrayList<T>();
		Set<Entry<Long, T>> entrySet = entities.entrySet();
		for (Entry<Long, T> entry : entrySet) {
			result.add((T) entry.getValue().clone());
		}
		Collections.sort(result, new Comparator<T>() {

			@Override
			public int compare(T arg0, T arg1) {
				return arg0.getId().compareTo(arg1.getId()) * -1;
			}

		});
		return result;
	}

	public void clear() {
		entities.clear();
	}

	@Override
	@SuppressWarnings("unchecked")
	public OnMemoryRepository<T> clone() {
		try {
			OnMemoryRepository<T> repository = (OnMemoryRepository<T>) super
					.clone();
			repository.entities = new ConcurrentHashMap<Long, T>();
			Set<Entry<Long, T>> entrySet = entities.entrySet();
			for (Entry<Long, T> entry : entrySet) {
				repository.entities.put(entry.getKey(), (T) entry.getValue()
						.clone());
			}
			return repository;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized T findById(Long id) {
		if (entities.containsKey(id) == false) {
			throw new EntityNotFoundException(id);
		}
		return (T) entities.get(id).clone();
	}

	@SuppressWarnings("unchecked")
	public void store(T entity) {
		entities.put(entity.getId(), (T) entity.clone());
	}
}
