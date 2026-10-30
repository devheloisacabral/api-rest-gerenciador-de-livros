package br.edu.univassouras.livros.controller;

import br.edu.univassouras.livros.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LivroControllerTest {

    private static final String LIVRO_JSON = """
            {
              "titulo": "Dom Casmurro",
              "autor": "Machado de Assis",
              "genero": "Romance",
              "anoPublicacao": 1899,
              "statusLeitura": "Lido"
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LivroRepository repository;

    @BeforeEach
    void limparLivros() {
        repository.limpar();
    }

    @Test
    void deveCadastrarLivro() throws Exception {
        mockMvc.perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LIVRO_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/livros/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Dom Casmurro"));
    }

    @Test
    void deveListarLivros() throws Exception {
        cadastrarLivro();

        mockMvc.perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].autor").value("Machado de Assis"));
    }

    @Test
    void deveConsultarLivroPorId() throws Exception {
        cadastrarLivro();

        mockMvc.perform(get("/livros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genero").value("Romance"))
                .andExpect(jsonPath("$.anoPublicacao").value(1899));
    }

    @Test
    void deveAtualizarLivro() throws Exception {
        cadastrarLivro();
        String atualizacao = """
                {
                  "titulo": "Dom Casmurro - Edição Atualizada",
                  "autor": "Machado de Assis",
                  "genero": "Literatura Brasileira",
                  "anoPublicacao": 1900,
                  "statusLeitura": "Lendo"
                }
                """;

        mockMvc.perform(put("/livros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atualizacao))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Dom Casmurro - Edição Atualizada"))
                .andExpect(jsonPath("$.statusLeitura").value("Lendo"));
    }

    @Test
    void deveExcluirLivro() throws Exception {
        cadastrarLivro();

        mockMvc.perform(delete("/livros/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        mockMvc.perform(get("/livros/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarNaoEncontradoParaIdInexistente() throws Exception {
        mockMvc.perform(get("/livros/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem")
                        .value("Livro com identificador 999 não encontrado"));
    }

    @Test
    void deveRejeitarCadastroComCamposObrigatoriosVazios() throws Exception {
        String livroInvalido = """
                {
                  "titulo": " ",
                  "autor": "",
                  "genero": "Romance",
                  "anoPublicacao": null,
                  "statusLeitura": "Lido"
                }
                """;

        mockMvc.perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Dados inválidos"))
                .andExpect(jsonPath("$.campos.titulo").value("O título é obrigatório"))
                .andExpect(jsonPath("$.campos.autor").value("O autor é obrigatório"))
                .andExpect(jsonPath("$.campos.anoPublicacao")
                        .value("O ano de publicação é obrigatório"));
    }

    private void cadastrarLivro() throws Exception {
        mockMvc.perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LIVRO_JSON))
                .andExpect(status().isCreated());
    }
}
