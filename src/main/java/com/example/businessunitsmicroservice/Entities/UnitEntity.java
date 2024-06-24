package com.example.businessunitsmicroservice.Entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "UNITS")
public class UnitEntity {

    @Id
    private String id;
    private Date creationDate;
    private String type;
    private String  emailManager;
    private Set<UnitEntity> subUnits;
    private Set<String> emailsEmpolyee;
    private UnitEntity parent;


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

    public Set<UnitEntity> getSubUnits() {
        return subUnits;
    }

    public void setSubUnits(Set<UnitEntity> subUnits) {
        this.subUnits = subUnits;
    }

    public UnitEntity getParent() {
        return parent;
    }

    public void setParent(UnitEntity parent) {
        this.parent = parent;
    }


    public Set<String> getEmailsEmpolyee() {
        return emailsEmpolyee;
    }

    public void setEmailsEmpolyee(Set<String> emailsEmpolyee) {
        this.emailsEmpolyee = emailsEmpolyee;
    }
}
