package br.com.jlgregorio.rentacar.controller;

import br.com.jlgregorio.rentacar.dto.AnimalDTO;
import br.com.jlgregorio.rentacar.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animals")
@Tag(name = "Animals", description = "This endpoint manages Animals")
public class AnimalController {

    @Autowired
    private AnimalService service;

    @PostMapping
    @Operation(summary = "Persists a new Animal in database", tags = {"Animals"}, responses = {
            @ApiResponse(description = "Success!", responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnimalDTO.class))
            })
    })
    public AnimalDTO create(@RequestBody AnimalDTO dto){
        return service.create(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Animal using the ID", tags = {"Animals"}, responses = {
            @ApiResponse(description = "Success!", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema =
                        @Schema(implementation = AnimalDTO.class)
                    )
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
    })
    public AnimalDTO findById(@PathVariable("id") int id_animal){
        AnimalDTO dto = service.findById(id_animal);
        //..adding HATEOAS link
        buildEntityLink(dto);
        return dto;
    }

    @GetMapping
    public ResponseEntity<PagedModel<AnimalDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<AnimalDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "animalName"));

        Page<AnimalDTO> animals = service.findAll(pageable);

        for (AnimalDTO animal:animals){
            buildEntityLink(animal);
        }
        return new ResponseEntity(assembler.toModel(animals), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<PagedModel<AnimalDTO>> findByName(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<AnimalDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "animalName"));

        Page<AnimalDTO> animals = service.findByName(name, pageable);

        for (AnimalDTO animal:animals){
            buildEntityLink(animal);
        }
        return new ResponseEntity(assembler.toModel(animals), HttpStatus.OK);
    }

    @PutMapping
    public AnimalDTO update(@RequestBody AnimalDTO dto){
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id_animal){
        AnimalDTO dto = service.findById(id_animal);
        service.delete(dto);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    public void buildEntityLink(AnimalDTO animal){
        //..self link
        animal.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()
                        ).findById(animal.getId_animal())
                ).withSelfRel()
        );
    }

//    public void buildCollectionLink(CollectionModel<AnimalDTO> animals){
//        animals.add(
//                WebMvcLinkBuilder.linkTo(
//                        WebMvcLinkBuilder.methodOn(this.getClass()).findAll()
//                ).withRel(IanaLinkRelations.COLLECTION)
//        );
//    }

}
