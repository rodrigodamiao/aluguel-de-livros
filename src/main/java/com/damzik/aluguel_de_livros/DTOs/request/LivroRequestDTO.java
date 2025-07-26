package com.damzik.aluguel_de_livros.DTOs.request;

import com.damzik.aluguel_de_livros.entities.Autor;
import com.damzik.aluguel_de_livros.enums.LivroStatus;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class LivroRequestDTO {
    @NotBlank(message = "O nome do livro não pode ser nulo")
    private String nome;

    @Past(message = "A data de publicação deve estar no passado")
    private LocalDateTime dataPublicacao;

    @NotNull(message = "O id do autor não pode ser nulo")
    private Long autorId;

    private LivroStatus livroStatus;
}
