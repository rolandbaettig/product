package ch.steponline.address.model;

import ch.steponline.address.model.Employer;
import ch.steponline.core.model.AbstractVersionedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 08.04.14
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="Employee",schema="address",
        indexes = {
                @Index(name="IDX_Employee_SurName",columnList = "PreName"),
                @Index(name="IDX_Employee_Employer",columnList = "EmployerId")
        }
)
public class Employee extends AbstractVersionedEntity<Long,Employee> {

    @Id
    private Long id;

    @Column(name="PreName",nullable = false,length = 150)
    @NotNull
    @Size(max=150)
    private String preName;

    @Column(name="SurName",nullable = false,length = 150)
    @NotNull
    @Size(max=150)
    private String surName;

    @Column(name="EmployeeSince",nullable = false)
    @Temporal(value = TemporalType.DATE)
    @NotNull
    private Date employeeSince;

    @Column(name="EmployeeUntil",nullable = true)
    @Temporal(value = TemporalType.DATE)
    private Date employeeUntil;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="EmployerId",referencedColumnName = "Id",
            foreignKey = @ForeignKey(name="FK_Employee_Employer",value=ConstraintMode.CONSTRAINT,foreignKeyDefinition = "FOREIGN KEY (EmployerId) REFERENCES Employer (Id) ON DELETE CASCADE"))
    private Employer employer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreName() {
        return preName;
    }

    public void setPreName(String preName) {
        this.preName = preName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Date getEmployeeSince() {
        return employeeSince;
    }

    public void setEmployeeSince(Date employeeSince) {
        this.employeeSince = employeeSince;
    }

    public Date getEmployeeUntil() {
        return employeeUntil;
    }

    public void setEmployeeUntil(Date employeeUntil) {
        this.employeeUntil = employeeUntil;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        if (this.employer!=null &&
                this.employer.getEmployees().contains(this)) {
            this.employer.getEmployees().remove(this);
        }
        this.employer = employer;

        if (employer!=null && !employer.getEmployees().contains(this)) {
            employer.getEmployees().add(this);
        }
        this.employer = employer;
    }
}
