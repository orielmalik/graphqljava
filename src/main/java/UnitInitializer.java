

import Boundaries.Manager;
import Boundaries.UnitBoundary;
import Interfaces.UnitCrud;
import Interfaces.UnitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
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
        unitBoundary.setCreationDate(new Date());
        Manager manager=new Manager();
        manager.setEmail("ceo@gmail.com");
        unitBoundary.setManager(manager);
        UnitBoundary root = this.UnitService.create(null,unitBoundary)
                .block();
//todo continiue  liwa bar about the tree look at Eyal Code at DummyI
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

    }
}
