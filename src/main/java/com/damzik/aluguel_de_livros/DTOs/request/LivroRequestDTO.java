package com.damzik.aluguel_de_livros.DTOs.request;

import com.damzik.aluguel_de_livros.entities.Autor;
import com.damzik.aluguel_de_livros.enums.LivroStatus;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class LivroRequestDTO {
    private String nome;
    private LocalDateTime dataPublicacao;
    private Long autorId;
    private LivroStatus livroStatus;
}
