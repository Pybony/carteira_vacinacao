package io.schneider.carteira.vacinacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.schneider.carteira.vacinacao.domain.repository.VacinaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static io.schneider.carteira.vacinacao.fixture.VacinaDTOFixture.parametrosVacinaDTO;
import static io.schneider.carteira.vacinacao.fixture.VacinaDTOFixture.retornoVacinaDTO;
import static io.schneider.carteira.vacinacao.fixture.VacinaEntityFixture.vacinaEntityEntrada;
import static io.schneider.carteira.vacinacao.fixture.VacinaEntityFixture.vacinaEntityRetorno;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class VacinaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VacinaRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = "/reset_sequences.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveInserirVacinaQuandoEnviarDadosCorretos() throws Exception {
        final var parametros = parametrosVacinaDTO().build();
        final var retornoEsperadoDTO = retornoVacinaDTO().build();
        final var retornoEsperadoEntity = vacinaEntityRetorno().build();

        mockMvc.perform(post("/api/vacinas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parametros)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/vacinas/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(retornoEsperadoDTO)));

        assertThat(repository.findAll()).hasSize(1);
        assertThat(repository.findAll().get(0))
                .usingRecursiveComparison()
                .isEqualTo(retornoEsperadoEntity);
    }

    @Test
    @Sql(scripts = "/reset_sequences.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveRetornarVacinaQuandoBuscarPeloId() throws Exception {
        final var vacina = repository.save(vacinaEntityEntrada().build());
        final var retornoEsperado = retornoVacinaDTO().id(vacina.getId()).build();

        mockMvc.perform(get("/api/vacinas/" + vacina.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(retornoEsperado)));
    }

}