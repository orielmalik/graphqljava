package Interfaces;

import Boundaries.UnitBoundary;
import reactor.core.publisher.Mono;

public interface UnitService {

    Mono<UnitBoundary> create(String existingParentUnitId, UnitBoundary unitBoundary);
    Mono<Void> deleteAllNotOrg();
    Mono<Void> deleteAll();


    Mono<UnitBoundary> getSpecificUnitById(String id);
}