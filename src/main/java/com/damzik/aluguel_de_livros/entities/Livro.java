package com.damzik.aluguel_de_livros.entities;


import com.damzik.aluguel_de_livros.enums.LivroStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDateTime dataPublicacao;

    @ManyToOne
    private Autor autor;

    private LivroStatus livroStatus;
}