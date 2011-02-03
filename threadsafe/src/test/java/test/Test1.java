package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class Test1 {
	static final Department エロ部 = Department.of(1L, "エロ部");
	static final Department 宴会部 = Department.of(2L, "宴会部");
	static final Department 企画部 = Department.of(3L, "企画部");
	static final Department 技術部 = Department.of(4L, "技術部");

	@Test
	public void test() {

		OnMemoryRepository<Employee> employeeRepository = new OnMemoryRepository<Employee>();
		Employee kato = Employee.of(1L, "かとう");
		kato.setPosition(Position.EROGRAMMER);
		kato.addDepartment(エロ部);
		kato.addDepartment(技術部);
		kato.addDepartment(企画部);

		assertThat(kato.getDepartments().size(), is(3));

		employeeRepository.store(kato); // storeしてから
		kato.clearDepartments(); // クリアする
		kato = null; // そして、一応ここで明示的にemployeeインスタンスとの関連を断つ

		Employee kato2 = employeeRepository.findById(1L); // 新しいのをもってくる
		assertThat(kato2.getDepartments().size(), is(3)); // このテストはコケる
	}
}
