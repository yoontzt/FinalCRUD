package bom;

import entities.DepartmentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Employee {

	private int id;
	private String name;
	private Integer age;
	private String email;
	private DepartmentEntity department;
}
