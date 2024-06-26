package com.example.businessunitsmicroservice.Boundaries;

import com.example.businessunitsmicroservice.Entities.EmployeeEntity;
import com.example.businessunitsmicroservice.Entities.UnitEntity;
import com.example.businessunitsmicroservice.Tools.ValidationUtils;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UnitBoundary {


    private String id;
    private String type;
    private Manager manager;
    private EmployeeBoundary[]employees;
    private UnitBoundary[]subUnits;
    private String creationDate;
    private UnitBoundary parentUnit;

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

            setParentUnit(new UnitBoundary(unitEntity.getParent()));
        }
        if(unitEntity.getSubUnits()!=null)
        {
            setSubUnits(ValidationUtils.convertHashSetToArray(unitEntity.getSubUnits(), unitEntity));

        }
        if(unitEntity.getEmailsEmpolyee()!=null) {
            //UnitBoundary[] 0arr=new UnitBoundary[unitEntity.getEmployees().size()];
            //setEmployees(ValidationUtils.createEmployeesFromEmails(unitEntity.getEmailsEmpolyee()));
            setEmployees(ValidationUtils.createEmployeesFromEntities(unitEntity.getEmployees()));
        }else
        {
            unitEntity.setEmailsEmpolyee(new HashSet<>());
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
        if(getManager()!=null&&getManager().getEmail()!=null) {
            unitEntity.setEmailManager(getManager().getEmail());
        }
        unitEntity.setId(getId());
        if(type!=null) {
            unitEntity.setType(getType());
        }
        if(getParentUnit()!=null) {
            unitEntity.setParent(getParentUnit().toEntity());
            if(unitEntity.getParentIds()==null)
            {
                unitEntity.setParentIds(new HashSet<>());
            }
            unitEntity.getParentIds().add(getParentUnit().getId());

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
            unitEntity.setEmployees(ValidationUtils.extractmployee(getEmployees()));
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

    public UnitBoundary getParentUnit() {
        return parentUnit;
    }

    public void setParentUnit(UnitBoundary parentUnit) {
        this.parentUnit = parentUnit;
    }

    public UnitBoundary[] getSubUnits() {
        return subUnits;
    }

    public void setSubUnits(UnitBoundary[] subUnits) {
        this.subUnits = subUnits;
    }

}
