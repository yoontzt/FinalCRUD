package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import bom.Department;
import entities.DepartmentEntity;

@Stateless
public class DepartmentService extends GenericService<DepartmentEntity, Department> {
	public DepartmentService() {
		super();
	}

	public DepartmentEntity findDepartmentById(int deptId) {
		List<DepartmentEntity> getOneDepartment = em
				.createNamedQuery("findByDepartmentId",  DepartmentEntity.class)
				.setParameter("deptid", deptId).getResultList();

		if (getOneDepartment.isEmpty())
			throw new NoResultException("No source found");
		else
			return getOneDepartment.get(0);

	}

	
	public List<DepartmentEntity> showAll() {
		TypedQuery<DepartmentEntity> q = em.createNamedQuery("showDepartmentList", DepartmentEntity.class);
		return q.getResultList();
	}
	
	public DepartmentEntity toEntity(Department bom) {
		
		if (bom != null) {
			DepartmentEntity depEntity = new DepartmentEntity(bom.getId(), bom.getName());
			
			return depEntity;
		}
		
		return null;
	}

	public Department toBom(DepartmentEntity entity) {
		if (entity != null) {
			
			Department dep = new Department(entity.getId(), entity.getName());
			return dep;
		}
		return null;
	}
	public List<DepartmentEntity> toEntities(List<Department> boms) {
		if (boms == null) {
			return Collections.emptyList();
		}
		List<DepartmentEntity> entities = new ArrayList<>();
		boms.stream().map(each -> toEntity(each)).filter(Objects::nonNull).forEach(entity -> entities.add(entity));
		return entities;
	}

	public List<Department> toBoms(List<DepartmentEntity> entities) {
		if (entities == null) {
			return Collections.emptyList();
		}
		List<Department> boms = new ArrayList<>();
		entities.stream().map(each -> toBom(each)).filter(Objects::nonNull).forEach(bom -> boms.add(bom));
		return boms;
	}
}
