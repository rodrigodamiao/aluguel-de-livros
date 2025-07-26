package com.damzik.aluguel_de_livros.DTOs.response;

import com.damzik.aluguel_de_livros.entities.Livro;
import com.damzik.aluguel_de_livros.entities.LivroAlugado;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class LivroAlugadoResponseDTO {

    private Long id;
    private Long userId;
    private Long livroId;
    private String nomeDoLivro;
    private LocalDateTime dataDoAluguel;
    private LocalDateTime dataLimiteDeDevolucao;

    public LivroAlugadoResponseDTO(LivroAlugado livroAlugado){
        this.id = livroAlugado.getId();
        this.userId = livroAlugado.getUserId();
        this.livroId = livroAlugado.getLivroId();
        this.nomeDoLivro = livroAlugado.getNomeDoLivro();
        this.dataDoAluguel = livroAlugado.getDataDoAluguel();
        this.dataLimiteDeDevolucao = livroAlugado.getDataLimiteDeDevolucao();
    }

}
