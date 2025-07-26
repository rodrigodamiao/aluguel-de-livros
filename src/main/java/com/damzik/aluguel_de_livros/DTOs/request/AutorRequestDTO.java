package com.damzik.aluguel_de_livros.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AutorRequestDTO {

    @NotBlank(message = "O nome do autor não pode ser nulo")
    private String nome;
}
