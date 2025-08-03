package com.damzik.aluguel_de_livros.services;

import com.damzik.aluguel_de_livros.DTOs.request.LivroRequestDTO;
import com.damzik.aluguel_de_livros.DTOs.response.LivroResponseDTO;
import com.damzik.aluguel_de_livros.entities.Autor;
import com.damzik.aluguel_de_livros.entities.Livro;
import com.damzik.aluguel_de_livros.enums.LivroStatus;
import com.damzik.aluguel_de_livros.repositories.AutorRepository;
import com.damzik.aluguel_de_livros.repositories.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

    @ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;
    @Mock
    private AutorRepository autorRepository;

    @Test
    void deveRetornarUmaListaDeLivros() {
        Autor autor = new Autor(1L, "Rodrigo", new ArrayList<>());
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setNome("Clean Code");
        livro.setDataPublicacao(LocalDateTime.of(2024, 7, 25, 15, 0));
        livro.setAutor(autor);
        livro.setLivroStatus(LivroStatus.DISPONIVEL);
        List<Livro> livros = List.of(livro);

        Mockito.when(livroRepository.findAll()).thenReturn(livros);

        List<LivroResponseDTO> livrosDTO = livroService.listarLivros();

        assertEquals(1L, livrosDTO.getFirst().getId());
        assertEquals("Clean Code", livrosDTO.getFirst().getNome());
        assertEquals(1, livrosDTO.size());
    }

    @Test
    void deveRetornarLivroPorId() {
        Autor autor = new Autor(1L, "Rodrigo", new ArrayList<>());
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setNome("Clean Code");
        livro.setDataPublicacao(LocalDateTime.of(2024, 7, 25, 15, 0));
        livro.setAutor(autor);
        livro.setLivroStatus(LivroStatus.DISPONIVEL);

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        LivroResponseDTO livroDTO = livroService.findLivroById(1L);

        assertEquals(1L, livroDTO.getId());
        assertEquals("Clean Code", livroDTO.getNome());
    }

    @Test
    void deveCadastrarLivro() {
        Autor autor = new Autor(1L, "Rodrigo", new ArrayList<>());

        LivroRequestDTO livroRequestDTO = new LivroRequestDTO();
        livroRequestDTO.setNome("Clean Code");
        livroRequestDTO.setDataPublicacao(LocalDateTime.of(2024, 7, 25, 15, 0));
        livroRequestDTO.setAutorId(1L);

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setNome(livroRequestDTO.getNome());
        livro.setDataPublicacao(livroRequestDTO.getDataPublicacao());
        livro.setAutor(autor);
        livro.setLivroStatus(LivroStatus.DISPONIVEL);

        Mockito.when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        Mockito.when(livroRepository.save(Mockito.any(Livro.class))).thenReturn(livro);

        LivroResponseDTO livroDTO = livroService.cadastrarLivro(livroRequestDTO);

        assertEquals(1L, livroDTO.getId());
        assertEquals("Clean Code", livroDTO.getNome());
    }

    @Test
    void deveDeletarLivroDisponivel() {
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setLivroStatus(LivroStatus.DISPONIVEL);

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        livroService.deletarLivro(1L);

        Mockito.verify(livroRepository).delete(livro);
    }

    @Test
    void deveLancarExcecaoAoDeletarLivroAlugado(){
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setLivroStatus(LivroStatus.ALUGADO);

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        assertThrows(IllegalStateException.class, () -> livroService.deletarLivro(1L));
    }
}