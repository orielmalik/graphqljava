package Entities;


import Boundaries.UnitBoundary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "UNITS")
public class UnitEntity {

    @Id
    private String id;
    private Date creationDate;
    private String type;
    private String  emailManager;
    private List<UnitEntity> subUnits;
    private UnitEntity[]units;
    public UnitEntity(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmailManager() {
        return emailManager;
    }

    public void setEmailManager(String emailManager) {
        this.emailManager = emailManager;
    }

    public List<UnitEntity> getSubUnits() {
        return subUnits;
    }

    public void setSubUnits(List<UnitEntity> subUnits) {
        this.subUnits = subUnits;
    }

    public UnitEntity[] getUnits() {
        return units;
    }

    public void setUnits(UnitEntity[] units) {
        this.units = units;
    }


}
