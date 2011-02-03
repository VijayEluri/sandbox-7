package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class Test1 {
	static final Department �G���� = Department.of(1L, "�G����");
	static final Department ��� = Department.of(2L, "���");
	static final Department ��敔 = Department.of(3L, "��敔");
	static final Department �Z�p�� = Department.of(4L, "�Z�p��");

	@Test
	public void test() {

		OnMemoryRepository<Employee> employeeRepository = new OnMemoryRepository<Employee>();
		Employee kato = Employee.of(1L, "���Ƃ�");
		kato.setPosition(Position.EROGRAMMER);
		kato.addDepartment(�G����);
		kato.addDepartment(�Z�p��);
		kato.addDepartment(��敔);

		assertThat(kato.getDepartments().size(), is(3));

		employeeRepository.store(kato); // store���Ă���
		kato.clearDepartments(); // �N���A����
		kato = null; // �����āA�ꉞ�����Ŗ����I��employee�C���X�^���X�Ƃ̊֘A��f��

		Employee kato2 = employeeRepository.findById(1L); // �V�����̂������Ă���
		assertThat(kato2.getDepartments().size(), is(3)); // ���̃e�X�g�̓R�P��
	}
}
