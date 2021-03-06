package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

public class ThreadTest {

	static final Department エロ部 = Department.of(1L, "エロ部");
	static final Department 宴会部 = Department.of(2L, "宴会部");
	static final Department 企画部 = Department.of(3L, "企画部");
	static final Department 技術部 = Department.of(4L, "技術部");

	@Test
	public void test() {
		OnMemoryRepository<Department> departmentRepository = new OnMemoryRepository<Department>();
		departmentRepository.store(エロ部);
		departmentRepository.store(宴会部);
		departmentRepository.store(企画部);
		departmentRepository.store(技術部);

		EmployeeRepository employeeRepository = new EmployeeRepository(
				departmentRepository);
		employeeRepository.store(Employee.of(1L, "名無し"));

		CountDownLatch startLatch = new CountDownLatch(1);

		List<Thread> threads = new ArrayList<Thread>(4);

		Employee kato = Employee.of(1L, "かとう");
		kato.setPosition(Position.EROGRAMMER);
		kato.addDepartment(エロ部);
		kato.addDepartment(技術部);
		kato.addDepartment(企画部);

		Employee tsumoto = Employee.of(1L, "都元");
		tsumoto.setPosition(Position.PROGRAMMER);
		tsumoto.addDepartment(技術部);
		tsumoto.addDepartment(企画部);
		tsumoto.addDepartment(宴会部);

		threads.add(new Thread(new EmployeeTask(employeeRepository, 1L, kato,
				startLatch)));
		threads.add(new Thread(new EmployeeTask(employeeRepository, 1L,
				tsumoto, startLatch)));

		for (Thread thread : threads) {
			thread.start();
		}
		startLatch.countDown();
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Employee employee = employeeRepository.findById(1L);

		System.out.println(employee);

		if (employee.getName().equals("かとう")) {
			assertThat(employee.getName(), is("かとう"));
			assertThat(employee.getPosition(), is(Position.EROGRAMMER));
			assertThat(employee.getDepartments().get(0), is(技術部));
			assertThat(employee.getDepartments().get(1), is(企画部));
			assertThat(employee.getDepartments().get(2), is(エロ部));

		} else if (employee.getName().equals("都元")) {
			assertThat(employee.getName(), is("都元"));
			assertThat(employee.getPosition(), is(Position.PROGRAMMER));
			assertThat(employee.getDepartments().get(0), is(技術部));
			assertThat(employee.getDepartments().get(1), is(企画部));
			assertThat(employee.getDepartments().get(2), is(宴会部));

		}
	}

	static class EmployeeTask implements Runnable {
		private final Employee employee;
		private final EmployeeRepository employeeRepository;
		private final Long id;
		private final CountDownLatch startLatch;

		EmployeeTask(EmployeeRepository employeeRepository, Long id,
				Employee employee, CountDownLatch startLatch) {
			this.employeeRepository = employeeRepository;
			this.id = id;
			this.employee = employee;
			this.startLatch = startLatch;
		}

		@Override
		public void run() {
			try {
				startLatch.await();
				Employee target = employeeRepository.findById(id);
				target.setName(employee.getName());
				target.setPosition(employee.getPosition());
				target.clearDepartments();
				for (Department department : employee.getDepartments()) {
					target.addDepartment(department);
				}
				employeeRepository.store(target);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
