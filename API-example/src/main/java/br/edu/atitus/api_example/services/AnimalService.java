package br.edu.atitus.api_example.services;

import br.edu.atitus.api_example.entities.AnimalEntity;
import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.repositories.AnimalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private FileStorageService fileStorageService; 


    public AnimalEntity cadastrar(AnimalEntity animal, MultipartFile foto) {
        if (animal == null || animal.getNome() == null || foto == null || foto.isEmpty()) {
             throw new RuntimeException("Dados do animal ou foto inválidos.");
        }

        String fotoPath = fileStorageService.salvarFoto(foto);
        
        animal.setFotoPath(fotoPath);
        
        animal.setAdotado(false);
        
        return animalRepository.save(animal);
    }
    
    public List<AnimalEntity> listarDisponiveis() {

        return animalRepository.findByIsAdotadoFalse();
    }

    public AnimalEntity adotar(UUID animalId, UserEntity adotante) {

        AnimalEntity animal = animalRepository.findById(animalId)
            .orElseThrow(() -> new RuntimeException("Animal não encontrado."));
            
        if (animal.isAdotado()) {
            throw new RuntimeException("Este animal já foi adotado por outro usuário!");
        }

        animal.setAdotado(true);
        animal.setAdotante(adotante); 

        return animalRepository.save(animal);
    }
}