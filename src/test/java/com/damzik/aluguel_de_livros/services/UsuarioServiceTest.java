package com.damzik.aluguel_de_livros.services;

import com.damzik.aluguel_de_livros.DTOs.request.UsuarioRequestDTO;
import com.damzik.aluguel_de_livros.DTOs.response.UsuarioResponseDTO;
import com.damzik.aluguel_de_livros.entities.Autor;
import com.damzik.aluguel_de_livros.entities.Livro;
import com.damzik.aluguel_de_livros.entities.LivroAlugado;
import com.damzik.aluguel_de_livros.entities.Usuario;
import com.damzik.aluguel_de_livros.enums.LivroStatus;
import com.damzik.aluguel_de_livros.repositories.AutorRepository;
import com.damzik.aluguel_de_livros.repositories.LivroAlugadoRepository;
import com.damzik.aluguel_de_livros.repositories.LivroRepository;
import com.damzik.aluguel_de_livros.repositories.UsuarioRepository;
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
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private LivroAlugadoRepository livroAlugadoRepository;

    @Mock
    private AutorRepository autorRepository;

    @Test
    void deveListarUsuarios() {
        Usuario usuario = new Usuario(1L, "Rodrigo", "rodrigo@email.com", null);
        List<Usuario> usuarios = List.of(usuario);

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioResponseDTO> usuariosResponse = usuarioService.listarUsuarios();

        assertEquals(1L, usuariosResponse.getFirst().getId());
        assertEquals("Rodrigo", usuariosResponse.getFirst().getNome());
        assertEquals("rodrigo@email.com", usuariosResponse.getFirst().getEmail());
    }

    @Test
    void deveAcharUsuarioPorId() {
        Usuario usuario = new Usuario(1L, "Rodrigo", "rodrigo@email.com", null);

        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO usuarioResponse = usuarioService.findUserById(usuario.getId());

        assertEquals(1L, usuarioResponse.getId());
        assertEquals("Rodrigo", usuarioResponse.getNome());
        assertEquals("rodrigo@email.com", usuarioResponse.getEmail());
    }

    @Test
    void deveCadastrarUsuario() {
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNome("Rodrigo");
        usuarioRequestDTO.setEmail("rodrigo@email.com");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome(usuarioRequestDTO.getNome());
        usuario.setEmail(usuarioRequestDTO.getEmail());

        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO usuarioResponseDTO = usuarioService.cadastrarUsuario(usuarioRequestDTO);

        assertEquals(1L, usuarioResponseDTO.getId());
        assertEquals("Rodrigo", usuarioResponseDTO.getNome());
        assertEquals("rodrigo@email.com", usuarioResponseDTO.getEmail());
    }

    @Test
    void deveAlugarLivro() {
        Usuario usuario = new Usuario(1L, "Rodrigo", "rodrigo@email.com", null);
        Autor autor = new Autor(1L, "Rodrigo", new ArrayList<>());
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setNome("Clean Code");
        livro.setAutor(autor);
        livro.setLivroStatus(LivroStatus.DISPONIVEL);

        LivroAlugado livroAlugado = new LivroAlugado();
        livroAlugado.setId(10L);
        livroAlugado.setLivroId(1L);
        livroAlugado.setUserId(1L);
        livroAlugado.setNomeDoLivro("Clean Code");
        livroAlugado.setDataDoAluguel(LocalDateTime.now());
        livroAlugado.setDataLimiteDeDevolucao(livroAlugado.getDataDoAluguel().plusDays(7));

        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        Mockito.when(livroRepository.save(Mockito.any())).thenReturn(livro);
        Mockito.when(livroAlugadoRepository.save(Mockito.any())).thenReturn(livroAlugado);
        Mockito.when(usuarioRepository.save(Mockito.any())).thenReturn(usuario);

        UsuarioResponseDTO usuarioResponse = usuarioService.alugarLivro(1L, 1L);

        assertEquals("Rodrigo", usuarioResponse.getNome());
        assertEquals("Clean Code", usuarioResponse.getLivroAlugadoResponseDTO().getNomeDoLivro());
        assertEquals(LivroStatus.ALUGADO, livro.getLivroStatus());
    }


    @Test
    void deveDevolverLivro() {
        Usuario usuario = new Usuario(1L, "Rodrigo", "rodrigo@email.com", null);
        Autor autor = new Autor(1L, "Rodrigo", new ArrayList<>());
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setNome("Clean Code");
        livro.setAutor(autor);
        livro.setLivroStatus(LivroStatus.ALUGADO);

        LivroAlugado livroAlugado = new LivroAlugado();
        livroAlugado.setId(10L);
        livroAlugado.setLivroId(livro.getId());
        livroAlugado.setUserId(usuario.getId());
        livroAlugado.setNomeDoLivro(livro.getNome());
        livroAlugado.setDataDoAluguel(LocalDateTime.now());
        livroAlugado.setDataLimiteDeDevolucao(livroAlugado.getDataDoAluguel().plusDays(7));

        usuario.setLivroAlugado(livroAlugado);

        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        Mockito.when(usuarioRepository.save(Mockito.any())).thenReturn(usuario);

        UsuarioResponseDTO usuarioResponse = usuarioService.devolverLivro(1L);

        assertEquals("Rodrigo", usuarioResponse.getNome());
        assertNull(usuario.getLivroAlugado());
        assertEquals(LivroStatus.DISPONIVEL, livro.getLivroStatus());
    }

    @Test
    void deveDeletarUsuario() {
        Usuario usuario = new Usuario(1L, "Rodrigo", "rodrigo@email.com", null);

        Mockito.when(usuarioRepository.findById(usuario.getId()))
                .thenReturn(Optional.of(usuario));

        usuarioService.deletarUsuario(usuario.getId());

        Mockito.verify(usuarioRepository).delete(usuario);
    }
}