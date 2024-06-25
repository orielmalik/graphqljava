package com.example.businessunitsmicroservice.Interfaces;

import com.example.businessunitsmicroservice.Boundaries.EmployeeBoundary;
import com.example.businessunitsmicroservice.Boundaries.UnitBoundary;
import com.example.businessunitsmicroservice.Entities.UnitEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface UnitService {

    public Mono<UnitBoundary>create(String existingParentUnitId,UnitBoundary unitBoundary);
    public Mono<Void> deleteAllNotOrg();
    public Mono<Void> deleteAll();
    public Mono<Void> bindUnits(UnitEntity units,String id);


    Flux<UnitBoundary> getAll();

    Mono<UnitBoundary> getById(String id);

    Mono<UnitBoundary> getAllbyId();

    Mono<UnitBoundary> getSpecificUnitById(String id);

    Flux<UnitBoundary> getAllUnits(int page, int size);
    Flux<UnitBoundary> getAllSUBUnits();

    Mono<Void>connectToParent();
    Mono<Void>connect(String parent,String child);
    public Flux<UnitBoundary> getSubUnits(UnitEntity fromId, int size, int page) ;
    Mono<Void>checkIfExist(UnitEntity unit);

    Mono<UnitBoundary> saveUnit(UnitEntity unitEntity);

    public Mono<EmployeeBoundary> getEmployeeFromAllUnits(String email) ;

}
