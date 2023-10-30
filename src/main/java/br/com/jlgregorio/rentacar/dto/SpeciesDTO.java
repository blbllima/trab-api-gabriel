package br.com.jlgregorio.rentacar.dto;

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
public class SpeciesDTO extends RepresentationModel {

    private int id_species;

    @NotBlank
    @Size(min = 1, max = 50)
    private String speciesName;

    @NotBlank
    @Size(min = 1, max = 20)
    private String naturalRange;

    @NotBlank
    @Size(min = 5, max = 50)
    private int averageSize;

}
