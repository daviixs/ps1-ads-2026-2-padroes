package br.edu.fatecfranca.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.fatecfranca.api.controllers.UserController;
import br.edu.fatecfranca.api.entities.User;
import br.edu.fatecfranca.api.factories.UserFactory;
import br.edu.fatecfranca.api.services.contracts.UserCrudService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserCrudService service;

    @MockitoBean
    private UserFactory factory;

    @Test
    void createsAndListsUsers() throws Exception {
        User saved = user(1L, "davi");
        given(factory.create(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));
        given(service.create(any(User.class))).willReturn(saved);
        given(service.findAll()).willReturn(List.of(saved));

        String payload = """
                {"fullname":"Davi Xavier","username":"davi","email":"davi@example.com",
                 "password":"senha-segura","is_admin":false}
                """;

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("davi"));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("davi@example.com"));
    }

    @Test
    void findsUpdatesAndDeletesExistingUser() throws Exception {
        User existing = user(1L, "davi");
        User updated = user(1L, "davi-x");
        given(service.findById(1L)).willReturn(Optional.of(existing));
        given(service.existsById(1L)).willReturn(true);
        given(service.update(any(User.class))).willReturn(updated);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("davi"));

        mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content("""
                {"fullname":"Davi Xavier","username":"davi-x","email":"davi@example.com",
                 "password":"senha-segura","is_admin":false}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("davi-x"));

        mockMvc.perform(delete("/users/1")).andExpect(status().isNoContent());
    }

    @Test
    void returnsNotFoundForUnknownUser() throws Exception {
        given(service.findById(99L)).willReturn(Optional.empty());
        given(service.existsById(99L)).willReturn(false);

        mockMvc.perform(get("/users/99")).andExpect(status().isNotFound());
        mockMvc.perform(put("/users/99").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/users/99")).andExpect(status().isNotFound());
    }

    private User user(Long id, String username) {
        User user = new User();
        user.setId(id);
        user.setFullname("Davi Xavier");
        user.setUsername(username);
        user.setEmail("davi@example.com");
        user.setPassword("senha-segura");
        user.setIsAdmin(false);
        return user;
    }
}
