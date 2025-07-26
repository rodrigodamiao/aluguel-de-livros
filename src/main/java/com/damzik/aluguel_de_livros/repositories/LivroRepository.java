package com.damzik.aluguel_de_livros.repositories;

import com.damzik.aluguel_de_livros.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
