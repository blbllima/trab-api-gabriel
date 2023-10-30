package br.com.jlgregorio.rentacar.repository;

import br.com.jlgregorio.rentacar.model.AnimalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalModel, Integer> {

    public Page<AnimalModel> findAll(Pageable pageable);

    public Page<AnimalModel> findByAnimalNameStartsWithIgnoreCase(String name, Pageable pageable);

}
