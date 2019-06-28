package web_config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import bom.Department;
import bom.Employee;
import lombok.Getter;
import lombok.Setter;
import services.DepartmentService;
import services.EmployeeService;

@SuppressWarnings("deprecation")
@ManagedBean
@ViewScoped
public class WebHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8495411679904641370L;
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

	public String addNewEmployee() {
		employee.setDepartment(depService.toEntity(department));
		empService.addEmployee(employee);
		employeeList = empService.toBoms(empService.showAll());
		return "index.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public String updateEmployeeFromPage() {
		employee.setDepartment(depService.toEntity(department));
		empService.updateEmployee(employee);
		employeeList = empService.toBoms(empService.showAll());
		return "index.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public String deleteEmployeeFromPage(Employee employeeBOM) {
		empService.deleteEmployee(empService.toEntity(employeeBOM));
		employeeList = empService.toBoms(empService.showAll());
		return "index.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public String viewEmployee(Employee emp) {
		return "update.xhtml";
	}

	public void changeDepartment(ValueChangeEvent dept) {
		department = depService.toBom(depService.findDepartmentById(Integer.parseInt(dept.getNewValue().toString())));
	}
}