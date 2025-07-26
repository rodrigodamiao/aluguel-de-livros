package com.damzik.aluguel_de_livros.DTOs.response;

import com.damzik.aluguel_de_livros.entities.Autor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class AutorResponseDTO {

    private Long id;
    private String nome;
    private List<LivroResponseDTO> livroResponseDTOList = new ArrayList<>();

    public AutorResponseDTO(Autor autor){
        this.id = autor.getId();
        this.nome = autor.getNome();
        this.livroResponseDTOList = autor.getLivros().stream().map(LivroResponseDTO::new).toList();
    }
}
