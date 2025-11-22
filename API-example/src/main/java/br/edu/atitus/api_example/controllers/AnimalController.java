package br.edu.atitus.api_example.controllers;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.edu.atitus.api_example.entities.AnimalEntity;
import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.repositories.UserRepository;
import br.edu.atitus.api_example.services.AnimalService;
import br.edu.atitus.api_example.services.UserService;

@RestController
@RequestMapping("/api/animais")
@CrossOrigin("*") 
public class AnimalController {

   
    private final UserRepository userRepository; 
    private final UserService userService;
    private final AnimalService animalService;

   
    public AnimalController(UserRepository userRepository, UserService userService, AnimalService animalService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.animalService = animalService;
    }
    

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnimalEntity> cadastrar(
        @RequestPart("animal") AnimalEntity animal, 
        @RequestPart("foto") MultipartFile foto
    ) {
        AnimalEntity novoAnimal = animalService.cadastrar(animal, foto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAnimal);
    }
    

    @GetMapping("/disponiveis")
    public ResponseEntity<List<AnimalEntity>> listarDisponiveis() {

    	return ResponseEntity.ok(animalService.listarDisponiveis());
    }
    

    @PatchMapping("/{id}/adotar")
    public ResponseEntity<AnimalEntity> adotar(
        @PathVariable UUID id, 
        Principal principal 
    ) {

    	UserEntity adotante = userService.findByEmail(principal.getName());
        
        AnimalEntity adotado = animalService.adotar(id, adotante);
        return ResponseEntity.ok(adotado);
    }
}