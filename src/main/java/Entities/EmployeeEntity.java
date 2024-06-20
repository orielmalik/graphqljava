package Entities;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "EMPLOYEE")
public class EmployeeEntity {

   @Id private String email;
   private EmployeeEntity[]manages;
   private UnitEntity[]units;

    public EmployeeEntity(){}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public EmployeeEntity[] getManages() {
        return manages;
    }

    public void setManages(EmployeeEntity[] manages) {
        this.manages = manages;
    }

    public UnitEntity[] getUnits() {
        return units;
    }

    public void setUnits(UnitEntity[] units) {
        this.units = units;
    }
}
