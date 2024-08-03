package io.schneider.carteira.vacinacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.schneider.carteira.vacinacao.domain.entity.PessoaEntity;
import io.schneider.carteira.vacinacao.domain.entity.VacinaEntity;
import io.schneider.carteira.vacinacao.domain.repository.PessoaRepository;
import io.schneider.carteira.vacinacao.domain.repository.RegistroVacinacaoRepository;
import io.schneider.carteira.vacinacao.domain.repository.VacinaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static io.schneider.carteira.vacinacao.fixture.PessoaDTOFixture.retornoPessoaDTO;
import static io.schneider.carteira.vacinacao.fixture.PessoaEntityFixture.pessoaEntityEntrada;
import static io.schneider.carteira.vacinacao.fixture.RegistroVacinacaoDTOFixture.parametrosRegistroVacinacaoDTO;
import static io.schneider.carteira.vacinacao.fixture.RegistroVacinacaoDTOFixture.retornoRegistroVacinacaoDTO;
import static io.schneider.carteira.vacinacao.fixture.RegistroVacinacaoEntityFixture.registroVacinacaoEntityEntrada;
import static io.schneider.carteira.vacinacao.fixture.RegistroVacinacaoEntityFixture.registroVacinacaoEntityRetorno;
import static io.schneider.carteira.vacinacao.fixture.VacinaDTOFixture.retornoVacinaDTO;
import static io.schneider.carteira.vacinacao.fixture.VacinaEntityFixture.vacinaEntityRetorno;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RegistroVacinacaoControllerIntegratioTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private RegistroVacinacaoRepository registroVacinacaoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = "/reset_sequences.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveInserirVacinacaoQuandoEnviarDadosCorretos() throws Exception {
        final var pessoa = pessoaRepository.save(pessoaEntityEntrada().build());
        final var vacina = vacinaRepository.save(vacinaEntityRetorno().build());

        final var parametros = parametrosRegistroVacinacaoDTO()
                .vacinaId(vacina.getId())
                .pessoaId(pessoa.getId())
                .build();

        final var retornoEsperadoDTO = retornoRegistroVacinacaoDTO()
                .vacina(retornoVacinaDTO()
                        .id(vacina.getId())
                        .build())
                .pessoa(retornoPessoaDTO()
                        .id(pessoa.getId())
                        .build())
                .build();

        final var retornoEsperadoEntity = registroVacinacaoEntityRetorno()
                .pessoa(PessoaEntity.builder()
                        .id(pessoa.getId())
                        .build())
                .vacina(VacinaEntity.builder()
                        .id(vacina.getId())
                        .build())
                .build();

        mockMvc.perform(post("/api/registro-vacinacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parametros)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/registro-vacinacao/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(retornoEsperadoDTO)));

        assertThat(registroVacinacaoRepository.findAll()).hasSize(1);
        assertThat(registroVacinacaoRepository.findAll().get(0))
                .usingRecursiveComparison()
                .isEqualTo(retornoEsperadoEntity);
    }

    @Test
    @Sql(scripts = "/reset_sequences.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveRetornarVacinacaoQuandoBuscarPeloId() throws Exception {
        final var pessoa = pessoaRepository.save(pessoaEntityEntrada().build());
        final var vacina = vacinaRepository.save(vacinaEntityRetorno().build());
        final var registroVacinacao = registroVacinacaoRepository.save(registroVacinacaoEntityEntrada()
                .pessoa(PessoaEntity.builder()
                        .id(pessoa.getId())
                        .build())
                .vacina(VacinaEntity.builder()
                        .id(vacina.getId())
                        .build())
                .build());
        final var retornoEsperado = retornoRegistroVacinacaoDTO()
                .id(registroVacinacao.getId())
                .vacina(retornoVacinaDTO()
                        .id(vacina.getId())
                        .build())
                .pessoa(retornoPessoaDTO()
                        .id(pessoa.getId())
                        .build())
                .build();

        mockMvc.perform(get("/api/registro-vacinacao/" + registroVacinacao.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(retornoEsperado)));
    }

}