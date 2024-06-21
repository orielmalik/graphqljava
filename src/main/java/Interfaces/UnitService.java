package Interfaces;

import Boundaries.UnitBoundary;
import reactor.core.publisher.Mono;

public interface UnitService {

public Mono<UnitBoundary>create(String existingParentUnitId,UnitBoundary unitBoundary);
    public Mono<Void> deleteAllNotOrg();
    public Mono<Void> deleteAll();


}
