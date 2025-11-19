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

    // Define o diretório base onde as fotos serão salvas.
    // Ele criará a pasta 'uploads' no diretório raiz do seu projeto.
    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    // Construtor: Garante que o diretório 'uploads' exista.
    public FileStorageService() {
        try {
            Files.createDirectories(this.fileStorageLocation);
            System.out.println("Diretório de upload criado em: " + this.fileStorageLocation.toString());
        } catch (Exception ex) {
            // Lançamos uma RuntimeException, pois a API não pode rodar sem o diretório de uploads.
            throw new RuntimeException("Não foi possível criar o diretório de upload.", ex);
        }
    }

    /**
     * Salva o arquivo de foto no sistema de arquivos.
     * @param file O arquivo (MultipartFile) enviado pelo cliente.
     * @return O caminho relativo que deve ser salvo no banco de dados.
     */
    public String salvarFoto(MultipartFile file) {
        // 1. Limpa e normaliza o nome original do arquivo (para segurança)
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        // 2. Cria um nome de arquivo único para evitar sobrescrever
        // Usamos um timestamp + nome original para garantir a unicidade.
        String fileName = System.currentTimeMillis() + "_" + originalFileName;

        try {
            // Verifica se o arquivo está vazio ou se o nome é inválido
            if (file.isEmpty() || fileName.contains("..")) {
                throw new RuntimeException("Nome de arquivo inválido ou arquivo vazio: " + fileName);
            }
            
            // 3. Constrói o caminho completo (uploads/nome_unico.jpg)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            
            // 4. Copia o arquivo para o destino, substituindo se já existir
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            // 5. Retorna o caminho da URL que o Front-end irá usar (ex: /uploads/170010000_foto.jpg)
            return "/uploads/" + fileName;
            
        } catch (IOException ex) {
            throw new RuntimeException("Falha ao armazenar o arquivo " + fileName, ex);
        }
    }
}
