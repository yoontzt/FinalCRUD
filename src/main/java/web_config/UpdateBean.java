package web_config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
@ManagedBean
@ViewScoped
public class UpdateBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

		
		departmentList = depService.toBoms(depService.showAll());
		Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String employeeId = paramMap.get("eid");
		int eid = Integer.valueOf(employeeId);
		
		employee = empService.toBom(empService.findById(eid));
		setId(employee.getDepartment().getId());
	}

	

	public String updateEmployeeFromPage() {
		employee.setDepartment(depService.toEntity(department));
		empService.updateEmployee(employee);
		employeeList = empService.toBoms(empService.showAll());
		PrimeFaces.current().executeScript("top.location.reload()");
		return "index.xhtml";
	}


	public void changeDepartment(ValueChangeEvent dept) {
		department = depService.toBom(depService.findDepartmentById(Integer.parseInt(dept.getNewValue().toString())));
	}
}