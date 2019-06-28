package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
	@NamedQuery(name="findByDepartmentId", query="SELECT d FROM DepartmentEntity d where d.id = :deptid"),
	@NamedQuery(name="showDepartmentList",query="select d from DepartmentEntity d order by d.name ASC")
})
@Entity
@Table(name="department")
public class DepartmentEntity {

	
	@Id
	private int id;
	
	@Column(name="name")
	private String name;
	
	
	
}
