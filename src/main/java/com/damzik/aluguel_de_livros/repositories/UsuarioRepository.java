package com.damzik.aluguel_de_livros.repositories;

import com.damzik.aluguel_de_livros.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
