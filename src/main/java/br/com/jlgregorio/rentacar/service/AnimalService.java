package br.com.jlgregorio.rentacar.service;

import br.com.jlgregorio.rentacar.dto.AnimalDTO;
import br.com.jlgregorio.rentacar.exception.ResourceNotFoundException;
import br.com.jlgregorio.rentacar.mapper.CustomModelMapper;
import br.com.jlgregorio.rentacar.model.AnimalModel;
import br.com.jlgregorio.rentacar.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository repository;

    public AnimalDTO create(AnimalDTO dto){
        AnimalModel model = CustomModelMapper.parseObject(dto, AnimalModel.class);
        return CustomModelMapper.parseObject(repository.save(model), AnimalDTO.class);
    }

    public AnimalDTO findById(int id){
        AnimalModel model = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(null));
        return CustomModelMapper.parseObject(model, AnimalDTO.class);
    }

    public Page<AnimalDTO> findAll(Pageable pageable){
        var page = repository.findAll(pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, AnimalDTO.class));
    }

    public AnimalDTO update(AnimalDTO dto){
        AnimalModel model = repository.findById(dto.getId_animal()).orElseThrow(
                () -> new ResourceNotFoundException(null));
        model = CustomModelMapper.parseObject(dto, AnimalModel.class);
        return CustomModelMapper.parseObject(repository.save(model), AnimalDTO.class);
    }

    public void delete(AnimalDTO dto){
        AnimalModel model = repository.findById(dto.getId_animal()).orElseThrow(
                () -> new ResourceNotFoundException(null)
        );
        repository.delete(model);
    }

    public Page<AnimalDTO> findByName(String name, Pageable pageable){
        var page = repository.findByAnimalNameStartsWithIgnoreCase(name, pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, AnimalDTO.class));
    }

}
