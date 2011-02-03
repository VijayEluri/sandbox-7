package test;

public class EmployeeRepository extends OnMemoryRepository<Employee> {

	private final OnMemoryRepository<Department> departmentRepository;

	public EmployeeRepository(
			OnMemoryRepository<Department> departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Override
	public synchronized void store(Employee entity) {
		for (Department department : entity.getDepartments()) {
			departmentRepository.store(department);
		}
		super.store(entity);

	}

}