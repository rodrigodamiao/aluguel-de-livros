package com.damzik.aluguel_de_livros.config;


import com.damzik.aluguel_de_livros.entities.Autor;
import com.damzik.aluguel_de_livros.entities.Livro;
import com.damzik.aluguel_de_livros.entities.Usuario;
import com.damzik.aluguel_de_livros.enums.LivroStatus;
import com.damzik.aluguel_de_livros.repositories.AutorRepository;
import com.damzik.aluguel_de_livros.repositories.LivroRepository;
import com.damzik.aluguel_de_livros.repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public DataLoader(AutorRepository autorRepository, LivroRepository livroRepository, UsuarioRepository usuarioRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostConstruct
    public void init() {
        // Criar autores
        Autor autor1 = new Autor(null, "Autor A", null);
        Autor autor2 = new Autor(null, "Autor B", null);
        Autor autor3 = new Autor(null, "Autor C", null);
        autorRepository.saveAll(List.of(autor1, autor2, autor3));

        // Criar livros
        Livro livro1 = new Livro(null, "Clean Code", LocalDateTime.now().minusYears(1), autor1, LivroStatus.DISPONIVEL);
        Livro livro2 = new Livro(null, "Effective Java", LocalDateTime.now().minusYears(2), autor2, LivroStatus.DISPONIVEL);
        Livro livro3 = new Livro(null, "Spring in Action", LocalDateTime.now().minusMonths(6), autor3, LivroStatus.DISPONIVEL);
        livroRepository.saveAll(List.of(livro1, livro2, livro3));

        // Criar usuários
        Usuario user1 = new Usuario(null, "Rodrigo", "rodrigo@email.com", null);
        Usuario user2 = new Usuario(null, "Ana", "ana@email.com", null);
        Usuario user3 = new Usuario(null, "Carlos", "carlos@email.com", null);
        usuarioRepository.saveAll(List.of(user1, user2, user3));
    }
}