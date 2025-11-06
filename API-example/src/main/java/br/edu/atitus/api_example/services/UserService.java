package br.edu.atitus.api_example.services;

import br.edu.atitus.api_example.entities.UserEntity;

public class UserService {

	public UserEntity save(UserEntity user) throws Exception {
		if (user == null)
			throw new Exception("Objeto Nulo");
		if (user.getName() == null || user.getName().isEmpty())
			throw new Exception("Nome Inválido");
		user.setName(user.getName().trim());
		
		if (user.getEmail() == null || user.getEmail().isEmpty())
			throw new Exception("E-mail Inválido");
		user.setEmail(user.getEmail().trim());
		
		if (user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().length() < 8)
			throw new Exception("Password Inválido");
		
		//TODO criar hash de senha
		
		//TODO validar permissão
		
		
		
		
	
		
		//TODO enviar para a camada repository
		return user;
	}
	
}
