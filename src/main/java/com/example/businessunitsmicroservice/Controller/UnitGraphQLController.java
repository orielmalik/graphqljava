package com.example.businessunitsmicroservice.graphql;

import com.example.businessunitsmicroservice.Boundaries.UnitBoundary;
import com.example.businessunitsmicroservice.Services.UnitService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;

@Controller
public class UnitGraphQLController {
    private UnitService unitService;
    private SimpleDateFormat formatter;

    public UnitGraphQLController(UnitService unitService) {
        this.unitService = unitService;
        this.formatter = new SimpleDateFormat("dd-MM-yyyy");
    }

    @QueryMapping
    public Mono<UnitBoundary> getSpecificUnit(@Argument String id) {
        return this.unitService
                .getSpecificUnitById(id)
                .map(this::toGraphQLBoundary)
                .log();
    }

//    @QueryMapping
//    public Flux<UnitBoundary> allUnits(@Argument int page, @Argument int size) {
//        // Assuming you have a method to get a page of units
//        return this.unitService
//                .getAllUnits(size, page)
//                .map(this::toGraphQLBoundary)
//                .log();
//    }
//
//    @SchemaMapping
//    public Flux<UnitBoundary> subUnits(UnitBoundary unit, @Argument int page, @Argument int size) {
//        // Assuming you have a method to get sub-units
//        return this.unitService
//                .getSubUnits(unit.getId(), size, page)
//                .map(this::toGraphQLBoundary)
//                .log();
//    }
//
//    private UnitBoundary toGraphQLBoundary(UnitBoundary boundary) {
//        UnitBoundary graphQLBoundary = new UnitBoundary();
//
//        graphQLBoundary.setId(boundary.getId());
//        graphQLBoundary.setType(boundary.getType());
//        graphQLBoundary.setManager(boundary.getManager());
//        graphQLBoundary.setEmployees(boundary.getEmployees());
//        graphQLBoundary.setCreationDate(
//                this.formatter.format(boundary.getCreationDate()));
//        // Set other fields as needed
//
//        return graphQLBoundary;
//    }
//}

}