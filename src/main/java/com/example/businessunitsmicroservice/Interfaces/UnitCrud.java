package com.example.businessunitsmicroservice.Interfaces;

import com.example.businessunitsmicroservice.Entities.UnitEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UnitCrud extends ReactiveMongoRepository<UnitEntity,String> {


}
