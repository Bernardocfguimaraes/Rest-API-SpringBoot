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

    // Injeção de dependências
    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private FileStorageService fileStorageService; 

    // ==========================================================
    // 1. CADASTRO DE ANIMAL (Com Upload)
    // ==========================================================
    /**
     * Salva a foto, define o caminho na entidade e persiste o animal no banco.
     */
    public AnimalEntity cadastrar(AnimalEntity animal, MultipartFile foto) {
        // Validação simples (Pode ser mais robusta)
        if (animal == null || animal.getNome() == null || foto == null || foto.isEmpty()) {
             throw new RuntimeException("Dados do animal ou foto inválidos.");
        }

        // 1. Salva a foto no sistema de arquivos e obtém o caminho
        String fotoPath = fileStorageService.salvarFoto(foto);
        
        // 2. Define o caminho da foto na entidade
        animal.setFotoPath(fotoPath);
        
        // 3. Define o status inicial (caso não tenha sido definido no Controller)
        animal.setAdotado(false);
        
        // 4. Salva no banco de dados
        return animalRepository.save(animal);
    }
    
    // ==========================================================
    // 2. LISTAGEM PARA O MAPA
    // ==========================================================
    /**
     * Retorna todos os animais que ainda não foram adotados.
     */
    public List<AnimalEntity> listarDisponiveis() {
        // O método 'findByIsAdotadoFalse' é gerado automaticamente pelo Spring Data JPA
        return animalRepository.findByIsAdotadoFalse();
    }


    // ==========================================================
    // 3. LÓGICA DE ADOÇÃO
    // ==========================================================
    /**
     * Marca o animal como adotado e associa o usuário (adotante) logado.
     */
    public AnimalEntity adotar(UUID animalId, UserEntity adotante) {
        // 1. Busca o animal pelo ID
        AnimalEntity animal = animalRepository.findById(animalId)
            .orElseThrow(() -> new RuntimeException("Animal não encontrado."));
            
        // 2. Regra de negócio: evita adotar um animal já adotado
        if (animal.isAdotado()) {
            throw new RuntimeException("Este animal já foi adotado por outro usuário!");
        }

        // 3. Associa o adotante e muda o status
        animal.setAdotado(true);
        animal.setAdotante(adotante); 

        // 4. Salva as mudanças no banco de dados
        return animalRepository.save(animal);
    }
}