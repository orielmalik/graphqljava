package com.example.businessunitsmicroservice.Services;

import com.example.businessunitsmicroservice.Boundaries.UnitBoundary;
import com.example.businessunitsmicroservice.Entities.UnitEntity;
import com.example.businessunitsmicroservice.Exceptions.NotFound404;
import com.example.businessunitsmicroservice.Interfaces.UnitService;
import com.example.businessunitsmicroservice.Interfaces.UnitCrud;
import com.example.businessunitsmicroservice.Tools.ValidationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UnitServiceImp implements UnitService {

    private UnitCrud unitCrud;


    public UnitServiceImp(UnitCrud peopleCrud )
    {
        this.unitCrud=peopleCrud;
    }

    @Override
    public Mono<UnitBoundary> create(String existingParentUnitId, UnitBoundary unitBoundary) {

         if((existingParentUnitId == null || existingParentUnitId.isEmpty())&&
        unitBoundary.getId().equals("org")&&ValidationUtils.isValidDateFormat(unitBoundary.getCreationDate()))
        {
            return this.unitCrud.findById(unitBoundary.getId())
                    .flatMap(unitEntity -> Mono.error(new NotFound404("Unit already exists")))
                    .switchIfEmpty(Mono.defer(() -> {
                        return this.unitCrud.save(unitBoundary.toEntity());
                    }))
            .map(o -> {return  new UnitBoundary((UnitEntity) o);}).log();
        }
 else if ((existingParentUnitId == null || existingParentUnitId.isEmpty() )&&
                 (unitBoundary.getId() == null || unitBoundary.getId().isEmpty())) {
            return Mono.error(new NotFound404("not found existing"));
        }
        else if (!ValidationUtils.isValidDateFormat(unitBoundary.getCreationDate())) {
            return Mono.error(new NotFound404("not found existing"));//same to fail  hackers
        }
else {//case all nodes without org
             return this.unitCrud.findById(unitBoundary.getId())
                     .flatMap(unitEntity -> Mono.error(new NotFound404("Unit already exists")))
                     .switchIfEmpty(Mono.defer(() -> {

                         return this.unitCrud.save(unitBoundary.toEntity());
                     }))
                     .zipWith(this.unitCrud.findById(existingParentUnitId))
                     .switchIfEmpty(Mono.error(new NotFound404("not found existing")))
                     .flatMap(tuple -> {
                         UnitEntity child = (UnitEntity) tuple.getT1();
                         UnitEntity parent = tuple.getT2();

                         if(parent.getSubUnits()==null)
                         {
                             parent.setSubUnits(new HashSet<>());
                         }
                         parent.getSubUnits().add(child);
                         return this.unitCrud.save(parent);
                     })
                     .map(UnitBoundary::new)
                     .log();
         }
    }

    @Override
    public Mono<Void> deleteAllNotOrg() {
        return this.unitCrud.findAll()
                .flatMap(unitEntity -> {
                    if(!unitEntity.getId().equals("org"))
                    {
                        return  this.unitCrud.deleteById(unitEntity.getId());
                    }else {
                        unitEntity.setSubUnits(null);
                        return Flux.empty();
                    }
                }).then().log();
    }

    @Override
    public Mono<Void> deleteAll() {
        return this.unitCrud.deleteAll();
    }

    @Override
    public Mono<Void> bindUnits() {
        return null;
    }

    @Override
    public Flux<UnitBoundary> getAll() {
        return this.unitCrud.findAll().map(UnitBoundary::new);
    }

    @Override
    public Mono<UnitBoundary> getById(String id) {
        return this.unitCrud.findById(id).map(UnitBoundary::new);
    }

    @Override
    public Mono<UnitBoundary> getAllbyId() {
        return this.unitCrud.findById("org").map(UnitBoundary::new);
    }

    @Override
    public Flux<UnitBoundary> getAllUnits(int page, int size) {
        return this.unitCrud.findAll(PageRequest.of(page, size, Sort.Direction.ASC, "createdTimestamp", "name", "id"))
                .map(UnitBoundary::new)
                .log();
    }


}
