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
@Table(name = "animals")
public class AnimalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_animal;

    @Column(name = "animal_name", nullable = false, length = 50)
    private String animalName;

    @Column(nullable = false, length = 50)
    private String description;

    @ManyToOne
    @JoinColumn(name="species")
    private SpeciesModel species;

}
