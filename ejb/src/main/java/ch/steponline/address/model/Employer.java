package ch.steponline.address.model;

import ch.steponline.address.model.Employee;
import ch.steponline.core.model.AbstractVersionedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 08.04.14
 * Time: 15:05
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="Employer",schema="address", indexes = {
        @Index(name="IDX_Firm_Name",columnList = "Name")
})
public class Employer extends AbstractVersionedEntity<Long,Employer>{
    @Id
    private Long id;

    @Column(name="Name",nullable = false,length = 255)
    @NotNull
    @Size(max=255)
    private String name;

    @Column(name="WorkingDays",nullable = false)
    @NotNull
    private Double workingDays;

    @Column(name="DailyWorkingHours",nullable=false)
    @NotNull
    private Double dailyWorkingHours;

    @OneToMany(mappedBy = "employer",fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)

    private Set<Employee> employees=new HashSet<Employee>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Double workingDays) {
        this.workingDays = workingDays;
    }

    public Double getDailyWorkingHours() {
        return dailyWorkingHours;
    }

    public void setDailyWorkingHours(Double dailyWorkingHours) {
        this.dailyWorkingHours = dailyWorkingHours;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee) {
        if (employee.getEmployer()==null ||
                !employee.getEmployer().equals(this)) {
            employee.setEmployer(this);
        }
        if (!employees.contains(employee)) {
            employees.add(employee);
        }
    }

    public void removeEmployee(Employee employee) {
        if (employee.getEmployer()!=null &&
                employee.getEmployer().equals(this)) {
            employees.remove(employee);
            employee.setEmployer(null);
        }
    }
}
