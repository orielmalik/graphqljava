package Boundaries;

import Entities.UnitEntity;

import java.util.Date;

public class UnitBoundary {


    private String id;
    private String type;
    private Manager manager;
    private EmployeeBoundary[]employees;
    private UnitBoundary[]subUnits;
    private Date creationDate;
    private ParentUnit parentUnit;

    public UnitBoundary(){}

public UnitBoundary(UnitEntity unitEntity)
{
    setEmployees(getEmployees());
    setId(getId());
    Manager man=new Manager();man.setEmail(unitEntity.getEmailManager());
    setManager(man);
    setType(unitEntity.getType());
    setCreationDate(unitEntity.getCreationDate());
}

    public UnitEntity toEntity()
    {
        UnitEntity unitEntity=new UnitEntity();
        unitEntity.setCreationDate(getCreationDate());
        unitEntity.setEmailManager(getManager().getEmail());
        unitEntity.setId(getId());
        unitEntity.setType(getType());
        unitEntity.setUnits(unitEntity.getUnits());
        //the rest i will fill after call
        return unitEntity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public EmployeeBoundary[] getEmployees() {
        return employees;
    }

    public void setEmployees(EmployeeBoundary[] employees) {
        this.employees = employees;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public ParentUnit getParentUnit() {
        return parentUnit;
    }

    public void setParentUnit(ParentUnit parentUnit) {
        this.parentUnit = parentUnit;
    }

    public UnitBoundary[] getSubUnits() {
        return subUnits;
    }

    public void setSubUnits(UnitBoundary[] subUnits) {
        this.subUnits = subUnits;
    }
}
