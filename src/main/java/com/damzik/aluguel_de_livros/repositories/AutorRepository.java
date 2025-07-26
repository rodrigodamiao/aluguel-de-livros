package com.damzik.aluguel_de_livros.repositories;

import com.damzik.aluguel_de_livros.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor,Long> {
}
