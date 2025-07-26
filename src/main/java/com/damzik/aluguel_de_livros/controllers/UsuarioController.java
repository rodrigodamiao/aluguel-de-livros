package com.damzik.aluguel_de_livros.controllers;

import com.damzik.aluguel_de_livros.DTOs.response.UsuarioResponseDTO;
import com.damzik.aluguel_de_livros.entities.Usuario;
import com.damzik.aluguel_de_livros.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Listar Livros
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getUsuarios(){
        return ResponseEntity.ok().body(usuarioService.listarUsuarios());
    }

    // Find user by id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUserById(@PathVariable Long id){
        return ResponseEntity.ok().body(usuarioService.findUserById(id));
    }

    // Cadastrar Usuário
    @PostMapping()
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(usuario));
    }

    // Alugar Livro para o usuario
    @PostMapping("/alugar/{usuarioId}/{livroId}")
    public ResponseEntity<UsuarioResponseDTO> alugarLivro(@PathVariable Long usuarioId, @PathVariable Long livroId){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.alugarLivro(usuarioId,livroId));
    }

    // Devolver Livro do usuario
    @PutMapping("/devolver/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> devolverLivro(@PathVariable Long usuarioId){
        return ResponseEntity.ok().body(usuarioService.devolverLivro(usuarioId));
    }

    // Deletar Usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
