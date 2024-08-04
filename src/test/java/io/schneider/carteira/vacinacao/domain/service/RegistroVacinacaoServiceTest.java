package io.schneider.carteira.vacinacao.domain.service;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosRegistroVacinacaoDTO;
import io.schneider.carteira.vacinacao.domain.entity.PessoaEntity;
import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import io.schneider.carteira.vacinacao.domain.entity.VacinaEntity;
import io.schneider.carteira.vacinacao.domain.mapper.RegistroVacinacaoMapper;
import io.schneider.carteira.vacinacao.domain.repository.PessoaRepository;
import io.schneider.carteira.vacinacao.domain.repository.VacinaRepository;
import io.schneider.carteira.vacinacao.domain.repository.RegistroVacinacaoRepository;
import io.schneider.carteira.vacinacao.shared.model.exception.AplicativoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.schneider.carteira.vacinacao.fixture.PessoaEntityFixture.*;
import static io.schneider.carteira.vacinacao.fixture.VacinaEntityFixture.*;
import static io.schneider.carteira.vacinacao.fixture.RegistroVacinacaoDTOFixture.retornoRegistroVacinacaoDTO;
import static io.schneider.carteira.vacinacao.fixture.RegistroVacinacaoEntityFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroVacinacaoServiceTest {

    @Mock
    private RegistroVacinacaoRepository registroVacinacaoRepository;

    @Mock
    private VacinaRepository vacinaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private RegistroVacinacaoMapper mapper;

    @InjectMocks
    private RegistroVacinacaoService service;

    @Test
    void deveRetornarRegistroVacinacaoComSucessoQuandoEnviarTodosDados() {
        final var parametros = ParametrosRegistroVacinacaoDTO.builder().build();
        final var registroVacinacaoEntityEntrada = registroVacinacaoEntityEntrada().build();
        final var pessoaEntityOptional = pessoaEntityOptional();
        final var vacinaEntityOptional = vacinaEntityOptional();
        final var registroVacinacaoEntityRetorno = registroVacinacaoEntityRetorno()
                .pessoa(pessoaEntityRetorno().build())
                .vacina(vacinaEntityRetorno().build())
                .build();
        final var retornoEsperado = retornoRegistroVacinacaoDTO().build();

        when(mapper.paraEntity(parametros))
                .thenReturn(registroVacinacaoEntityEntrada);

        when(registroVacinacaoRepository.save(registroVacinacaoEntityEntrada))
                .thenReturn(registroVacinacaoEntityRetorno);

        when(pessoaRepository.findById(registroVacinacaoEntityEntrada.getPessoa().getId()))
                .thenReturn(pessoaEntityOptional);

        when(vacinaRepository.findById(registroVacinacaoEntityEntrada.getVacina().getId()))
                .thenReturn(vacinaEntityOptional);

        when(mapper.paraDTO(registroVacinacaoEntityRetorno))
                .thenReturn(retornoRegistroVacinacaoDTO().build());

        assertThat(service.salvar(parametros)).usingRecursiveComparison().isEqualTo(retornoEsperado);
        verify(mapper, times(1)).paraEntity(any(ParametrosRegistroVacinacaoDTO.class));
        verify(registroVacinacaoRepository, times(1)).save(any(RegistroVacinacaoEntity.class));
        verify(pessoaRepository, times(1)).findById(anyLong());
        verify(vacinaRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).paraDTO(any(RegistroVacinacaoEntity.class));
    }

    @Test
    void deveConsultarComSucessoQuandoReceberIdValido() {
        final var id = 1L;
        final var optionalRegistroVacinacao = registroVacinacaoEntityOptional();
        final var optionalPessoa = pessoaEntityOptional();
        final var optionalVacina = vacinaEntityOptional();
        final var registroVacinacaoEntityRetorno = registroVacinacaoEntityRetorno()
                .pessoa(pessoaEntityRetorno().build())
                .vacina(vacinaEntityRetorno().build())
                .build();
        final var retornoEsperado = retornoRegistroVacinacaoDTO().build();

        when(registroVacinacaoRepository.findById(id))
                .thenReturn(optionalRegistroVacinacao);

        when(pessoaRepository.findById(id))
                .thenReturn(optionalPessoa);

        when(vacinaRepository.findById(id))
                .thenReturn(optionalVacina);

        when(mapper.paraDTO(registroVacinacaoEntityRetorno))
                .thenReturn(retornoRegistroVacinacaoDTO().build());

        assertThat(service.consultarPorId(id)).usingRecursiveComparison().isEqualTo(retornoEsperado);
        verify(registroVacinacaoRepository, times(1)).findById(anyLong());
        verify(pessoaRepository, times(1)).findById(anyLong());
        verify(vacinaRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).paraDTO(any(RegistroVacinacaoEntity.class));
    }

    @Test
    void deveLancarExcetionQuandoReceberIdInvalido() {
        final var id = 2L;
        final var optionalRegistroVacinacao = registroVacinacaoEntityOptionalVazia();

        when(registroVacinacaoRepository.findById(id))
                .thenReturn(optionalRegistroVacinacao);

        assertThatThrownBy(() -> service.consultarPorId(id))
                .isInstanceOf(AplicativoException.class)
                .hasMessage("Registro de vacinação não encontrada");
        verify(registroVacinacaoRepository, times(1)).findById(anyLong());
        verify(pessoaRepository, never()).findById(anyLong());
        verify(vacinaRepository, never()).findById(anyLong());
        verify(mapper, never()).paraDTO(any(RegistroVacinacaoEntity.class));
    }

    @Test
    void deveLancarExcetionQuandoReceberIdPessoaInvalido() {
        final var id = 2L;
        final var optionalRegistroVacinacao = Optional.of(registroVacinacaoEntityRetorno()
                .pessoa(PessoaEntity.builder()
                        .id(2L)
                        .build())
                .build());
        final var optionalPessoa = pessoaEntityOptionalVazia();

        when(registroVacinacaoRepository.findById(id))
                .thenReturn(optionalRegistroVacinacao);

        when(pessoaRepository.findById(id))
                .thenReturn(optionalPessoa);

        assertThatThrownBy(() -> service.consultarPorId(id))
                .isInstanceOf(AplicativoException.class)
                .hasMessage("Pessoa não encontrada");
        verify(registroVacinacaoRepository, times(1)).findById(anyLong());
        verify(pessoaRepository, times(1)).findById(anyLong());
        verify(vacinaRepository, never()).findById(anyLong());
        verify(mapper, never()).paraDTO(any(RegistroVacinacaoEntity.class));
    }

    @Test
    void deveLancarExcetionQuandoReceberIdVacinaInvalido() {
        final var id = 2L;
        final var optionalRegistroVacinacao = Optional.of(registroVacinacaoEntityRetorno()
                .vacina(VacinaEntity.builder()
                        .id(2L)
                        .build())
                .build());
        final var optionalPessoa = pessoaEntityOptional();
        final var optionalVacina = vacinaEntityOptionalVazia();

        when(registroVacinacaoRepository.findById(id))
                .thenReturn(optionalRegistroVacinacao);

        when(pessoaRepository.findById(1L))
                .thenReturn(optionalPessoa);

        when(vacinaRepository.findById(id))
                .thenReturn(optionalVacina);

        assertThatThrownBy(() -> service.consultarPorId(id))
                .isInstanceOf(AplicativoException.class)
                .hasMessage("Vacina não encontrada");
        verify(registroVacinacaoRepository, times(1)).findById(anyLong());
        verify(pessoaRepository, times(1)).findById(anyLong());
        verify(vacinaRepository, times(1)).findById(anyLong());
        verify(mapper, never()).paraDTO(any(RegistroVacinacaoEntity.class));
    }

}