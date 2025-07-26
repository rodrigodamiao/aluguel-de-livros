package com.damzik.aluguel_de_livros.services;

import com.damzik.aluguel_de_livros.DTOs.request.LivroRequestDTO;
import com.damzik.aluguel_de_livros.entities.Autor;
import com.damzik.aluguel_de_livros.entities.Livro;
import com.damzik.aluguel_de_livros.enums.LivroStatus;
import com.damzik.aluguel_de_livros.repositories.AutorRepository;
import com.damzik.aluguel_de_livros.repositories.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    // Listas Livros
    public List<Livro> listarLivros(){
        return livroRepository.findAll();
    }

    // Find livro by id
    public Livro findLivroById(Long id){
        return livroRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    // Cadastrar Livro
    public Livro cadastrarLivro(LivroRequestDTO livroRequestDTO){
        Livro novoLivro = new Livro();
        Autor autor = autorRepository.findById(livroRequestDTO.getAutorId())
                        .orElseThrow(EntityNotFoundException::new);

        novoLivro.setNome(livroRequestDTO.getNome());
        novoLivro.setDataPublicacao(livroRequestDTO.getDataPublicacao());
        novoLivro.setAutor(autor);
        novoLivro.setLivroStatus(LivroStatus.DISPONIVEL);

        return livroRepository.save(novoLivro);
    }

    // Deletar Livro
    public void deletarLivro(Long id){
        Livro livro = livroRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        if(livro.getLivroStatus() != LivroStatus.DISPONIVEL) {
            throw new IllegalStateException("Não é possível deletar um livro que está alugado.");
        }

        livroRepository.delete(livro);
    }
}