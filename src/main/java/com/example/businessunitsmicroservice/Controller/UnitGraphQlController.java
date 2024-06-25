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

    @SchemaMapping
    public Flux<UnitBoundary> subUnits(
            UnitBoundary dummy,
            @Argument int page,
            @Argument int size) {
        return this.unitService.getSubUnits(dummy.toEntity(), size, page).log();
    }


    @QueryMapping
    public Mono<EmployeeBoundary> employee(@Argument String id) {
        return this.unitService
                .getEmployeeFromAllUnits(id)
                .flatMap(employee -> {
                    // Fetch units and managed units with pagination
                    Mono<UnitBoundary[]> unitsMono = this.unitService
                            .getUnitsForEmployee(employee.getEmail(), 0, 10) // Example values for pagination
                            .collectList()
                            .map(list -> list.toArray(new UnitBoundary[0]));

                    Mono<UnitBoundary[]> managesMono = this.unitService
                            .getManagedUnitsForEmployee(employee.getEmail(), 0, 10) // Example values for pagination
                            .collectList()
                            .map(list -> list.toArray(new UnitBoundary[0]));

                    return Mono.zip(unitsMono, managesMono)
                            .map(tuple -> {
                                employee.setUnits(tuple.getT1());
                                employee.setManages(tuple.getT2());
                                return employee;
                            });
                })
                .log();
    }

}
