package br.com.jlgregorio.rentacar.dto;

import br.com.jlgregorio.rentacar.model.SpeciesModel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnimalDTO extends RepresentationModel {

    private int id_animal;

    @NotBlank
    @Size(min = 1, max = 50)
    private String animalName;

    @NotBlank
    @Size(min = 1, max = 50)
    private String description;

    @ManyToOne
    @JoinColumn(name="species")
    private SpeciesModel species;

}
