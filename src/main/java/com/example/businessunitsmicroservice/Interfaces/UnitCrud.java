package com.example.businessunitsmicroservice.Interfaces;


import com.example.businessunitsmicroservice.Entities.UnitEntity;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UnitCrud extends ReactiveMongoRepository<UnitEntity,String> {

    //public Mono<UnitEntity> findById(String id);
    public Flux<UnitEntity> findAllByIdNotNull(Pageable pageable);

    public Flux<UnitEntity> findAllByEmailsEmpolyeeContains(@Param("emailId") String emailId,Pageable pageable);

    public Flux<UnitEntity> findAllByParentIdContains(@Param("fromId") String fromId,Pageable pageable);

}