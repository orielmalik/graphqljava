package com.example.businessunitsmicroservice.Boundaries;

public class ParentUnit {
 private UnitBoundary unitBoundary;

public ParentUnit(){}

    public ParentUnit(UnitBoundary root) {
    this.unitBoundary=root;
    }



    public UnitBoundary getUnitBoundary() {
        return unitBoundary;
    }

    public void setUnitBoundary(UnitBoundary unitBoundary) {
        this.unitBoundary = unitBoundary;
    }
}
