package com.example.businessunitsmicroservice.Interfaces;


import com.example.businessunitsmicroservice.Entities.UnitEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UnitCrud extends ReactiveMongoRepository<UnitEntity,String> {

    public Mono<UnitEntity> findById(String id);
    public Flux<UnitEntity> findAllByIdNotNull (Pageable pageable);
}
