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

import java.util.*;

@Service
public class UnitServiceImp implements UnitService {

    private UnitCrud unitCrud;


    public UnitServiceImp(UnitCrud peopleCrud )
    {
        this.unitCrud=peopleCrud;
    }
    //date of json requestbody to fail Hackers if the date is not at format
    //i return the same exception to fail hackers
    @Override
    public Mono<UnitBoundary> create(String existingParentUnitId, UnitBoundary unitBoundary) {

        if((existingParentUnitId == null || existingParentUnitId.isEmpty())&&
                unitBoundary.getId().equals("org"))
        {
            return this.unitCrud.findById(unitBoundary.getId())
                    .flatMap(unitEntity -> Mono.error(new NotFound404("Unit already exists")))
                    .switchIfEmpty(Mono.defer(() -> {
                        unitBoundary.setCreationDate(ValidationUtils.dateToString(new Date()));
                        return this.unitCrud.save(unitBoundary.toEntity());
                    }))
                    .map(o -> {return  new UnitBoundary((UnitEntity) o);}).log();
        }
        else if ((existingParentUnitId == null || existingParentUnitId.isEmpty() )&&
                (unitBoundary.getId() == null || unitBoundary.getId().isEmpty())) {
            return Mono.error(new NotFound404("not found existing"));
        }

        else {//case all nodes without org
            return this.unitCrud.findById(unitBoundary.getId())
                    .flatMap(unitEntity -> Mono.error(new NotFound404("Unit already exists")))
                    .switchIfEmpty(Mono.defer(() -> {
                        unitBoundary.setCreationDate(ValidationUtils.dateToString(new Date()));
                        return Mono.just(unitBoundary.toEntity());
                    }))
                    .zipWith(this.unitCrud.findById(existingParentUnitId))
                    .switchIfEmpty(Mono.error(new NotFound404("not found existing")))
                    .flatMap(tuple -> {

                        UnitEntity child = (UnitEntity) tuple.getT1();
                        UnitEntity parent = tuple.getT2();
                        child.setParent(parent);
                        return this.unitCrud.save(child);
                    })
                    .map(UnitBoundary::new)
                    .log();}
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
                        return this.unitCrud.save(unitEntity);
                    }
                }).then().log();
    }

    @Override
    public Mono<Void> deleteAll() {
        return this.unitCrud.deleteAll();
    }

    @Override
    public Mono<Void> bindUnits(UnitEntity units,String id) {
        return this.unitCrud.findById(id).
                flatMap(unitEntity -> {
                    if(unitEntity.getSubUnits()==null)
                    {
                        unitEntity.setSubUnits(new HashSet<>());
                    }
                    unitEntity.getSubUnits().add(units);
                    ;return  this.unitCrud.save(unitEntity);}).then();
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
    public Mono<UnitBoundary> getSpecificUnitById(String id) {
        return this.unitCrud.findById(id)
                .switchIfEmpty(Mono.error(new NotFound404("Unit not found with id: " + id)))
                .map(UnitBoundary::new)
                .log();
    }



    @Override
    public Flux<UnitBoundary> getAllUnits(int page, int size) {
        return this.unitCrud.findAllByIdNotNull(PageRequest.of(page, size, Sort.Direction.ASC, "createdTimestamp", "name", "id"))
                .map(UnitBoundary::new)
                .log();
    }

    @Override
    public Mono<Void> connectToParent() {
        return this.unitCrud.findAll()
                .flatMap(unitEntity -> {
                    if (unitEntity.getParent() == null || unitEntity.getParent().getId().isEmpty()||unitEntity==null) {
                        return Mono.empty();
                    }
                    return this.unitCrud.findById(unitEntity.getParent().getId())
                            .switchIfEmpty( Mono.empty())
                            .flatMap(parentEntity -> {
                                if (parentEntity.getSubUnits() == null) {
                                    parentEntity.setSubUnits(new HashSet<>());
                                }
                                if (!parentEntity.getSubUnits().contains(unitEntity)) {
                                    parentEntity.getSubUnits().add(unitEntity);
                                    return this.unitCrud.save(parentEntity);
                                }
                                return Mono.just(parentEntity);
                            });
                })
                .then();
    }

    @Override
    public Mono<Void> connect(String parent, String child) {
        return this.unitCrud.findById(child)
                .switchIfEmpty(Mono.empty())
                .zipWith(this.unitCrud.findById(parent))
                .switchIfEmpty(Mono.empty())
                .flatMap(tuple->{
                    UnitEntity c=tuple.getT1();
                    UnitEntity p=tuple.getT2();
                    if(p.getParent().equals(c))
                    {
                        if(p.getSubUnits()==null){
                        p.setSubUnits(new HashSet<>());
                    }else {
                            p.getSubUnits().add(c);
                            return this.unitCrud.save(p);
                        }
                    }
                        return  Mono.empty();

                }).then().log();
    }

    @Override
    public Mono<UnitBoundary> saveUnit(UnitEntity unitEntity) {
        return this.unitCrud.save(unitEntity).map(UnitBoundary::new).log();
    }


}
