package com.example.businessunitsmicroservice.Entities;

import com.example.businessunitsmicroservice.Boundaries.Manager;
import com.example.businessunitsmicroservice.Boundaries.ParentUnit;
import com.example.businessunitsmicroservice.Boundaries.UnitBoundary;
import com.example.businessunitsmicroservice.Interfaces.UnitService;
import com.example.businessunitsmicroservice.Tools.ValidationUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UnitInitializer implements CommandLineRunner {
    private UnitService UnitService;

    public UnitInitializer(UnitService UnitService) {
        this.UnitService = UnitService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.UnitService.deleteAll()
                .block();

        // level 0
        UnitBoundary unitBoundary=new UnitBoundary();
        unitBoundary.setId("org");
        Manager manager=new Manager();
        manager.setEmail("ceo@gmail.com");
        unitBoundary.setManager(manager);
        unitBoundary.setCreationDate(ValidationUtils.dateToString(new Date()));
        UnitBoundary root = this.UnitService.create("",unitBoundary)
                .block();

        //נתונים שעל השירות שלכם לאתחל בבסיס הנתונים
        //היררכיה של יחידות ארגוניות שעליכם להקים עם אתחול השירות, או לוודא שהיא כבר קיימת בבסיס הנתונים שלכם. ליד כל מזהה של יחידה ארגונית, מצוינת כתובת הדואל של המנהלת של אותה יחידה:
        //org (מנהלת: ceo@demo.org) - יחידה זו תמיד קיימת בשירות שלכם, גם אם מפעילים את פעולת DELETE ב-REST API
        //R&D (מנהלת manager@rnd.demo.org)
        //Core_Division (מנהלת manager@rnd.demo.org)
        //Cloud_Team (מנהלת team.leader@rnd.demo.org)
        //DevOps_Team (מנהלת team.leader@devops.demo.org)
        //Logistics (מנהלת manager@logistics.demo.org)
        //Marketing (מנהלת manager@marteting.demo.org)
        //Sales (מנהלת manager@sales.demo.org)
        //Support (מנהלת manager@support.demo.org)
        //Third_Level_Support (מנהלת manager@support.demo.org)
        //Post_Sale (מנהלת manager@support.demo.org)
        //Finance (מנהלת manager@fin.demo.org)
        //HR (מנהלת manager@hr.demo.org)

        List<UnitBoundary> root_children =
                Flux.just("R&D", "Logistics", "Marketing","Sales","Support","Finance","HR")
                        .map(UnitBoundary::new)
                        .flatMap(d->{
                            d.setManager(createManagerForUnit(d.getId()));
                            d.setCreationDate(ValidationUtils.dateToString(new Date()));
                           // d.setParentUnit(new ParentUnit(unitBoundary));

                            return this.UnitService.create(unitBoundary.getId(),d)
                                    .flatMap(unitBoundary1 -> {return this.UnitService.bindUnits(unitBoundary1.toEntity(),root.getId());})
                                    .then(Mono.just(d));
                        })
                        .log()
                        .collectList()
                        .block();

      UnitBoundary  rd=this.UnitService.getById("R&D").block();

          List<UnitBoundary> rd_children =
                Flux.just("Core_Division", "Cloud_Team","DevOps_Team")
                        .map(UnitBoundary::new)
                        .flatMap(d->{
                            d.setManager(createManagerForUnit(rd.getId()));
                            d.setCreationDate(ValidationUtils.dateToString(new Date()));
                            //d.setParentUnit(new ParentUnit(rd));
                            return this.UnitService.create(rd.getId(),d)
                                    .flatMap(unitBoundary1 -> {return this.UnitService.bindUnits(unitBoundary1.toEntity(),rd.getId());})
                                    .then(Mono.just(d));
                        })
                        .log()
                        .collectList()
                        .block();

 UnitBoundary  support=this.UnitService.getById("Support").block();

          List<UnitBoundary> su_children =
                Flux.just("Third_Level_Support ", "Post_Sale")
                        .map(UnitBoundary::new)
                        .flatMap(d->{
                          //  d.setParentUnit(new ParentUnit(support));
                            d.setManager(createManagerForUnit(support.getId()));
                            d.setCreationDate(ValidationUtils.dateToString(new Date()));
                            return this.UnitService.create(support.getId(),d)
                                    .flatMap(unitBoundary1 -> {return this.UnitService.bindUnits(unitBoundary1.toEntity(),support.getId());})
                                    .then(Mono.just(d));
                        })
                        .log()
                        .collectList()
                        .block();


    }

private void  pl(UnitBoundary unitBoundary)
{


        if (unitBoundary.getSubUnits() != null) {
            for (int i = 0; i < unitBoundary.getSubUnits().length; i++) {
                if (unitBoundary.getSubUnits()[i].getSubUnits() != null) {
                    System.out.println(unitBoundary.getSubUnits()[i].toString());
                    for (int j = 0; j < unitBoundary.getSubUnits()[i].getSubUnits().length; j++) {
                        pl(unitBoundary.getSubUnits()[i].getSubUnits()[j]);
                    }
                }

        }
    }
    }


    private Manager createManagerForUnit(String unitName) {
        Manager manager = new Manager();

        switch (unitName) {
            case "org":
                manager.setEmail("ceo@demo.org");
                break;
            case "R&D":
                manager.setEmail("manager@rnd.demo.org");
                break;
            case "Core_Division":
                manager.setEmail("manager@rnd.demo.org");
                break;
            case "Cloud_Team":
                manager.setEmail("team.leader@rnd.demo.org");
                break;
            case "DevOps_Team":
                manager.setEmail("team.leader@devops.demo.org");
                break;
            case "Logistics":
                manager.setEmail("manager@logistics.demo.org");
                break;
            case "Marketing":
                manager.setEmail("manager@marketing.demo.org");
                break;
            case "Sales":
                manager.setEmail("manager@sales.demo.org");
                break;
            case "Support":
                manager.setEmail("manager@support.demo.org");
                break;
            case "Third_Level_Support":
                manager.setEmail("manager@support.demo.org");
                break;
            case "Post_Sale":
                manager.setEmail("manager@support.demo.org");
                break;
            case "Finance":
                manager.setEmail("manager@fin.demo.org");
                break;
            case "HR":
                manager.setEmail("manager@hr.demo.org");
                break;
            default:
                throw new IllegalArgumentException("Unknown unit name: " + unitName);
        }

        return manager;
    }
}
