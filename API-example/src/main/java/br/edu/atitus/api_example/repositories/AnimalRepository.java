package br.edu.atitus.api_example.repositories;

import java.util.List;
import java.util.UUID;
import java.util.Optional; // Importe Optional para o findById correto

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.api_example.entities.AnimalEntity;

public interface AnimalRepository extends JpaRepository<AnimalEntity, UUID> {
    
    // Método para listar apenas os disponíveis (não adotados)
    List<AnimalEntity> findByIsAdotadoFalse();

    // Os métodos findById e save não precisam ser declarados, mas se você precisar 
    // do findById com Optional, a JpaRepository já o fornece.

    // A forma correta do método herdado que o Spring Data JPA usa é:
    // Optional<AnimalEntity> findById(UUID id);
    // AnimalEntity save(AnimalEntity entity); 
}
