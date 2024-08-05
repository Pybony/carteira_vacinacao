package io.schneider.carteira.vacinacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.schneider.carteira.vacinacao.domain.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static io.schneider.carteira.vacinacao.fixture.PessoaDTOFixture.parametrosPessoaDTO;
import static io.schneider.carteira.vacinacao.fixture.PessoaDTOFixture.retornoPessoaDTO;
import static io.schneider.carteira.vacinacao.fixture.PessoaEntityFixture.pessoaEntityEntrada;
import static io.schneider.carteira.vacinacao.fixture.PessoaEntityFixture.pessoaEntityRetorno;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PessoaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Sql(scripts = "/reset_sequences.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveInserirPessoaQuandoEnviarDadosCorretos() throws Exception {
        final var parametros = parametrosPessoaDTO().build();
        final var retornoEsperadoDTO = retornoPessoaDTO().build();
        final var retornoEsperadoEntity = pessoaEntityRetorno().build();

        mockMvc.perform(post("/api/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parametros)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/pessoas/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(retornoEsperadoDTO)));

        assertThat(repository.findAll()).hasSize(1);
        assertThat(repository.findAll().get(0))
                .usingRecursiveComparison()
                .isEqualTo(retornoEsperadoEntity);
    }

    @Test
    @Sql(scripts = "/reset_sequences.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveRetornarPessoaQuandoBuscarPeloId() throws Exception {
        final var pessoa = repository.save(pessoaEntityEntrada().build());
        final var retornoEsperado = retornoPessoaDTO().id(pessoa.getId()).build();

        mockMvc.perform(get("/api/pessoas/" + pessoa.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(retornoEsperado)));
    }

}
