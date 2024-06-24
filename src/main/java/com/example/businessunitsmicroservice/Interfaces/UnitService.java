package com.example.businessunitsmicroservice.Interfaces;

import com.example.businessunitsmicroservice.Boundaries.UnitBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UnitService {

public Mono<UnitBoundary>create(String existingParentUnitId,UnitBoundary unitBoundary);
    public Mono<Void> deleteAllNotOrg();
    public Mono<Void> deleteAll();
    public Mono<Void> bindUnits();


    Flux<UnitBoundary> getAll();

    Mono<UnitBoundary> getById(String id);

    Mono<UnitBoundary> getAllbyId();
}
