package test;

import org.apache.commons.lang.Validate;

public abstract class AbstractEntity implements Entity {

	private final Long id;

	public AbstractEntity(Long id) {
		Validate.notNull(id);
		this.id = id;
	}

	@Override
	public AbstractEntity clone() {
		try {
			return (AbstractEntity) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error();
		}

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
		AbstractEntity other = (AbstractEntity) obj;
		return id.equals(other.id);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}