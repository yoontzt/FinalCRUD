package web_config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;

import bom.Department;
import bom.Employee;
import lombok.Getter;
import lombok.Setter;
import services.DepartmentService;
import services.EmployeeService;

@SuppressWarnings("deprecation")
@ManagedBean(name = "homeBean")
@ViewScoped
public class HomeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5002361951830512815L;
	private @Getter @Setter Department department = new Department();
	private @Getter @Setter Employee employee = new Employee();
	private @Getter @Setter int id;

	@Inject
	private EmployeeService empService;

	@Inject
	private DepartmentService depService;

	private @Getter @Setter List<Employee> employeeList = new ArrayList<>();

	private @Getter @Setter List<Department> departmentList = new ArrayList<>();

	@PostConstruct
	public void init() {
		employeeList = empService.toBoms(empService.showAll());
		departmentList = depService.toBoms(depService.showAll());
		if (departmentList.size() > 0) {
			department = departmentList.get(0);
		}

	}

	public void addNewEmployee() {
		employee.setDepartment(depService.toEntity(department));
		empService.addEmployee(employee);
		employeeList = empService.toBoms(empService.showAll());
		PrimeFaces.current().executeScript("PF('addEmployee').hide()");
		
	}

	public void updateEmployeeFromPage() {
		employee.setDepartment(depService.toEntity(department));
		empService.updateEmployee(employee);
		employeeList = empService.toBoms(empService.showAll());
		PrimeFaces.current().executeScript("PF('UpdateEmployee').hide()");
		
	}

	public void deleteEmployeeFromPage(Employee employeeBOM) {
		empService.deleteEmployee(empService.toEntity(employeeBOM));
		employeeList = empService.toBoms(empService.showAll());
		
	}

	public void viewEmployee(Employee emp) {
		setEmployee(emp);
		setId(emp.getDepartment().getId());
		PrimeFaces.current().executeScript("PF('UpdateEmployee').show()");

	}

	public void changeDepartment(ValueChangeEvent dept) {
		department = depService.toBom(depService.findDepartmentById(Integer.parseInt(dept.getNewValue().toString())));
	}
}