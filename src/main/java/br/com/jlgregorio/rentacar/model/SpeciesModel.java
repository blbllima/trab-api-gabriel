package br.com.jlgregorio.rentacar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "species")
public class SpeciesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_species;

    @Column(name = "species_name", nullable = false, length = 50)
    private String speciesName;

    @Column(name = "natural_range", nullable = false, length = 20)
    private String naturalRange; /*A região geográfica onde a espécie é tipicamente encontrada na natureza */

    @Column(name = "average_size", nullable = false)
    private int averageSize; /*The average size or measurements of adult individuals of the species.*/

}
