package com.example.crud.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.entity.Usuario;
import com.example.crud.repository.UsuarioRepository;

@RestController

public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/criar-usuario")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario novoUsuario) {

        Usuario usuarioCriado = usuarioRepository.save(novoUsuario);
        System.out.println("usuario criado:" + usuarioCriado);

        return ResponseEntity.ok(usuarioCriado);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        System.out.println("username:" + username + " password: " + password);

        Usuario usuario = usuarioRepository.findByUsername(username);
        System.out.println("usuario:" + usuario);

        if (usuario != null && usuario.getPassword().equals(password)) {
            // Login bem-sucedido
            return ResponseEntity.ok().build();
        } else {
            // Login falhou
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
