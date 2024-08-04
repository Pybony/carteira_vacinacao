package io.schneider.carteira.vacinacao.domain.service;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosVacinaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoVacinaDTO;
import io.schneider.carteira.vacinacao.domain.entity.VacinaEntity;
import io.schneider.carteira.vacinacao.domain.mapper.VacinaMapper;
import io.schneider.carteira.vacinacao.domain.repository.VacinaRepository;
import io.schneider.carteira.vacinacao.shared.model.exception.AplicativoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.schneider.carteira.vacinacao.fixture.VacinaDTOFixture.parametrosVacinaDTO;
import static io.schneider.carteira.vacinacao.fixture.VacinaDTOFixture.retornoVacinaDTO;
import static io.schneider.carteira.vacinacao.fixture.VacinaEntityFixture.*;
import static io.schneider.carteira.vacinacao.shared.model.EsquemaVacinacaoEnum.DOSE_UNICA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacinaServiceTest {

    @Mock
    private VacinaRepository repository;

    @Mock
    private VacinaMapper mapper;

    @InjectMocks
    private VacinaService service;

    @Test
    void deveRetornarVacinaComSucessoQuandoEnviarTodosDados() {
        final var parametros = parametrosVacinaDTO().build();
        final var retornoEsperado = retornoVacinaDTO().build();
        final var vacinaEntityEntrada = vacinaEntityEntrada().build();
        final var vacinaEntityRetorno = vacinaEntityRetorno().build();

        when(mapper.paraEntity(parametros))
                .thenReturn(vacinaEntityEntrada);

        when(repository.save(vacinaEntityEntrada))
                .thenReturn(vacinaEntityRetorno);

        when(mapper.paraDTO(vacinaEntityRetorno))
                .thenReturn(new RetornoVacinaDTO(1L, "Epatite", DOSE_UNICA));

        assertThat(service.salvar(parametros)).usingRecursiveComparison().isEqualTo(retornoEsperado);
        verify(mapper, times(1)).paraEntity(any(ParametrosVacinaDTO.class));
        verify(repository, times(1)).save(any(VacinaEntity.class));
        verify(mapper, times(1)).paraDTO(any(VacinaEntity.class));
    }

    @Test
    void deveConsultarComSucessoQuandoReceberIdValido() {
        final var id = 1L;
        final var retornoEsperado = retornoVacinaDTO().build();
        final var optionalPessoa = vacinaEntityOptional();

        when(repository.findById(id))
                .thenReturn(optionalPessoa);

        when(mapper.paraDTO(optionalPessoa.get()))
                .thenReturn(new RetornoVacinaDTO(1L, "Epatite", DOSE_UNICA));

        assertThat(service.consultarPorId(id)).usingRecursiveComparison().isEqualTo(retornoEsperado);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).paraDTO(any(VacinaEntity.class));
    }

    @Test
    void deveLancarExcetionQuandoReceberIdInvalido() {
        final var id = 2L;
        final var optionalVacina = vacinaEntityOptionalVazia();

        when(repository.findById(id))
                .thenReturn(optionalVacina);

        assertThatThrownBy(() -> service.consultarPorId(id))
                .isInstanceOf(AplicativoException.class)
                .hasMessage("Vacina não encontrada");
        verify(repository, times(1)).findById(anyLong());
        verify(mapper, never()).paraDTO(any(VacinaEntity.class));
    }

}