package br.com.jlgregorio.rentacar.controller;

import br.com.jlgregorio.rentacar.dto.SpeciesDTO;
import br.com.jlgregorio.rentacar.service.SpeciesService;
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
@RequestMapping("/api/speciess")
@Tag(name = "speciess", description = "This endpoint manages speciess")
public class SpeciesController {

    @Autowired
    private SpeciesService service;

    @PostMapping
    @Operation(summary = "Persists a new Publisher in database", tags = {"speciess"}, responses = {
            @ApiResponse(description = "Success!", responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpeciesDTO.class))
            })
    })
    public SpeciesDTO create(@RequestBody SpeciesDTO dto){
        return service.create(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Publisher using the ID", tags = {"speciess"}, responses = {
            @ApiResponse(description = "Success!", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema =
                        @Schema(implementation = SpeciesDTO.class)
                    )
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
    })
    public SpeciesDTO findById(@PathVariable("id") int id_species){
        SpeciesDTO dto = service.findById(id_species);
        //..adding HATEOAS link
        buildEntityLink(dto);
        return dto;
    }

    @GetMapping
    public ResponseEntity<PagedModel<SpeciesDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<SpeciesDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "speciesName"));

        Page<SpeciesDTO> speciess = service.findAll(pageable);

        for (SpeciesDTO species:speciess){
            buildEntityLink(species);
        }
        return new ResponseEntity(assembler.toModel(speciess), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<PagedModel<SpeciesDTO>> findByName(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<SpeciesDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "speciesName"));

        Page<SpeciesDTO> speciess = service.findByName(name, pageable);

        for (SpeciesDTO species:speciess){
            buildEntityLink(species);
        }
        return new ResponseEntity(assembler.toModel(speciess), HttpStatus.OK);
    }

    @PutMapping
    public SpeciesDTO update(@RequestBody SpeciesDTO dto){
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id_species){
        SpeciesDTO dto = service.findById(id_species);
        service.delete(dto);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    public void buildEntityLink(SpeciesDTO species){
        //..self link
        species.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()
                        ).findById(species.getId_species())
                ).withSelfRel()
        );
    }


}
