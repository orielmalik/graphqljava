package Controller;


import Boundaries.UnitBoundary;
import Interfaces.UnitCrud;
import Interfaces.UnitService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/org")
public class RestAPIController {
private UnitService unitService;

public RestAPIController(UnitService unitService)
{
    this.unitService=unitService;
}
@PostMapping
        ("/{existingParentUnitId}")
    public Mono<UnitBoundary>createUnit(@PathVariable(value ="existingParentUnitId")String existingParentUnitId,@RequestBody UnitBoundary unitBoundary)
{
    return this.unitService.create(existingParentUnitId,unitBoundary);
}


    @DeleteMapping
    public Mono<Void>deleteAllExceptOrg()
    {
        return  this.unitService.deleteAllNotOrg();
    }
}
