package br.com.jlgregorio.rentacar.repository;

import br.com.jlgregorio.rentacar.model.SpeciesModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesRepository extends JpaRepository<SpeciesModel, Integer> {

    public Page<SpeciesModel> findAll(Pageable pageable);

    public Page<SpeciesModel> findBySpeciesNameStartsWithIgnoreCase(String name, Pageable pageable);

}
