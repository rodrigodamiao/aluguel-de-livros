package com.damzik.aluguel_de_livros.DTOs.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "O nome do usuário não pode ser nulo")
    private String nome;

    @Email
    @NotBlank(message = "O email não pode ser nulo")
    private String email;
}
