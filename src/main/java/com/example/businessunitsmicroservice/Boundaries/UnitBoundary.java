package com.example.businessunitsmicroservice.Boundaries;

import com.example.businessunitsmicroservice.Entities.UnitEntity;
import com.example.businessunitsmicroservice.Tools.ValidationUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

public class UnitBoundary {


    private String id;
    private String type;
    private Manager manager;
    private EmployeeBoundary[]employees;
    private UnitBoundary[]subUnits;
    private String creationDate;
    private ParentUnit parentUnit;

    public UnitBoundary(){}

    public UnitBoundary(UnitEntity unitEntity)
    {
        setEmployees(getEmployees());
        setId(unitEntity.getId());
        Manager man=new Manager();man.setEmail(unitEntity.getEmailManager());
        setManager(man);
        setType(unitEntity.getType());
        setCreationDate(ValidationUtils.dateToString(unitEntity.getCreationDate()));
        if(unitEntity.getParent()!=null){
        setParentUnit(new ParentUnit(new UnitBoundary(unitEntity.getParent())));}
        if(unitEntity.getSubUnits()!=null)
       {
               setSubUnits(ValidationUtils.convertHashSetToArray(unitEntity.getSubUnits(), unitEntity));

       }
        if(unitEntity.getEmailsEmpolyee()!=null) {
            setEmployees(ValidationUtils.createEmployeesFromEmails(unitEntity.getEmailsEmpolyee()));
        }
    }

    public UnitBoundary(String id) {
        this.id=id;
    }

    public UnitEntity toEntity()
    {
        Date date;
        UnitEntity unitEntity=new UnitEntity();
        if(ValidationUtils.isValidDateFormat(getCreationDate()))//exactly will happen if fixed
        {
            try {
                date=ValidationUtils.stringToDate(getCreationDate())  ;
                unitEntity.setCreationDate(date);
            }catch (ParseException p)
            {

            }

        }
        unitEntity.setEmailManager(getManager().getEmail());
        unitEntity.setId(getId());
        unitEntity.setType(getType());
        if(getParentUnit()!=null) {
            unitEntity.setParent(getParentUnit().getUnitBoundary().toEntity());
        }
        unitEntity.setSubUnits(new HashSet<>());
        if(getSubUnits()!=null) {
            for (int i = 0; i < getSubUnits().length; i++) {
                if (getSubUnits()[i] != null)
                {
                   UnitEntity unitEntity1= getSubUnits()[i].toEntity();
                    unitEntity.getSubUnits().add(unitEntity1);//recursian for tree

                }
            }
        }

        if(getEmployees()!=null) {
            unitEntity.setEmailsEmpolyee(ValidationUtils.extractEmails(getEmployees()));
        }
        //the rest i will fill after call with graphql
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
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
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UnitBoundary {")
                .append("id='").append(id).append("', ")
                .append("type='").append(type).append("', ")
                .append("manager=").append(manager == null ? "null" : manager.getEmail().toString())
                .append(", ")
                .append("creationDate='").append(creationDate).append("'");

        if (subUnits != null && subUnits.length > 0) {
            sb.append(", subUnits=[");
            for (int i = 0; i < subUnits.length; i++) {
                if (subUnits[i] != null) {
                    sb.append(subUnits[i].toString()).append(", ");
                } else {
                    sb.append("null, ");
                }
            }
            sb.setLength(sb.length() - 2); // Remove trailing comma and space
            sb.append("]");
        }

        sb.append(", parentUnit=").append(parentUnit == null ? "null" : parentUnit.toString())
                .append("}");
        return sb.toString();
    }
}
