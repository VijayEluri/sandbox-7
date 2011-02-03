package test;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Employee extends AbstractEntity {

	public static Employee of(Long id, String name) {
		return new Employee(id, name);
	}

	private OnMemoryRepository<Department> departmentRepository = new OnMemoryRepository<Department>();

	private String name;

	private Position position;

	private Employee(Long id, String name) {
		super(id);
		this.name = name;
	}

	public void addDepartment(Department department) {
		departmentRepository.store(department);
	}

	public void clearDepartments() {
		departmentRepository.clear();
	}

	@Override
	public Employee clone() {
		Employee result = (Employee) super.clone();
		result.departmentRepository = departmentRepository.clone();
		return result;
	}

	public List<Department> getDepartments() {
		return departmentRepository.asEntitiesList();
	}

	public String getName() {
		return name;
	}

	public Position getPosition() {
		return position;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
