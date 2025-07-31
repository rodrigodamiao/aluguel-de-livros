package com.damzik.aluguel_de_livros.services;

import com.damzik.aluguel_de_livros.DTOs.request.AutorRequestDTO;
import com.damzik.aluguel_de_livros.DTOs.response.AutorResponseDTO;
import com.damzik.aluguel_de_livros.entities.Autor;
import com.damzik.aluguel_de_livros.entities.Livro;
import com.damzik.aluguel_de_livros.entities.Usuario;
import com.damzik.aluguel_de_livros.enums.LivroStatus;
import com.damzik.aluguel_de_livros.repositories.AutorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorService {
    private final AutorRepository autorRepository;

    // Listar autores
    public List<AutorResponseDTO> listarAutores(){
        return autorRepository.findAll().stream().map(AutorResponseDTO::new).toList();
    }

    // Find autor by id
    public AutorResponseDTO findAutorById(Long id){
        return new AutorResponseDTO(autorRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    // Cadastrar Autor
    public AutorResponseDTO cadastrarAutor(AutorRequestDTO autor){
        Autor novoAutor = new Autor();
        novoAutor.setNome(autor.getNome());

        return new AutorResponseDTO(autorRepository.save(novoAutor));
    }

    // Deletar Autor
    public void deleteAutor(Long id){
        Autor autor = autorRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        for(Livro livro : autor.getLivros()){
            if(livro.getLivroStatus() == LivroStatus.ALUGADO) throw new IllegalStateException("Não é possível deletar o Autor com livros pertencentes a ele alugados.");
        }

        autorRepository.delete(autor);
    }
}
