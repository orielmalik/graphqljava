package com.example.businessunitsmicroservice.Controller;
import com.example.businessunitsmicroservice.Interfaces.UnitService;
import com.example.businessunitsmicroservice.Boundaries.UnitBoundary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.example.businessunitsmicroservice.*;

@RestController
@RequestMapping("/org")
public class UnitController {
    private UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/{existingParentUnitId}")
    public Mono<UnitBoundary> createUnit(@PathVariable String existingParentUnitId, @RequestBody UnitBoundary unitBoundary) {
        return this.unitService.create(existingParentUnitId, unitBoundary);
    }

    @DeleteMapping
    public Mono<Void> deleteAllExceptOrg() {
        return this.unitService.deleteAllNotOrg();
    } @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<UnitBoundary> get() {
        return this.unitService.getAll();
    }





}
