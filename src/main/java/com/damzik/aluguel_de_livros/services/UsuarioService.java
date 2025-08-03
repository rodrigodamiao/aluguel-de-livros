package com.damzik.aluguel_de_livros.services;

import com.damzik.aluguel_de_livros.DTOs.request.UsuarioRequestDTO;
import com.damzik.aluguel_de_livros.DTOs.response.UsuarioResponseDTO;
import com.damzik.aluguel_de_livros.entities.Livro;
import com.damzik.aluguel_de_livros.entities.LivroAlugado;
import com.damzik.aluguel_de_livros.entities.Usuario;
import com.damzik.aluguel_de_livros.enums.LivroStatus;
import com.damzik.aluguel_de_livros.repositories.LivroAlugadoRepository;
import com.damzik.aluguel_de_livros.repositories.LivroRepository;
import com.damzik.aluguel_de_livros.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;
    private final LivroAlugadoRepository livroAlugadoRepository;

    // Listar Usuários
    public List<UsuarioResponseDTO> listarUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream().map(UsuarioResponseDTO::new).toList();
    }

    // Find user by id
    public UsuarioResponseDTO findUserById(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return new UsuarioResponseDTO(usuario);
    }

    // Cadastrar Usuário
    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO usuarioRequestDTO){
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(usuarioRequestDTO.getNome());
        novoUsuario.setEmail(usuarioRequestDTO.getEmail());

        return new UsuarioResponseDTO(usuarioRepository.save(novoUsuario));
    }

    // Alugar Livro
    public UsuarioResponseDTO alugarLivro(Long usuarioId, Long livroId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow((EntityNotFoundException::new));
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow((EntityNotFoundException::new));

        if(usuario.getLivroAlugado() != null){
            throw new IllegalStateException("Usuário já possui um livro alugado.");
        }
        if(livro.getLivroStatus() == LivroStatus.ALUGADO){
            throw new IllegalStateException("Esse livro já está alugado por outra pessoa.");
        }

        livro.setLivroStatus(LivroStatus.ALUGADO);
        livroRepository.save(livro);

        LivroAlugado livroAlugado = new LivroAlugado();
        livroAlugado.setLivroId(livroId);
        livroAlugado.setUserId(usuarioId);
        livroAlugado.setNomeDoLivro(livro.getNome());
        livroAlugado.setDataDoAluguel(LocalDateTime.now());
        livroAlugado.setDataLimiteDeDevolucao(livroAlugado.getDataDoAluguel().plusDays(7));

        livroAlugadoRepository.save(livroAlugado);

        usuario.setLivroAlugado(livroAlugado);

        return new UsuarioResponseDTO(usuarioRepository.save(usuario));
    }

    // Devolver Livro
    public UsuarioResponseDTO devolverLivro(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        LivroAlugado livroAlugado = usuario.getLivroAlugado();
        if (livroAlugado == null) {
            throw new IllegalStateException("Usuário não possui livro alugado.");
        }

        Livro livro = livroRepository.findById(livroAlugado.getLivroId())
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        livro.setLivroStatus(LivroStatus.DISPONIVEL);

        usuario.setLivroAlugado(null);

        livroAlugadoRepository.delete(livroAlugado);

        return new UsuarioResponseDTO(usuarioRepository.save(usuario));
    }

    // Deletar Usuário
    public void deletarUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if(usuario.getLivroAlugado() != null) throw new IllegalStateException("Não é possível deletar um usuário com livro alugado");

        usuarioRepository.delete(usuario);
    }
}
