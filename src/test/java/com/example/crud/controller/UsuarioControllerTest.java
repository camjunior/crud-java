package com.example.crud.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.crud.entity.Usuario;
import com.example.crud.repository.UsuarioRepository;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void testCriarUsuario() throws Exception {
        // Criação do novo usuário de teste
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername("usuarioTeste");
        novoUsuario.setPassword("senhaTeste");

        // Configuração do comportamento do mock do UsuarioRepository
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(novoUsuario);

        // Requisição POST para criar o novo usuário
        mockMvc.perform(MockMvcRequestBuilders.post("/criar-usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"usuarioTeste\", \"password\":\"senhaTeste\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("usuarioTeste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("senhaTeste"));
    }

    @Test
    public void testLogin() throws Exception {
        // Criação do usuário de teste no banco de dados
        Usuario usuarioTeste = new Usuario();
        usuarioTeste.setUsername("usuarioTeste");
        usuarioTeste.setPassword("senhaTeste");

        // Configuração do comportamento do mock do UsuarioRepository
        when(usuarioRepository.findByUsername("usuarioTeste")).thenReturn(usuarioTeste);

        // Mapa com os dados de login
        Map<String, String> loginData = new HashMap<>();
        loginData.put("username", "usuarioTeste");
        loginData.put("password", "senhaTeste");

        // Requisição POST para realizar o login
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"usuarioTeste\", \"password\":\"senhaTeste\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
