package br.edu.atitus.api_example.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.edu.atitus.api_example.entities.PointEntity;
import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.repositories.PointRepository;
import jakarta.transaction.Transactional;

@Service
public class PointService {

	private final PointRepository repository;
	private final UserService userService; 

	public PointService(PointRepository repository, UserService userService) {
		super();
		this.repository = repository;
		this.userService = userService;
	}
	
	
	@Transactional
	public PointEntity save(PointEntity point) throws Exception {
		if(point == null)
			throw new Exception("Objeto Nulo");
		
		if(point.getDescription() == null || point.getDescription().isEmpty())
			throw new Exception("Descrição Inválida");

		if(point.getLatitude() < -90 || point.getLatitude() > 90)
			throw new Exception("Latitude Inválida (deve estar entre -90 e 90)");
		if(point.getLongitude() < -180 || point.getLongitude() > 180)
			throw new Exception("Longitude Inválida (deve estar entre -180 e 180)");
		
		UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		point.setUser(userAuth);	
		
		return repository.save(point);
	}
    

	public List<PointEntity> listarPorUsuario(String emailUsuario) {
	 
	    UserEntity user = userService.findByEmail(emailUsuario); 
	    

	    return repository.findByUser(user); 
	}

    @Transactional
    public PointEntity atualizar(UUID id, PointEntity novoPonto, String emailUsuario) throws Exception {
        
        
        PointEntity pontoExistente = repository.findById(id)
            .orElseThrow(() -> new Exception("Ponto não encontrado."));

 
        UserEntity userLogado = userService.findByEmail(emailUsuario);


        if (!pontoExistente.getUser().getId().equals(userLogado.getId())) {
            throw new Exception("Acesso negado. O ponto não pertence a este usuário.");
        }
        
        pontoExistente.setDescription(novoPonto.getDescription());
        pontoExistente.setLatitude(novoPonto.getLatitude());
        pontoExistente.setLongitude(novoPonto.getLongitude());

        return repository.save(pontoExistente);
    }
	
	@Transactional
	public void deleteById(UUID id) throws Exception{
		var pointInBD = repository.findById(id).orElseThrow(() -> new Exception("Ponto não localizado"));
		
		UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!pointInBD.getUser().getId().equals(userAuth.getId()))
			throw new Exception("Você não tem permissão para essa ação");
		
		repository.deleteById(id);
	}
	
	public java.util.List<PointEntity> findAll(){
		return repository.findAll();
	}
}