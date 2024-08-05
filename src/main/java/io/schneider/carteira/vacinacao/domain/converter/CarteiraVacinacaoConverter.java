package io.schneider.carteira.vacinacao.domain.converter;

import io.schneider.carteira.vacinacao.controller.dto.RetornoCarteiraVacinacaoDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoDoseAplicadaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoVacinaAplicadaDTO;
import io.schneider.carteira.vacinacao.domain.entity.PessoaEntity;
import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import io.schneider.carteira.vacinacao.domain.entity.VacinaEntity;
import io.schneider.carteira.vacinacao.domain.mapper.PessoaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CarteiraVacinacaoConverter {

    private final PessoaMapper pessoaMapper;

    public RetornoCarteiraVacinacaoDTO paraCarteiraVacinacaoDTO(final PessoaEntity pessoa, final Collection<RegistroVacinacaoEntity> registros) {
        final var vacinasAplicadas = registros.stream()
                .collect(Collectors.groupingBy(RegistroVacinacaoEntity::getVacina))
                .entrySet().stream()
                .map(entry -> juntarDosesAplicadasVacina(entry.getKey(), entry.getValue()))
                .toList();

        return RetornoCarteiraVacinacaoDTO.builder()
                .pessoa(pessoaMapper.paraDTO(pessoa))
                .vacinas(vacinasAplicadas)
                .build();
    }

    private RetornoVacinaAplicadaDTO juntarDosesAplicadasVacina(final VacinaEntity vacina, final Collection<RegistroVacinacaoEntity> dosesAplicadas) {
        final var dosesAplicadasDTO = dosesAplicadas.stream()
                .map(this::paraDoseAplicadadaDTO)
                .toList();

        return paraVacinaAplicadaDTO(vacina, dosesAplicadasDTO);
    }

    private RetornoDoseAplicadaDTO paraDoseAplicadadaDTO(final RegistroVacinacaoEntity registro) {
        return RetornoDoseAplicadaDTO.builder()
                .id(registro.getId())
                .dataAplicacao(registro.getDataAplicacao())
                .doseAplicada(registro.getDoseAplicada())
                .build();
    }

    private RetornoVacinaAplicadaDTO paraVacinaAplicadaDTO(final VacinaEntity vacina, final Collection<RetornoDoseAplicadaDTO> dosesAplicadas) {
        return RetornoVacinaAplicadaDTO.builder()
                .id(vacina.getId())
                .nome(vacina.getNome())
                .esquemaVacinacao(vacina.getEsquemaVacinacao())
                .doses(dosesAplicadas)
                .build();
    }

}
