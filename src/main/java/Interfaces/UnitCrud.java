package Interfaces;

import Entities.UnitEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UnitCrud extends ReactiveMongoRepository<UnitEntity,String> {


    public Mono<Void>deleteAllExcept(String email);
}
