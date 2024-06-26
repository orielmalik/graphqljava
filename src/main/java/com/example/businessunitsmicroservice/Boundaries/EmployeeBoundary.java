package com.example.businessunitsmicroservice.Boundaries;

import com.example.businessunitsmicroservice.Entities.EmployeeEntity;
import com.example.businessunitsmicroservice.Entities.UnitEntity;

import java.util.ArrayList;
import java.util.Arrays;

public class EmployeeBoundary {

    private String email;

    private UnitBoundary[] Units;
    private UnitBoundary[]manages;

public EmployeeBoundary(){}
public EmployeeBoundary(EmployeeEntity employeeEntity)
{
    this.setEmail(employeeEntity.getEmail());

    if(employeeEntity.getManages()!=null) {
        this.setManages(new UnitBoundary[employeeEntity.getManages().size()]);
        for (int i = 0; i < getManages().length; i++) {
if(employeeEntity.getManages()!=null)
{
    getManages()[i]=new UnitBoundary(employeeEntity.getManages().get(i));
}
        }
    }



        if (employeeEntity.getUnits() != null) {
            for (int j = 0; j < getManages().length; j++) {
                getUnits()[j] = new UnitBoundary(employeeEntity.getUnits().get(j));

            }
        }
    }



    public EmployeeBoundary(String s) {
    this.email=s;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UnitBoundary[] getUnits() {
        return Units;
    }

    public void setUnits(UnitBoundary[] units) {
        this.Units = units;
    }

    public UnitBoundary[] getManages() {
        return manages;
    }

    public void setManages(UnitBoundary[] manages) {
        this.manages = manages;
    }

    public EmployeeEntity toEntity() {
    EmployeeEntity employeeEntity=new EmployeeEntity();
        employeeEntity.setUnits(new ArrayList<>());
        employeeEntity.setManages(new ArrayList<>());
if(getUnits()!=null){
        Arrays.asList(getUnits()).forEach(unitBoundary -> {
                    if (unitBoundary != null) {
                        employeeEntity.getUnits().add(unitBoundary.toEntity());
                    }
        });} if(getManages()!=null){
            Arrays.asList(getManages()).forEach(unitBoundary1 -> {
                if (unitBoundary1 != null) {
                    employeeEntity.getManages().add(unitBoundary1.toEntity());
                }
            });}
            employeeEntity.setEmail(getEmail());



        return employeeEntity;

    }
}