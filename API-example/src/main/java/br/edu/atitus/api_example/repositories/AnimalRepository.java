package br.edu.atitus.api_example.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.api_example.entities.AnimalEntity;

public interface AnimalRepository extends JpaRepository<AnimalEntity, UUID> {
    
    List<AnimalEntity> findByIsAdotadoFalse();
}


