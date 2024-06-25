package com.example.businessunitsmicroservice.Boundaries;

import com.example.businessunitsmicroservice.Entities.EmployeeEntity;

public class EmployeeBoundary {

    private String email;

    private UnitBoundary[]subUnits;
    private UnitBoundary[]manages;

public EmployeeBoundary(){}
public EmployeeBoundary(EmployeeEntity employeeEntity)
{
    this.setEmail(employeeEntity.getEmail());


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
        return subUnits;
    }

    public void setUnits(UnitBoundary[] units) {
        this.subUnits = units;
    }

    public UnitBoundary[] getManages() {
        return manages;
    }

    public void setManages(UnitBoundary[] manages) {
        this.manages = manages;
    }

    public EmployeeEntity toEntity() {
    EmployeeEntity employeeEntity=new EmployeeEntity();

           employeeEntity.setUnits(employeeEntity.getUnits());



            return  employeeEntity;
    }
}
