package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

public class ThreadTest {

	static final Department �G���� = Department.of(1L, "�G����");
	static final Department ��� = Department.of(2L, "���");
	static final Department ��敔 = Department.of(3L, "��敔");
	static final Department �Z�p�� = Department.of(4L, "�Z�p��");

	@Test
	public void test() {
		OnMemoryRepository<Department> departmentRepository = new OnMemoryRepository<Department>();
		departmentRepository.store(�G����);
		departmentRepository.store(���);
		departmentRepository.store(��敔);
		departmentRepository.store(�Z�p��);

		EmployeeRepository employeeRepository = new EmployeeRepository(
				departmentRepository);
		employeeRepository.store(Employee.of(1L, "������"));

		CountDownLatch startLatch = new CountDownLatch(1);

		List<Thread> threads = new ArrayList<Thread>(4);

		Employee kato = Employee.of(1L, "���Ƃ�");
		kato.setPosition(Position.EROGRAMMER);
		kato.addDepartment(�G����);
		kato.addDepartment(�Z�p��);
		kato.addDepartment(��敔);

		Employee tsumoto = Employee.of(1L, "�s��");
		tsumoto.setPosition(Position.PROGRAMMER);
		tsumoto.addDepartment(�Z�p��);
		tsumoto.addDepartment(��敔);
		tsumoto.addDepartment(���);

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

		if (employee.getName().equals("���Ƃ�")) {
			assertThat(employee.getName(), is("���Ƃ�"));
			assertThat(employee.getPosition(), is(Position.EROGRAMMER));
			assertThat(employee.getDepartments().get(0), is(�Z�p��));
			assertThat(employee.getDepartments().get(1), is(��敔));
			assertThat(employee.getDepartments().get(2), is(�G����));

		} else if (employee.getName().equals("�s��")) {
			assertThat(employee.getName(), is("�s��"));
			assertThat(employee.getPosition(), is(Position.PROGRAMMER));
			assertThat(employee.getDepartments().get(0), is(�Z�p��));
			assertThat(employee.getDepartments().get(1), is(��敔));
			assertThat(employee.getDepartments().get(2), is(���));

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
