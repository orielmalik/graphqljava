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

    public Mono<UnitEntity> findByEmailsEmpolyeeContains(@Param("emailId") String emailId);

    //TO EYAL:we know we can do the same with  query findByParentId and put at Entity String
    //but you said Action Freedom"חופש פעולה"
    //dont remove never a letter
    public Flux<UnitEntity> findAllByParentIdContains(@Param("fromId") String fromId,Pageable pageable);

    Flux<UnitEntity> findAllByEmailsEmpolyeeContains(@Param("email") String email);


}