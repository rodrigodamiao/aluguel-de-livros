package com.damzik.aluguel_de_livros.DTOs.response;

import com.damzik.aluguel_de_livros.DTOs.request.AutorRequestDTO;
import com.damzik.aluguel_de_livros.entities.Livro;
import com.damzik.aluguel_de_livros.enums.LivroStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class LivroResponseDTO {

    private Long id;
    private String nome;
    private LocalDateTime dataPublicacao;

    private AutorResponseDTO autorResponseDTO;

    private LivroStatus livroStatus;

    public LivroResponseDTO(Livro livro){
        this.id = livro.getId();
        this.nome = livro.getNome();
        this.dataPublicacao = livro.getDataPublicacao();
        this.autorResponseDTO = new AutorResponseDTO(livro.getAutor());
        this.livroStatus = livro.getLivroStatus();
    }
}
