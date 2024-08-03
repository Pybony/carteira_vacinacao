package io.schneider.carteira.vacinacao.domain.service;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosPessoaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoPessoaDTO;
import io.schneider.carteira.vacinacao.domain.entity.PessoaEntity;
import io.schneider.carteira.vacinacao.domain.mapper.PessoaMapper;
import io.schneider.carteira.vacinacao.domain.repository.PessoaRepository;
import io.schneider.carteira.vacinacao.shared.model.exception.NaoEncontradoExcepiton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static io.schneider.carteira.vacinacao.fixture.PessoaDTOFixture.parametrosPessoaDTO;
import static io.schneider.carteira.vacinacao.fixture.PessoaDTOFixture.retornoPessoaDTO;
import static io.schneider.carteira.vacinacao.fixture.PessoaEntityFixture.*;
import static io.schneider.carteira.vacinacao.shared.model.SexoEnum.MASCULINO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    private PessoaRepository repository;

    @Mock
    private PessoaMapper mapper;

    @InjectMocks
    private PessoaService service;

    @Test
    void deveRetornarPessoaComSucessoQuandoEnviarTodosDados() {
        final var parametros = parametrosPessoaDTO().build();
        final var retornoEsperado = retornoPessoaDTO().build();
        final var pessoaEntityEntrada = pessoaEntityEntrada().build();
        final var pessoaEntityRetorno = pessoaEntityRetorno().build();

        when(mapper.paraEntity(parametros))
                .thenReturn(pessoaEntityEntrada);

        when(repository.save(pessoaEntityEntrada))
                .thenReturn(pessoaEntityRetorno);

        when(mapper.paraDTO(pessoaEntityRetorno))
                .thenReturn(new RetornoPessoaDTO(1L, "João", LocalDate.now(), MASCULINO));

        assertThat(service.salvar(parametros)).usingRecursiveComparison().isEqualTo(retornoEsperado);
        verify(mapper, times(1)).paraEntity(any(ParametrosPessoaDTO.class));
        verify(repository, times(1)).save(any(PessoaEntity.class));
        verify(mapper, times(1)).paraDTO(any(PessoaEntity.class));
    }

    @Test
    void deveConsultarComSucessoQuandoReceberIdValido() {
        final var id = 1L;
        final var retornoEsperado = retornoPessoaDTO().build();
        final var optionalPessoa = pessoaEntityOptional();

        when(repository.findById(id))
                .thenReturn(optionalPessoa);

        when(mapper.paraDTO(optionalPessoa.get()))
                .thenReturn(new RetornoPessoaDTO(1L, "João", LocalDate.now(), MASCULINO));

        assertThat(service.consultarPorId(id)).usingRecursiveComparison().isEqualTo(retornoEsperado);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).paraDTO(any(PessoaEntity.class));
    }

    @Test
    void deveLancarExcetionQuandoReceberIdInvalido() {
        final var id = 2L;
        final var optionalPessoa = pessoaEntityOptionalVazia();

        when(repository.findById(id))
                .thenReturn(optionalPessoa);

        assertThatThrownBy(() -> service.consultarPorId(id))
                .isInstanceOf(NaoEncontradoExcepiton.class)
                .hasMessage("Pessoa não encontrada");
        verify(repository, times(1)).findById(anyLong());
        verify(mapper, never()).paraDTO(any(PessoaEntity.class));
    }

}