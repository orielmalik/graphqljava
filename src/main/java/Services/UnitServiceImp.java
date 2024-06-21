package Services;

import Boundaries.UnitBoundary;
import Entities.UnitEntity;
import Exceptions.BadRequest400;
import Exceptions.NotFound404;
import Interfaces.UnitService;
import Interfaces.UnitCrud;
import Tools.ValidationUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashSet;

@Service
public class UnitServiceImp implements UnitService {

    private UnitCrud unitCrud;


    public UnitServiceImp(UnitCrud peopleCrud )
    {
        this.unitCrud=peopleCrud;
    }


    @Override
    public Mono<UnitBoundary> create(String existingParentUnitId,UnitBoundary unitBoundary) {
        return this.unitCrud.findById(unitBoundary.getId()).
                flatMap(unitEntity -> {return Mono.error(new BadRequest400("email exist"))
                        ;}).
                switchIfEmpty(Mono.defer(()->{
                            UnitEntity unitEntity=unitBoundary.toEntity();
                            unitEntity.setCreationDate(new Date());
                            unitEntity.setId(unitBoundary.getId());
                            unitEntity.setSubUnits(new HashSet<>());
                            return Mono.just(unitEntity);
                        })).zipWith(this.unitCrud.findById(existingParentUnitId))
                        .flatMap(tuple->{
                            UnitEntity fromEntity = (UnitEntity) tuple.getT1();
                            UnitEntity toEntity = tuple.getT2();
                            toEntity.getSubUnits().add(fromEntity);
//TODO
                              return  this.unitCrud.save(toEntity);
                        }).map(UnitBoundary::new).log();

    }

    @Override
    public Mono<Void> deleteAllNotOrg() {
        return this.unitCrud.findById("ceo@demo.org")//we know the org -it is not null
                .flatMap(unitEntity -> { return this.unitCrud.deleteAllExcept(unitEntity.getId());})
                .then().log();

    }






}
