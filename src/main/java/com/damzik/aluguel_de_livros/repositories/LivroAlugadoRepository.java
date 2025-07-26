package com.damzik.aluguel_de_livros.repositories;

import com.damzik.aluguel_de_livros.entities.LivroAlugado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroAlugadoRepository extends JpaRepository<LivroAlugado, Long> {
}
