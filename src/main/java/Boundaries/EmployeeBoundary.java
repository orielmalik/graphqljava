package Boundaries;

import Entities.EmployeeEntity;

public class EmployeeBoundary {

    private String email;

    private UnitBoundary[]subUnits;
    private UnitBoundary[]manages;

public EmployeeBoundary(){}
public EmployeeBoundary(EmployeeEntity employeeEntity)
{
    this.setEmail(employeeEntity.getEmail());


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
}
