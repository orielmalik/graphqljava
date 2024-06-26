package com.example.businessunitsmicroservice.Entities;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "EMPLOYEE")
public class EmployeeEntity {

   @Id private String email;
   private List<UnitEntity> manages;
   private List<UnitEntity>units;

    public EmployeeEntity(){}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public List<UnitEntity> getManages() {
        return manages;
    }

    public void setManages(List<UnitEntity>manages) {
        this.manages = manages;
    }

    public List<UnitEntity> getUnits() {
        return units;
    }

    public void setUnits(List<UnitEntity> units) {
        this.units = units;
    }
}
