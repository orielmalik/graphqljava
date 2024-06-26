package com.example.businessunitsmicroservice.Controller;


import com.example.businessunitsmicroservice.Boundaries.*;
import com.example.businessunitsmicroservice.Interfaces.UnitService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class UnitGraphQlController {
    private UnitService unitService;

    public UnitGraphQlController(UnitService unitService) {
        this.unitService = unitService;
    }


//    @QueryMapping
//    public Mono<EmployeeBoundary> employee(@Argument String id) {
//        return this.unitService
//                .getEmployeeFromAllUnits(id)
//                                .log();
//    }


    @QueryMapping
    public Flux<UnitBoundary> allUnits(
            @Argument int page,
            @Argument int size
    ) {
        return this.unitService.getAllUnits(page, size)
                .log();
    }

    @QueryMapping
    public Mono<UnitBoundary> unit(
            @Argument String id
    ) {
        return this.unitService.getSpecificUnitById(id)
                .log();
    }
//pagination

    @SchemaMapping
    public Flux<UnitBoundary> subUnits(
            UnitBoundary dummy,
            @Argument int page,
            @Argument int size) {
        return this.unitService.getSubUnits(dummy.toEntity(), size, page).log();
    }   @SchemaMapping
    public Flux<UnitBoundary> units(
            EmployeeBoundary dummy,
            @Argument int page,
            @Argument int size) {
        return this.unitService.getEmployeeUnits(dummy.toEntity(), size, page).log();
    }
    public Flux<UnitBoundary> manages(
            EmployeeBoundary dummy,
            @Argument int page,
            @Argument int size) {
        return this.unitService.getEmployeemanages(dummy.toEntity(), size, page).log();
    }


    @QueryMapping
    public Mono<EmployeeBoundary> employee(@Argument String email) {
        return this.unitService
                .getEmployeeFromAllUnits(email);
    }

}
