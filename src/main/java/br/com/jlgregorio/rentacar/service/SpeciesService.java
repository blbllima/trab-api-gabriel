package br.com.jlgregorio.rentacar.service;

import br.com.jlgregorio.rentacar.dto.SpeciesDTO;
import br.com.jlgregorio.rentacar.exception.ResourceNotFoundException;
import br.com.jlgregorio.rentacar.mapper.CustomModelMapper;
import br.com.jlgregorio.rentacar.model.SpeciesModel;
import br.com.jlgregorio.rentacar.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SpeciesService {

    @Autowired
    private SpeciesRepository repository;

    public SpeciesDTO create(SpeciesDTO dto){
        SpeciesModel model = CustomModelMapper.parseObject(dto, SpeciesModel.class);
        return CustomModelMapper.parseObject(repository.save(model), SpeciesDTO.class);
    }

    public SpeciesDTO findById(int id){
        SpeciesModel model = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(null));
        return CustomModelMapper.parseObject(model, SpeciesDTO.class);
    }

    public Page<SpeciesDTO> findAll(Pageable pageable){
        var page = repository.findAll(pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, SpeciesDTO.class));
    }

    public SpeciesDTO update(SpeciesDTO dto){
        SpeciesModel model = repository.findById(dto.getId_species()).orElseThrow(
                () -> new ResourceNotFoundException(null));
        model = CustomModelMapper.parseObject(dto, SpeciesModel.class);
        return CustomModelMapper.parseObject(repository.save(model), SpeciesDTO.class);
    }

    public void delete(SpeciesDTO dto){
        SpeciesModel model = repository.findById(dto.getId_species()).orElseThrow(
                () -> new ResourceNotFoundException(null)
        );
        repository.delete(model);
    }

    public Page<SpeciesDTO> findByName(String name, Pageable pageable){
        var page = repository.findBySpeciesNameStartsWithIgnoreCase(name, pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, SpeciesDTO.class));
    }

}
