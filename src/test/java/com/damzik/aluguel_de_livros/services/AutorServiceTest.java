package com.damzik.aluguel_de_livros.services;

import com.damzik.aluguel_de_livros.DTOs.request.AutorRequestDTO;
import com.damzik.aluguel_de_livros.DTOs.response.AutorResponseDTO;
import com.damzik.aluguel_de_livros.entities.Autor;
import com.damzik.aluguel_de_livros.entities.Livro;
import com.damzik.aluguel_de_livros.enums.LivroStatus;
import com.damzik.aluguel_de_livros.repositories.AutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

    @InjectMocks
    private AutorService autorService;

    @Mock
    private AutorRepository autorRepository;

    @Test
    void deveRetornarUmaListaDeAutores() {
        Autor autor = new Autor(1L, "Rodrigo", new ArrayList<>());
        List<Autor> autores = List.of(autor);

        Mockito.when(autorRepository.findAll()).thenReturn(autores);

        List<AutorResponseDTO> resposta = autorService.listarAutores();

        assertEquals(1, autores.size());
        assertEquals("Rodrigo", resposta.getFirst().getNome());
    }

    @Test
    void deveRetornarAutorPorId() {
        Autor autor = new Autor(1L, "Rodrigo", new ArrayList<>());
        Mockito.when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        AutorResponseDTO autorResponseDTO = autorService.findAutorById(1L);

        assertEquals("Rodrigo", autorResponseDTO.getNome());
        assertEquals(1L, autorResponseDTO.getId());
    }

    @Test
    void deveCadastrarAutor() {
        AutorRequestDTO autorRequestDTO = new AutorRequestDTO();
        autorRequestDTO.setNome("Novo Autor");

        Autor autor = new Autor(1L, "Novo Autor", new ArrayList<>());

        Mockito.when(autorRepository.save(Mockito.any(Autor.class))).thenReturn(autor);

        AutorResponseDTO autorResponseDTO = autorService.cadastrarAutor(autorRequestDTO);

        assertEquals("Novo Autor", autorResponseDTO.getNome());
        assertEquals(1L, autorResponseDTO.getId());
    }

    @Test
    void deveDeletarAutorComLivrosDisponiveis() {
        Livro livro = new Livro();
        livro.setLivroStatus(LivroStatus.DISPONIVEL);

        Autor autor = new Autor(1L, "Rodrigo", List.of(livro));
        Mockito.when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        autorService.deleteAutor(1L);

        Mockito.verify(autorRepository).delete(autor);
    }

    @Test
    void deveLancarExcecaoAoDeletarAutorComLivroAlugado(){
        Livro livro = new Livro();
        livro.setLivroStatus(LivroStatus.ALUGADO);

        Autor autor = new Autor(1L, "Rodrigo", List.of(livro));
        Mockito.when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        assertThrows(IllegalStateException.class, () -> autorService.deleteAutor(1L));
    }
}