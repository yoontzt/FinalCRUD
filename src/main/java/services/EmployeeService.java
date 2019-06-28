package services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import bom.Employee;
import entities.EmployeeEntity;

@Stateless
public class EmployeeService extends GenericService<EmployeeEntity,Employee> {
	@EJB
	DepartmentService deptService;
	
	@EJB
	EmployeeService empService;
	
	public EmployeeService() {
		super();
	}
	
	public List<EmployeeEntity> showAll(){
		TypedQuery<EmployeeEntity> q = em.createNamedQuery("showEmployeeList", EmployeeEntity.class);
		return q.getResultList();
	}
	
	public EmployeeEntity findById(int id) {
		EmployeeEntity newemp = new EmployeeEntity();
		newemp = em.find(EmployeeEntity.class, id);
		return newemp;
		
	}
	
	
	public void addEmployee(Employee e) {
		EmployeeEntity newEntity = empService.toEntity(e);
		newEntity.setName(e.getName());
		newEntity.setAge(e.getAge());
		newEntity.setEmail(e.getEmail());
		newEntity.setDepartment(deptService.findDepartmentById(e.getDepartment().getId()));
		this.save(newEntity);
	}

	public void updateEmployee(Employee e) {
		this.update(empService.toEntity(e));
		
	}
	
	public void deleteEmployee(EmployeeEntity e) {
		EmployeeEntity newEntity = findById(e.getId());
		this.remove(newEntity);
		
	}

	public void deleteEmployeebyId(int id) {
		EmployeeEntity newemp = findById(id);
		this.remove(newemp);
		
	}
	
	@Override
	public EmployeeEntity toEntity(Employee bom) {
		if (bom != null) {
			EmployeeEntity empEntity = new EmployeeEntity(bom.getId(), bom.getName(),
					bom.getAge(), bom.getEmail(),bom.getDepartment());
			
			return empEntity;
		}
		
		return null;
	}

	@Override
	public Employee toBom(EmployeeEntity entity) {
		if (entity != null) {
			
			Employee emp = new Employee(entity.getId(), entity.getName(),
					 entity.getAge(), entity.getEmail(),entity.getDepartment());
			return emp;
		}
		return null;
	}

	
	
}
