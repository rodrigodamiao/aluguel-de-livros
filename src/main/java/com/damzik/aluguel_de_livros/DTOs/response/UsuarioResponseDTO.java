package com.damzik.aluguel_de_livros.DTOs.response;

import com.damzik.aluguel_de_livros.entities.Usuario;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;

    private LivroAlugadoResponseDTO livroAlugadoResponseDTO;

    public UsuarioResponseDTO(Usuario usuario){
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }
}
