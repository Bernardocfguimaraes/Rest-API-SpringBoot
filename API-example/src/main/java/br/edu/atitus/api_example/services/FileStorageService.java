package br.edu.atitus.api_example.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {


    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();


    public FileStorageService() {
        try {
            Files.createDirectories(this.fileStorageLocation);
            System.out.println("Diretório de upload criado em: " + this.fileStorageLocation.toString());
        } catch (Exception ex) {
            throw new RuntimeException("Não foi possível criar o diretório de upload.", ex);
        }
    }

    public String salvarFoto(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        String fileName = System.currentTimeMillis() + "_" + originalFileName;

        try {
 
            if (file.isEmpty() || fileName.contains("..")) {
                throw new RuntimeException("Nome de arquivo inválido ou arquivo vazio: " + fileName);
            }
            
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            return "/uploads/" + fileName;
            
        } catch (IOException ex) {
            throw new RuntimeException("Falha ao armazenar o arquivo " + fileName, ex);
        }
    }
}
