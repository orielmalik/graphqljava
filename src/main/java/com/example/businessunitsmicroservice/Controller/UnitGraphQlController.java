package com.example.businessunitsmicroservice.Controller;


import com.example.businessunitsmicroservice.Boundaries.UnitBoundary;
import com.example.businessunitsmicroservice.Interfaces.UnitService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class UnitGraphQlController {
    private UnitService unitService;

    public UnitGraphQlController(UnitService unitService) {
        this.unitService = unitService;
    }


    @QueryMapping
    public Mono<UnitBoundary> getSpecificUnit(@Argument String id) {
        return this.unitService
                .getSpecificUnitById(id)
                .map(this::toGraphQLBoundary)
                .log();
    }

    @QueryMapping
    public Flux<UnitBoundary> allUnits(
            @Argument int page,
            @Argument int size
    ) {
        return this.unitService.getAllUnits(page, size)
                .log();
    }

}
